package com.example.sql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 主界面：负责文本编辑、文件读写以及保存记录。
 */
public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "settings";
    private static final String NOTE_FILE = "note.txt";

    private EditText editNote;
    private CheckBox checkAutoSaveInline;
    private EditText editUserNameInline;
    private EditText editPasswordInline;
    private database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNote = findViewById(R.id.edit_note);
        Button btnSaveFile = findViewById(R.id.btn_save_file);
        Button btnLoadFile = findViewById(R.id.btn_load_file);
        Button btnSaveRecord = findViewById(R.id.btn_save_record);
        checkAutoSaveInline = findViewById(R.id.check_auto_save_inline);
        editUserNameInline = findViewById(R.id.edit_user_name_inline);
        editPasswordInline = findViewById(R.id.edit_password_inline);
        Button btnSaveSettingsInline = findViewById(R.id.btn_save_settings_inline);
        Button btnLoginJumpInline = findViewById(R.id.btn_login_jump_inline);

        dbHelper = new database(this);

        btnSaveFile.setOnClickListener(v -> saveToFileWithToast());
        btnLoadFile.setOnClickListener(v -> loadFromFile());
        btnSaveRecord.setOnClickListener(v -> saveToDatabase());
        if (btnSaveSettingsInline != null) {
            btnSaveSettingsInline.setOnClickListener(v -> {
                saveSettingsFromInlinePanel();
                Toast.makeText(this, R.string.tip_settings_saved, Toast.LENGTH_SHORT).show();
            });
        }
        if (btnLoginJumpInline != null) {
            btnLoginJumpInline.setOnClickListener(v -> startActivity(new Intent(this, RecordListActivity.class)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userName = sp.getString("user_name", getString(R.string.app_name));
        setTitle(getString(R.string.title_main_with_user, userName));
        loadInlineSettings(sp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (sp.getBoolean("auto_save", false)) {
            saveToFile(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void saveToFileWithToast() {
        boolean success = saveToFile(true);
        if (success) {
            Toast.makeText(this, R.string.tip_saved_to_file, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean saveToFile(boolean showError) {
        String text = editNote.getText().toString();
        try (java.io.FileOutputStream fos = openFileOutput(NOTE_FILE, MODE_PRIVATE)) {
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            if (showError) {
                Toast.makeText(this, getString(R.string.error_save_file, e.getMessage()), Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(NOTE_FILE)) {
            String content = readAll(fis);
            editNote.setText(content);
            Toast.makeText(this, R.string.tip_loaded_from_file, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.error_file_missing, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.error_load_file, e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    private String readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        return bos.toString(StandardCharsets.UTF_8.name());
    }

    private void saveToDatabase() {
        String content = editNote.getText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_SHORT).show();
            return;
        }
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        long id = dbHelper.insertRecord(title, content, time);
        if (id != -1) {
            Toast.makeText(this, R.string.tip_saved_to_db, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.error_save_db, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInlineSettings(SharedPreferences sp) {
        if (checkAutoSaveInline == null) {
            return;
        }
        boolean autoSave = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", "");
        String password = sp.getString("passwd", "");

        checkAutoSaveInline.setChecked(autoSave);
        if (editUserNameInline != null) {
            editUserNameInline.setText(userName);
        }
        if (editPasswordInline != null) {
            editPasswordInline.setText(password);
        }
    }

    private void saveSettingsFromInlinePanel() {
        if (checkAutoSaveInline == null) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userNameFromField = editUserNameInline != null ? editUserNameInline.getText().toString() : getString(R.string.app_name);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", checkAutoSaveInline.isChecked());
        if (editUserNameInline != null) {
            editor.putString("user_name", editUserNameInline.getText().toString());
        }
        if (editPasswordInline != null) {
            editor.putString("passwd", editPasswordInline.getText().toString());
        }
        editor.apply();

        setTitle(getString(R.string.title_main_with_user,
                TextUtils.isEmpty(userNameFromField) ? getString(R.string.app_name) : userNameFromField));
    }
}