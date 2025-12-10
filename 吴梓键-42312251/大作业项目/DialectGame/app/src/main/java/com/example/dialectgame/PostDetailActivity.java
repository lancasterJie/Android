package com.example.dialectgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.adapter.CommentAdapter;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.Comment;
import com.example.dialectgame.model.Favorite;
import com.example.dialectgame.model.Follow;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.model.Like;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {
    private int postId;
    private TextView tvTitle, tvContent, tvAuthor, tvTime, tvLikes, tvCommentCount, tvFavoriteCount;
    private RecyclerView rvComments;
    private EditText etComment;
    private Button btnSendComment, btnFollowAuthor;
    private ImageView ivLike, ivFavorite;
    private LinearLayout llLike, llComment, llFavorite;
    private AppDatabase db;
    private ForumPost currentPost;
    private User currentUser;
    private User postAuthor;
    private CommentAdapter commentAdapter;
    private boolean isLiked = false;
    private boolean isFavorited = false;
    private boolean isFollowingAuthor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postId = getIntent().getIntExtra("POST_ID", -1);
        if (postId == -1) {
            finish();
            return;
        }

        // 检查登录状态
        UserManager userManager = UserManager.getInstance(this);
        if (!userManager.isLoggedIn()) {
            finish();
            return;
        }
        currentUser = userManager.getCurrentUser();

        db = AppDatabase.getInstance(this);
        initViews();
        loadPostDetail();
        loadComments();
        setupButtonListeners();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_post_title);
        tvContent = findViewById(R.id.tv_post_content);
        tvAuthor = findViewById(R.id.tv_post_author);
        tvTime = findViewById(R.id.tv_post_time);
        tvLikes = findViewById(R.id.tv_post_likes);
        tvCommentCount = findViewById(R.id.tv_comment_count);
        tvFavoriteCount = findViewById(R.id.tv_favorite_count);
        rvComments = findViewById(R.id.rv_comments);
        etComment = findViewById(R.id.et_comment);
        btnSendComment = findViewById(R.id.btn_send_comment);
        btnFollowAuthor = findViewById(R.id.btn_follow_author);
        ivLike = findViewById(R.id.iv_like);
        ivFavorite = findViewById(R.id.iv_favorite);
        llLike = findViewById(R.id.ll_like);
        llComment = findViewById(R.id.ll_comment);
        llFavorite = findViewById(R.id.ll_favorite);

        rvComments.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void setupButtonListeners() {
        // 点赞按钮点击事件
        llLike.setOnClickListener(v -> toggleLike());

        // 评论按钮点击事件
        llComment.setOnClickListener(v -> etComment.requestFocus());

        // 收藏按钮点击事件
        llFavorite.setOnClickListener(v -> toggleFavorite());

        // 发送评论按钮点击事件
        btnSendComment.setOnClickListener(v -> sendComment());

        // 关注作者按钮点击事件
        btnFollowAuthor.setOnClickListener(v -> toggleFollowAuthor());
    }

    private void loadPostDetail() {
        currentPost = db.forumDao().getPostById(postId);
        if (currentPost == null) {
            finish();
            return;
        }

        postAuthor = db.userDao().findById(currentPost.getUserId());
        if (postAuthor == null) {
            finish();
            return;
        }

        tvTitle.setText(currentPost.getTitle());
        tvContent.setText(currentPost.getContent());
        tvAuthor.setText(postAuthor.getNickname());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        tvTime.setText(sdf.format(new Date(currentPost.getCreateTime())));

        // 加载点赞状态和数量
        loadLikeStatus();

        // 加载评论数量
        loadCommentCount();

        // 加载收藏状态和数量
        loadFavoriteStatus();

        // 加载关注状态
        loadFollowStatus();
    }

    private void loadLikeStatus() {
        new Thread(() -> {
            isLiked = db.likeDao().hasLiked(currentUser.getId(), postId);
            int likeCount = db.likeDao().getLikeCount(postId);

            runOnUiThread(() -> {
                tvLikes.setText(String.valueOf(likeCount));
                updateLikeButton();
            });
        }).start();
    }

    private void loadCommentCount() {
        new Thread(() -> {
            List<Comment> comments = db.forumDao().getCommentsByPostId(postId);
            int count = comments.size();

            runOnUiThread(() -> tvCommentCount.setText(String.valueOf(count)));
        }).start();
    }

    private void loadFavoriteStatus() {
        new Thread(() -> {
            isFavorited = db.favoriteDao().hasFavorited(currentUser.getId(), postId);
            int favoriteCount = db.favoriteDao().getFavoriteCount(postId);

            runOnUiThread(() -> {
                tvFavoriteCount.setText(String.valueOf(favoriteCount));
                updateFavoriteButton();
            });
        }).start();
    }

    private void loadFollowStatus() {
        // 如果是自己的帖子，隐藏关注按钮
        if (currentUser.getId() == postAuthor.getId()) {
            btnFollowAuthor.setVisibility(View.GONE);
            return;
        }

        new Thread(() -> {
            isFollowingAuthor = db.followDao().isFollowing(currentUser.getId(), postAuthor.getId());

            runOnUiThread(() -> updateFollowButton());
        }).start();
    }

    private void loadComments() {
        new Thread(() -> {
            List<Comment> comments = db.forumDao().getCommentsByPostId(postId);
            runOnUiThread(() -> {
                commentAdapter = new CommentAdapter(comments, db);
                rvComments.setAdapter(commentAdapter);
            });
        }).start();
    }

    private void sendComment() {
        String content = etComment.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = currentUser.getId();
        Comment comment = new Comment(postId, userId, content);
        db.forumDao().insertComment(comment);

        etComment.setText("");
        loadComments();
        loadCommentCount();
    }

    private void toggleLike() {
        new Thread(() -> {
            if (isLiked) {
                // 取消点赞
                Like like = new Like(currentUser.getId(), postId);
                db.likeDao().unlikePost(like);
            } else {
                // 点赞
                Like like = new Like(currentUser.getId(), postId);
                db.likeDao().likePost(like);
            }

            // 刷新状态
            loadLikeStatus();
        }).start();
    }

    private void toggleFavorite() {
        new Thread(() -> {
            if (isFavorited) {
                // 取消收藏
                Favorite favorite = new Favorite(currentUser.getId(), postId);
                db.favoriteDao().unfavoritePost(favorite);
            } else {
                // 收藏
                Favorite favorite = new Favorite(currentUser.getId(), postId);
                db.favoriteDao().favoritePost(favorite);
            }

            // 刷新状态
            loadFavoriteStatus();
        }).start();
    }

    private void toggleFollowAuthor() {
        new Thread(() -> {
            if (isFollowingAuthor) {
                // 取消关注
                Follow follow = new Follow(currentUser.getId(), postAuthor.getId());
                db.followDao().unfollow(follow);
            } else {
                // 关注
                Follow follow = new Follow(currentUser.getId(), postAuthor.getId());
                db.followDao().follow(follow);
            }

            // 刷新状态
            loadFollowStatus();
        }).start();
    }

    private void updateLikeButton() {
        if (isLiked) {
            ivLike.setImageResource(R.drawable.ic_liked);
            ivLike.setColorFilter(getResources().getColor(R.color.red));
        } else {
            ivLike.setImageResource(R.drawable.ic_like);
            ivLike.setColorFilter(getResources().getColor(R.color.black));
        }
    }

    private void updateFavoriteButton() {
        if (isFavorited) {
            ivFavorite.setImageResource(R.drawable.ic_favorited);
            ivFavorite.setColorFilter(getResources().getColor(R.color.red));
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite);
            ivFavorite.setColorFilter(getResources().getColor(R.color.black));
        }
    }

    private void updateFollowButton() {
        if (isFollowingAuthor) {
            btnFollowAuthor.setText("已关注");
            btnFollowAuthor.setBackgroundResource(R.drawable.btn_round_gray);
        } else {
            btnFollowAuthor.setText("关注");
            btnFollowAuthor.setBackgroundResource(R.drawable.btn_round_primary);
        }
    }
}