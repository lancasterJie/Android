package com.example.dialectgame; // 替换为你的包名

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.dialectgame.adapter.ForumPostAdapter;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;
import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ForumPostAdapter.OnPostClickListener {

    // 控件声明
    private RecyclerView rvBlogList;
    private ImageView ivAddPost;
    private ImageView ivUserCenter; // 新增：用户中心头像控件
    private AppDatabase appDatabase;
    private ForumPostAdapter postAdapter;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据库
        appDatabase = AppDatabase.getInstance(this);
        userManager = UserManager.getInstance(this);

        // 检查登录状态
        if (!userManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish(); // 未登录时直接进入登录页，关闭首页
            return;
        }

        // 初始化控件
        initViews();

        // 初始化事件监听
        initEvents();

        // 加载帖子列表
        loadForumPosts();

        // 加载用户头像
        loadUserAvatar();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        // 帖子列表
        rvBlogList = findViewById(R.id.rv_blog_list);
        rvBlogList.setLayoutManager(new LinearLayoutManager(this));

        // 悬浮加号按钮
        ivAddPost = findViewById(R.id.iv_add_post);

        // 新增：用户中心头像
        ivUserCenter = findViewById(R.id.av_user);
    }

    /**
     * 初始化事件监听
     */
    private void initEvents() {
        // 悬浮按钮点击事件（发布新帖子）
        ivAddPost.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
        });

        // 个人中心点击事件（修改：使用ivUserCenter控件）
        ivUserCenter.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
        });

        // 底部Tab点击事件（AI对话）
        findViewById(R.id.goto_chat).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ChatActivity.class));
        });

        // 底部Tab点击事件（方言解密）
        findViewById(R.id.goto_game).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegionSelectActivity.class));
        });

        // 底部Tab点击事件（游戏图鉴）
        findViewById(R.id.goto_album).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CardAlbumActivity.class));
        });
    }

    /**
     * 加载用户头像
     */
    private void loadUserAvatar() {
        User currentUser = userManager.getCurrentUser();
        if (currentUser != null) {
            String avatarPath = currentUser.getAvatar();
            // 显示头像：优先使用用户上传的头像，否则使用默认头像
            if (avatarPath != null && !avatarPath.isEmpty() && !"default_avatar".equals(avatarPath)) {
                Glide.with(this)
                        .load(new File(avatarPath))
                        .circleCrop() // 圆形裁剪，与用户中心保持一致
                        .into(ivUserCenter);
            } else {
                ivUserCenter.setImageResource(R.drawable.ic_user);
            }
        }
    }

    /**
     * 加载论坛帖子列表
     */
    private void loadForumPosts() {
        new Thread(() -> {
            // 从数据库获取帖子（子线程执行数据库操作）
            List<ForumPost> postList = appDatabase.forumDao().getAllPosts();
            // 主线程更新UI
            runOnUiThread(() -> {
                postAdapter = new ForumPostAdapter(postList, MainActivity.this, appDatabase);
                rvBlogList.setAdapter(postAdapter);
            });
        }).start();
    }

    /**
     * 帖子点击事件（跳转到帖子详情页）
     */
    @Override
    public void onPostClick(int postId) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("POST_ID", postId);
        startActivity(intent);
    }

    /**
     * 页面恢复时刷新数据（包括头像和帖子列表）
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadForumPosts();
        loadUserAvatar(); // 每次回到主页都刷新头像
    }
}