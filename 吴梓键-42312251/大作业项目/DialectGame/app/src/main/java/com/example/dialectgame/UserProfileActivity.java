package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvUsername;
    private EditText etNickname, etHometown, etEmail;
    private Button btnUpdate, btnLogout, btnBack;
    private ImageView ivAvatar;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private User currentUser;
    private boolean isEditing = false;
    private AppDatabase appDatabase; // 数据库实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // 初始化数据库
        appDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "dialect_game_db"
                ).allowMainThreadQueries() // 简化处理（实际项目建议用子线程）
                .build();

        // 检查登录状态
        UserManager userManager = UserManager.getInstance(this);
        if (!userManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        currentUser = userManager.getCurrentUser();

        // 初始化UI
        initViews();

        // 设置用户信息
        setUserInfo();

        // 返回按钮点击事件（返回论坛页面）
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class); // 假设论坛页面为ForumActivity
            startActivity(intent);
            finish();
        });

        // 更新/保存信息按钮点击事件
        btnUpdate.setOnClickListener(v -> {
            if (isEditing) {
                // 保存信息
                saveUserInfo();
            } else {
                // 进入编辑模式
                enterEditMode();
            }
        });

        // 退出登录
        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void initViews() {
        tvUsername = findViewById(R.id.tv_username);
        etNickname = findViewById(R.id.et_nickname);
        etHometown = findViewById(R.id.et_hometown);
        etEmail = findViewById(R.id.et_email);
        btnUpdate = findViewById(R.id.btn_update);
        btnLogout = findViewById(R.id.btn_logout);
        btnBack = findViewById(R.id.btn_back);
        ivAvatar = findViewById(R.id.iv_avatar);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
    }

    private void setUserInfo() {
        tvUsername.setText("用户名: " + currentUser.getUsername());
        etNickname.setText(currentUser.getNickname());
        etHometown.setText(currentUser.getHometown());
        etEmail.setText(currentUser.getEmail());

        // 设置性别选择
        String gender = currentUser.getGender();
        if ("male".equals(gender)) {
            rbMale.setChecked(true);
        } else if ("female".equals(gender)) {
            rbFemale.setChecked(true);
        }
    }

    // 进入编辑模式
    private void enterEditMode() {
        isEditing = true;
        btnUpdate.setText("保存信息");

        // 启用编辑控件
        etNickname.setEnabled(true);
        etHometown.setEnabled(true);
        etEmail.setEnabled(true);
        rgGender.setEnabled(true);
        btnUpdate.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
    }

    // 保存用户信息（直接在Activity中实现数据库更新）
    private void saveUserInfo() {
        String nickname = etNickname.getText().toString().trim();
        if (nickname.isEmpty()) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取性别选择
        String gender = "";
        if (rbMale.isChecked()) {
            gender = "male";
        } else if (rbFemale.isChecked()) {
            gender = "female";
        }

        String hometown = etHometown.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // 更新当前用户对象
        currentUser.setNickname(nickname);
        currentUser.setGender(gender);
        currentUser.setHometown(hometown);
        currentUser.setEmail(email);

        // 直接操作数据库更新用户信息
        new Thread(() -> {
            try {
                appDatabase.userDao().update(currentUser); // 假设UserDao有update(User user)方法
                runOnUiThread(() -> {
                    Toast.makeText(this, "信息更新成功", Toast.LENGTH_SHORT).show();
                    exitEditMode();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }

    // 退出编辑模式
    private void exitEditMode() {
        isEditing = false;
        btnUpdate.setText("更新信息");

        // 禁用编辑控件
        etNickname.setEnabled(false);
        etHometown.setEnabled(false);
        etEmail.setEnabled(false);
        rgGender.setEnabled(false);
        btnUpdate.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库连接（可选，Room会自动管理）
        if (appDatabase != null && appDatabase.isOpen()) {
            appDatabase.close();
        }
    }
}