// UserProfileActivity.java
package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvUsername;
    private EditText etNickname;
    private Button btnUpdate, btnLogout, btnSwitchAccount;
    private ImageView ivAvatar;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // 检查登录状态
        UserManager userManager = UserManager.getInstance(this);
        if (!userManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        currentUser = userManager.getCurrentUser();

        // 初始化UI
        tvUsername = findViewById(R.id.tv_username);
        etNickname = findViewById(R.id.et_nickname);
        btnUpdate = findViewById(R.id.btn_update);
        btnLogout = findViewById(R.id.btn_logout);
        btnSwitchAccount = findViewById(R.id.btn_switch_account);
        ivAvatar = findViewById(R.id.iv_avatar);

        // 设置用户信息
        tvUsername.setText("用户名: " + currentUser.getUsername());
        etNickname.setText(currentUser.getNickname());

        // 更新信息
        btnUpdate.setOnClickListener(v -> updateUserInfo());

        // 退出登录
        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // 切换账号
        btnSwitchAccount.setOnClickListener(v -> {
            userManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void updateUserInfo() {
        String nickname = etNickname.getText().toString().trim();
        if (nickname.isEmpty()) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (UserManager.getInstance(this).updateUserInfo(nickname, currentUser.getAvatar())) {
            Toast.makeText(this, "信息更新成功", Toast.LENGTH_SHORT).show();
            currentUser.setNickname(nickname);
        } else {
            Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
        }
    }
}