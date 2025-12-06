// PostDetailActivity.java
package com.example.dialectgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.adapter.CommentAdapter;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.Comment;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {
    private int postId;
    private TextView tvTitle, tvContent, tvAuthor, tvTime, tvLikes;
    private RecyclerView rvComments;
    private EditText etComment;
    private Button btnSendComment, btnLike;
    private AppDatabase db;
    private ForumPost currentPost;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postId = getIntent().getIntExtra("POST_ID", -1);
        if (postId == -1) {
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);
        initViews();
        loadPostDetail();
        loadComments();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_post_title);
        tvContent = findViewById(R.id.tv_post_content);
        tvAuthor = findViewById(R.id.tv_post_author);
        tvTime = findViewById(R.id.tv_post_time);
        tvLikes = findViewById(R.id.tv_post_likes);
        rvComments = findViewById(R.id.rv_comments);
        etComment = findViewById(R.id.et_comment);
        btnSendComment = findViewById(R.id.btn_send_comment);
        btnLike = findViewById(R.id.btn_like);

        rvComments.setLayoutManager(new LinearLayoutManager(this));

        btnSendComment.setOnClickListener(v -> sendComment());
        btnLike.setOnClickListener(v -> likePost());
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void loadPostDetail() {
        currentPost = db.forumDao().getPostById(postId);
        if (currentPost == null) {
            finish();
            return;
        }

        User author = db.userDao().findById(currentPost.getUserId());

        tvTitle.setText(currentPost.getTitle());
        tvContent.setText(currentPost.getContent());
        tvAuthor.setText(author != null ? author.getNickname() : "匿名用户");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        tvTime.setText(sdf.format(new Date(currentPost.getCreateTime())));

        tvLikes.setText(String.valueOf(currentPost.getLikeCount()));
    }

    private void loadComments() {
        List<Comment> comments = db.forumDao().getCommentsByPostId(postId);
        commentAdapter = new CommentAdapter(comments, db);
        rvComments.setAdapter(commentAdapter);
    }

    private void sendComment() {
        if (!UserManager.getInstance(this).isLoggedIn()) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        String content = etComment.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = UserManager.getInstance(this).getCurrentUser().getId();
        Comment comment = new Comment(postId, userId, content);
        db.forumDao().insertComment(comment);

        etComment.setText("");
        loadComments();
    }

    private void likePost() {
        if (currentPost == null) return;

        currentPost.setLikeCount(currentPost.getLikeCount() + 1);
        db.forumDao().updatePost(currentPost);
        tvLikes.setText(String.valueOf(currentPost.getLikeCount()));
    }
}