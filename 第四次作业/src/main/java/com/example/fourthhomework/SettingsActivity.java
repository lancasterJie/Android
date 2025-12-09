package com.example.fourthhomework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fourthhomework.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    // 控件引用
    private CheckBox cbAutoSave;
    private EditText etUserName;
    private EditText etPasswd;
    private Button btnLogin;

    // SharedPreferences 实例
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 初始化 SharedPreferences（名称：settings，模式：私有）
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        // 初始化控件
        initViews();

        // 加载之前保存的配置，更新 UI
        loadSavedSettings();

        // 设置按钮点击事件
        setClickListeners();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        cbAutoSave = findViewById(R.id.cb_auto_save);
        etUserName = findViewById(R.id.et_user_name);
        etPasswd = findViewById(R.id.et_passwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    /**
     * 加载 SharedPreferences 中保存的配置，更新 UI
     */
    private void loadSavedSettings() {
        // 读取自动保存开关状态（默认关闭）
        boolean autoSave = sp.getBoolean("auto_save", false);
        // 读取用户名（默认空字符串）
        String userName = sp.getString("user_name", "");
        // 读取密码（默认空字符串）
        String passwd = sp.getString("passwd", "");

        // 更新 UI
        cbAutoSave.setChecked(autoSave);
        etUserName.setText(userName);
        etPasswd.setText(passwd);
    }

    /**
     * 设置按钮点击监听器
     */
    private void setClickListeners() {
        // 登录按钮：跳转示例页面（这里用 MainActivity 演示，可替换为实际目标页面）
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击登录时，先保存当前配置
                saveSettings();

                // 跳转主界面（可根据需求改为其他页面）
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                // 清除返回栈，避免重复创建
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish(); // 关闭设置界面
            }
        });
    }

    /**
     * 保存当前 UI 配置到 SharedPreferences
     */
    private void saveSettings() {
        // 获取当前 UI 状态
        boolean autoSave = cbAutoSave.isChecked();
        String userName = etUserName.getText().toString().trim();
        String passwd = etPasswd.getText().toString().trim();

        // 通过 Editor 写入数据
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", autoSave);
        editor.putString("user_name", userName);
        editor.putString("passwd", passwd);
        // apply()：异步保存（推荐，不阻塞主线程）
        editor.apply();
    }

    /**
     * 当页面失去焦点（返回主界面/退出）时，自动保存配置
     */
    @Override
    protected void onPause() {
        super.onPause();
        // 无论是否开启自动保存，都保存当前配置（自动保存是触发文件读写，这里是保存配置）
        saveSettings();
    }
}