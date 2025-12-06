package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dialectgame.utils.UserManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化论坛相关UI和数据
        initForumUI();

        // 检查登录状态，未登录则跳转到登录页面
        if (!UserManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        // 用户中心点击事件
        findViewById(R.id.user_center_icon).setOnClickListener(v -> {
            if (UserManager.getInstance(this).isLoggedIn()) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // 跳转到AI对话页面（代码不变）
        Button chatButton = findViewById(R.id.goto_chat_button);
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // 跳转到游戏地区选择页（代码不变）
        Button gameButton = findViewById(R.id.goto_game_button);
        gameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegionSelectActivity.class);
            startActivity(intent);
        });

        // 跳转到卡片图鉴页（代码不变）
        Button albumButton = findViewById(R.id.goto_album_button);
        albumButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CardAlbumActivity.class);
            startActivity(intent);
        });
    }

    // 完善initForumUI方法
    private void initForumUI() {
        // 点击论坛内容区域跳转到论坛页面
        findViewById(R.id.forum_content_area).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ForumActivity.class));
        });
    }
}