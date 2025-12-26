package com.example.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private EditText editText;
    private Button btnSave, btnLoad, btnLogout;

    private TextView tvTitle;
    private SharedPreferences sharedPreferences;
    private static final String FILE_NAME = "note.txt";
    private static final String PREFS_NAME = "user_settings";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AUTO_SAVE = "auto_save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 检查登录状态
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        checkLoginStatus();

        initViews();

        setupClickListeners();

        setupBottomNavigation();
    }
    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (!isLoggedIn) {
            // 未登录，跳转到登录界面
            goToLoginActivity();
            return;
        }
    }
    /**
     * 加载用户设置
     */
    private void loadUserSettings() {
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        if (!username.isEmpty()) {
            tvTitle.setText("欢迎，" + username);
        }
    }
    /**
     * 检查是否开启自动保存
     */
    private boolean isAutoSaveEnabled() {
        return sharedPreferences.getBoolean(KEY_AUTO_SAVE, false);
    }

    /**
     * 自动保存文件
     */
    private void autoSaveToFile() {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 跳转到登录界面
     */
    private void goToLoginActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }
    private void initViews() {
        editText = findViewById(R.id.editText);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        btnLogout = findViewById(R.id.btnLogout);
    }


    private void setupClickListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromfile();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }
    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
        goToLoginActivity();
    }
    private void saveToFile(){
        String text = editText.getText().toString().trim();

        if(text.isEmpty()){
            Toast.makeText(this, "请输入要保存的内容", Toast.LENGTH_LONG).show();
            return;
        }

        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            Toast.makeText(this, "内容保存成功", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void loadFromfile() {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try{
            fileInputStream = openFileInput(FILE_NAME);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line)
                        .append("\n");
            }
            if(stringBuilder.length() > 0){
                stringBuilder.setLength(stringBuilder.length()-1);
            }
            editText.setText(stringBuilder.toString());
            Toast.makeText(this, "文件加载成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof java.io.FileNotFoundException) {
                Toast.makeText(this, "文件不存在，请先保存文件", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "读取文件时发生错误：" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            e.printStackTrace();
        } finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home); // 设置主页为选中状态

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                // 已经在主页，不做任何操作
                return true;
            } else if (itemId == R.id.menu_records) {
                // 跳转到记录列表
                Intent intent = new Intent(MainActivity.this, RecordListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }

            return false;
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        // 检查是否开启自动保存
        if (isAutoSaveEnabled()) {
            autoSaveToFile();
        }
    }
}
