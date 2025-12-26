package com.example.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText etUserName, etPassword, etConfirmPassword;
    private Button btnSubmit;
    private CheckBox cbAutoSave;
    private RadioGroup rgAuthType;
    private RadioButton rbLogin, rbRegister;
    private LinearLayout layoutConfirmPassword;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "user_settings";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AUTO_SAVE = "auto_save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 检查是否已经登录
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (isUserLoggedIn()) {
            // 如果已经登录，直接跳转到主界面
            goToMainActivity();
            return;
        }

        initViews();
        setupClickListeners();
        setupRadioGroupListener();
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    private void initViews() {
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        cbAutoSave = findViewById(R.id.cbAutoSave);
        rgAuthType = findViewById(R.id.rgAuthType);
        rbLogin = findViewById(R.id.rbLogin);
        rbRegister = findViewById(R.id.rbRegister);
        layoutConfirmPassword = findViewById(R.id.layoutConfirmPassword);

        // 默认选择登录模式
        rbLogin.setChecked(true);
        updateUIForLogin();
    }

    private void setupRadioGroupListener() {
        rgAuthType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbLogin) {
                    updateUIForLogin();
                } else if (checkedId == R.id.rbRegister) {
                    updateUIForRegister();
                }
            }
        });
    }

    private void updateUIForLogin(){
        layoutConfirmPassword.setVisibility(View.GONE);
        btnSubmit.setText("登录");

        // 保留用户名输入框的内容，清空密码输入框
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    private void updateUIForRegister(){
        layoutConfirmPassword.setVisibility(View.VISIBLE);
        btnSubmit.setText("注册");

        // 保留用户名输入框的内容，清空密码输入框
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    private void setupClickListeners(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbLogin.isChecked()) {
                    login();
                } else {
                    register();
                }
            }
        });
    }

    private void login(){
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_LONG).show();
            return;
        }
        String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        if(username.equals(savedUsername) && password.equals(savedPassword)){
            saveLoginState(true);
            saveAutoSaveSetting();
            goToMainActivity();
            Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(){
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        if (username.equals(savedUsername)) {
            Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "密码长度至少6位", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();

        saveAutoSaveSetting();

        rbLogin.setChecked(true);
        updateUIForLogin();

        Toast.makeText(this, "注册成功！请使用新账号登录", Toast.LENGTH_SHORT).show();
    }

    private void saveAutoSaveSetting() {
        boolean autoSave = cbAutoSave.isChecked();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_AUTO_SAVE, autoSave);
        editor.apply();
    }

    private void saveLoginState(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // 结束当前Activity，避免返回登录界面
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
