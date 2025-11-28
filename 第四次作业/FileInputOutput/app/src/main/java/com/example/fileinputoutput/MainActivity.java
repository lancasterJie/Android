package com.example.fileinputoutput;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView teTitle;
    private EditText et;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 设置Toolbar为ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        teTitle = findViewById(R.id.te_title);
        et = findViewById(R.id.et);
        
        // 初始化数据库帮助类
        dbHelper = new MyDbHelper(this);

        Button saveBtn = findViewById(R.id.bt_save);
        Button loadBtn = findViewById(R.id.bt_load);
        Button btSettings = findViewById(R.id.bt_settings);
        Button btSaveRecord = findViewById(R.id.bt_save_record);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(et.getText().toString());
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content;
                content = load("note.txt");

                et.setText(content);
            }
        });

        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(it);
            }
        });
        
        // 保存为记录按钮点击事件
        btSaveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAsRecord();
            }
        });
    }
    
    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    /**
     * 菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.menu_settings) {
            // 跳转到设置界面
            Intent it = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(it);
            return true;
        } else if (id == R.id.menu_record_list) {
            // 跳转到记录列表界面
            Intent it = new Intent(MainActivity.this, RecordListActivity.class);
            startActivity(it);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 将当前EditText内容保存为数据库记录
     */
    private void saveAsRecord() {
        String content = et.getText().toString().trim();
        
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // 生成标题（前10个字符）
        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());
        
        // 保存到数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO records (title, content, time) VALUES (?, ?, ?)";
        db.execSQL(sql, new Object[]{title, content, time});
        db.close();
        
        Toast.makeText(this, "保存记录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        String title = sp.getString("user_name", "无");

        teTitle.setText(title);
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        if (sp.getBoolean("auto_save", false) && !et.getText().toString().isEmpty()){
            save(et.getText().toString());
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);

        Log.d("assssaaa","111");

        if (sp.getBoolean("auto_save", false) && !et.getText().toString().isEmpty()){
            save(et.getText().toString());
        }
    }

    private void save(String inputString) {
        String fileName = "note.txt";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(inputString.getBytes());
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "文件不存在！", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
            }
            }
        }
    }

    private String load(String fileName) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(fileName);
            byte[] buffer = new byte[100];
            int byteCount = fis.read(buffer);
            String str2 = new String(buffer, 0, byteCount);
            return str2;
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this,"文件不存在！",Toast.LENGTH_LONG).show();
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}