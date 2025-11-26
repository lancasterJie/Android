package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.data.MyDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 主界面：集中演示三种本地持久化手段
 * 1）应用私有文件 note.txt 的读写；
 * 2）SharedPreferences 设置项（昵称 / 自动保存）的读取；
 * 3）通过 {@link MyDbHelper} 进行 SQLite 记录的插入与查看入口。
 */
public class MainActivity extends AppCompatActivity {

    private static final String NOTE_FILE_NAME = "note.txt";

    private EditText editNote;
    private Button btnSaveFile;
    private Button btnLoadFile;
    private Button btnSaveRecord;
    private Button btnOpenSettings;
    private Button btnOpenRecords;

    private SharedPreferences sharedPreferences;
    private boolean autoSaveEnabled;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences 用来控制标题与“自动保存”开关
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        // SQLiteOpenHelper 只创建一次，一直复用
        dbHelper = new MyDbHelper(this);

        editNote = findViewById(R.id.edit_note);
        btnSaveFile = findViewById(R.id.btn_save_file);
        btnLoadFile = findViewById(R.id.btn_load_file);
        btnSaveRecord = findViewById(R.id.btn_save_record);
        btnOpenSettings = findViewById(R.id.btn_open_settings);
        btnOpenRecords = findViewById(R.id.btn_open_records);

        // --- 文件读写 ---
        btnSaveFile.setOnClickListener(v -> {
            if (saveToFile()) {
                Toast.makeText(this, R.string.toast_save_success, Toast.LENGTH_SHORT).show();
            }
        });

        btnLoadFile.setOnClickListener(v -> {
            if (loadFromFile()) {
                Toast.makeText(this, R.string.toast_load_success, Toast.LENGTH_SHORT).show();
            }
        });

        // --- SQLite 记录与跳转 ---
        btnSaveRecord.setOnClickListener(v -> saveCurrentTextAsRecord());
        btnOpenSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class))
        );
        btnOpenRecords.setOnClickListener(v ->
                startActivity(new Intent(this, RecordListActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        applySettings(); // 每次回到主界面都刷新标题与自动保存状态
    }

    @Override
    protected void onPause() {
        if (autoSaveEnabled) {
            saveToFile();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    /**
     * 读取 SharedPreferences，更新：
     * 1）Activity 标题（昵称）；
     * 2）自动保存开关（决定 onPause 是否写文件）。
     */
    private void applySettings() {
        String userName = sharedPreferences.getString(
                "user_name",
                getString(R.string.default_user_name)
        );
        setTitle(getString(R.string.main_title_format, userName));
        autoSaveEnabled = sharedPreferences.getBoolean("auto_save", false);
    }

    /**
     * 将 EditText 的内容写入应用私有目录下的 note.txt。
     *
     * @return 写入成功返回 true，失败返回 false 以决定是否提示。
     */
    private boolean saveToFile() {
        String text = editNote.getText().toString();
        try (FileOutputStream fos = openFileOutput(NOTE_FILE_NAME, MODE_PRIVATE)) {
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            Toast.makeText(this, R.string.toast_save_failed, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 从 note.txt 读取文本并填充到 EditText。
     *
     * @return 成功读取返回 true；文件缺失或 IO 异常返回 false。
     */
    private boolean loadFromFile() {
        try (FileInputStream fis = openFileInput(NOTE_FILE_NAME)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            editNote.setText(baos.toString(StandardCharsets.UTF_8.name()));
            return true;
        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.toast_file_missing, Toast.LENGTH_SHORT).show();
            return false;
        } catch (IOException e) {
            Toast.makeText(this, R.string.toast_load_failed, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 将当前文本保存为 SQLite 记录：截取前 20 个字符作为标题，附带时间戳。
     */
    private void saveCurrentTextAsRecord() {
        String content = editNote.getText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            Toast.makeText(this, R.string.toast_empty_content, Toast.LENGTH_SHORT).show();
            return;
        }

        String cleanTitle = content.trim();
        if (cleanTitle.length() > 20) {
            cleanTitle = cleanTitle.substring(0, 20) + "...";
        }

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());

        long id = dbHelper.insertRecord(cleanTitle, content, time);
        if (id > 0) {
            Toast.makeText(this, R.string.toast_record_saved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.toast_record_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}