// CreatePostActivity.java
package com.example.dialectgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.utils.UserManager;

public class CreatePostActivity extends AppCompatActivity {
    private EditText etTitle, etContent;
    private Button btnSubmit;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // 检查登录状态
        if (!UserManager.getInstance(this).isLoggedIn()) {
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);
        etTitle = findViewById(R.id.et_post_title);
        etContent = findViewById(R.id.et_post_content);
        btnSubmit = findViewById(R.id.btn_submit_post);

        btnSubmit.setOnClickListener(v -> submitPost());

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void submitPost() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = UserManager.getInstance(this).getCurrentUser().getId();
        ForumPost post = new ForumPost(userId, title, content);
        db.forumDao().insertPost(post);

        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}