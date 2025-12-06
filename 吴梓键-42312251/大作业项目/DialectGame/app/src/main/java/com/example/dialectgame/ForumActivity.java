// ForumActivity.java
package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.adapter.ForumPostAdapter;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.utils.UserManager;
import java.util.List;

public class ForumActivity extends AppCompatActivity implements ForumPostAdapter.OnPostClickListener {
    private RecyclerView recyclerView;
    private ForumPostAdapter adapter;
    private AppDatabase db;
    private Button btnAddPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        db = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.rv_forum_posts);
        btnAddPost = findViewById(R.id.btn_add_post);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 检查登录状态
        btnAddPost.setOnClickListener(v -> {
            if (UserManager.getInstance(this).isLoggedIn()) {
                startActivity(new Intent(ForumActivity.this, CreatePostActivity.class));
            } else {
                startActivity(new Intent(ForumActivity.this, LoginActivity.class));
            }
        });

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    private void loadPosts() {
        List<ForumPost> posts = db.forumDao().getAllPosts();
        adapter = new ForumPostAdapter(posts, this, db);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPostClick(int postId) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("POST_ID", postId);
        startActivity(intent);
    }
}