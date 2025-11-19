package com.example.test3;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String NOTE_FILE_NAME = "note.txt";

    private EditText editNote;
    private TextView textStatus;
    private MyDbHelper dbHelper;
    private boolean autoSaveEnabled;

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

        editNote = findViewById(R.id.editNote);
        textStatus = findViewById(R.id.textStatus);
        Button btnSaveFile = findViewById(R.id.btnSaveFile);
        Button btnLoadFile = findViewById(R.id.btnLoadFile);
        Button btnSaveRecord = findViewById(R.id.btnSaveRecord);

        dbHelper = new MyDbHelper(this);

        btnSaveFile.setOnClickListener(v -> saveNoteToFile(true));
        btnLoadFile.setOnClickListener(v -> loadNoteFromFile());
        btnSaveRecord.setOnClickListener(v -> saveNoteToDatabase());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        autoSaveEnabled = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", getString(R.string.default_user_name));
        if (TextUtils.isEmpty(userName)) {
            userName = getString(R.string.default_user_name);
        }
        setTitle(getString(R.string.main_title_format, userName));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (autoSaveEnabled) {
            saveNoteToFile(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    private void saveNoteToFile(boolean showToast) {
        String text = editNote.getText().toString();
        try (FileOutputStream fos = openFileOutput(NOTE_FILE_NAME, MODE_PRIVATE)) {
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            if (showToast) {
                showToast(getString(R.string.msg_save_success));
            }
            updateStatus(getString(R.string.status_saved, getCurrentTime()));
        } catch (IOException e) {
            showToast(getString(R.string.msg_save_error));
        }
    }

    private void loadNoteFromFile() {
        try (FileInputStream fis = openFileInput(NOTE_FILE_NAME);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            String text = baos.toString(StandardCharsets.UTF_8.name());
            editNote.setText(text);
            updateStatus(getString(R.string.status_loaded, getCurrentTime()));
            showToast(getString(R.string.msg_load_success));
        } catch (FileNotFoundException e) {
            showToast(getString(R.string.msg_file_not_found));
        } catch (IOException e) {
            showToast(getString(R.string.msg_load_error));
        }
    }

    private void saveNoteToDatabase() {
        String content = editNote.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast(getString(R.string.msg_empty_note));
            return;
        }

        String title = content.length() > 15 ? content.substring(0, 15) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("time", time);

        long rowId = dbHelper.getWritableDatabase().insert("records", null, values);
        if (rowId != -1) {
            showToast(getString(R.string.msg_record_saved));
            updateStatus(getString(R.string.status_record_saved, time));
        } else {
            showToast(getString(R.string.msg_record_error));
        }
    }

    private void updateStatus(String message) {
        textStatus.setText(message);
    }

    private String getCurrentTime() {
        return DateFormat.getTimeInstance().format(new Date());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}