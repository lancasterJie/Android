package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化论坛相关UI和数据
        initForumUI();

        // 跳转到AI对话页面
        Button chatButton = findViewById(R.id.goto_chat_button);
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // 跳转到游戏地区选择页
        Button gameButton = findViewById(R.id.goto_game_button);
        gameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegionSelectActivity.class);
            startActivity(intent);
        });

        // 跳转到卡片图鉴页
        Button albumButton = findViewById(R.id.goto_album_button);
        albumButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CardAlbumActivity.class);
            startActivity(intent);
        });
    }

    private void initForumUI() {
        // 论坛功能（保留原有逻辑）
    }
}