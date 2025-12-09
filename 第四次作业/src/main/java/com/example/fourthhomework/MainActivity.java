package com.example.fourthhomework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // 控件引用（包含所有按钮：文件读写、设置、数据库相关）
    private EditText etNote;
    private Button btnSave;
    private Button btnLoad;
    private Button btnSettings;
    private Button btnSaveDb; // 保存到数据库
    private Button btnViewRecords; // 查看记录列表

    // 文件名常量
    private static final String FILE_NAME = "note.txt";
    // SharedPreferences 实例
    private SharedPreferences sp;
    // 数据库操作类实例
    private RecordDao recordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 SharedPreferences（设置相关）
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        // 初始化数据库操作类
        recordDao = new RecordDao(this);

        // 初始化所有控件（含数据库相关按钮）
        initViews();

        // 设置所有按钮点击事件
        setClickListeners();
    }

    /**
     * 每次回到主界面时，更新标题栏（读取最新的用户名）
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateTitle();
    }

    /**
     * 初始化所有控件（与布局文件中的 id 一一对应）
     */
    private void initViews() {
        etNote = findViewById(R.id.et_note);
        btnSave = findViewById(R.id.btn_save);
        btnLoad = findViewById(R.id.btn_load);
        btnSettings = findViewById(R.id.btn_settings);
        btnSaveDb = findViewById(R.id.btn_save_db); // 新增：数据库保存按钮
        btnViewRecords = findViewById(R.id.btn_view_records); // 新增：查看记录按钮
    }

    /**
     * 设置所有按钮的点击监听器
     */
    private void setClickListeners() {
        // 1. 保存到文件（原功能）
        btnSave.setOnClickListener(v -> {
            String content = etNote.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入要保存的内容", Toast.LENGTH_SHORT).show();
                return;
            }
            saveToFile(content);
        });

        // 2. 从文件加载（原功能）
        btnLoad.setOnClickListener(v -> {
            String content = loadFromFile();
            if (content != null) {
                etNote.setText(content);
                etNote.setSelection(content.length()); // 光标移到文本末尾
            }
        });

        // 3. 跳转到设置界面（原功能）
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // 4. 新增：保存到数据库
        btnSaveDb.setOnClickListener(v -> {
            String content = etNote.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入要保存的内容", Toast.LENGTH_SHORT).show();
                return;
            }

            // 生成标题：取前10字，超出部分用"..."代替
            String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
            // 获取当前时间（格式：yyyy-MM-dd HH:mm:ss）
            String currentTime = getCurrentTime();

            // 创建记录对象并保存到数据库
            Record record = new Record(title, content, currentTime);
            long insertId = recordDao.addRecord(record);

            // 保存结果提示
            if (insertId != -1) {
                Toast.makeText(MainActivity.this, "保存到数据库成功", Toast.LENGTH_SHORT).show();
                etNote.setText(""); // 清空输入框（可选，提升用户体验）
            } else {
                Toast.makeText(MainActivity.this, "保存到数据库失败", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. 新增：跳转到记录列表界面
        btnViewRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordListActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 从 SharedPreferences 读取用户名，更新标题栏
     */
    private void updateTitle() {
        String userName = sp.getString("user_name", "笔记");
        setTitle("欢迎，" + userName); // 标题栏显示「欢迎，XXX」
    }

    /**
     * 自动保存逻辑：当用户返回/退出应用时，若开启自动保存则保存到文件
     */
    @Override
    protected void onPause() {
        super.onPause();
        autoSaveToFile();
    }

    /**
     * 自动保存到文件（根据设置中的开关状态）
     */
    private void autoSaveToFile() {
        boolean isAutoSave = sp.getBoolean("auto_save", false);
        if (isAutoSave) {
            String content = etNote.getText().toString().trim();
            if (!content.isEmpty()) {
                saveToFile(content);
                // 自动保存不弹窗提示，避免打扰用户
            }
        }
    }

    /**
     * 获取当前时间（用于数据库记录的时间字段）
     */
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }

    // ---------------------- 原文件读写方法（保持不变）----------------------
    private void saveToFile(String content) {
        FileOutputStream fos = null;
        try {
            // 打开文件输出流（私有模式，仅本应用可访问）
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes()); // 写入内容
            Toast.makeText(this, "文件保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件保存失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) fos.close(); // 关闭流，释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String loadFromFile() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME); // 打开文件输入流
            byte[] buffer = new byte[fis.available()]; // 获取文件长度，创建缓冲区
            fis.read(buffer); // 读取文件内容到缓冲区
            return new String(buffer); // 转换为字符串返回
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件读取失败：文件不存在或出错", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            try {
                if (fis != null) fis.close(); // 关闭流，释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}