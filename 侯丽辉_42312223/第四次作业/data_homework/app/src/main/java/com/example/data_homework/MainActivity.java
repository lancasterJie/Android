package com.example.data_homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "note.txt";
    private static final String PREFS_NAME = "settings";
    private static final String KEY_AUTO_SAVE = "auto_save";
    private static final String KEY_USERNAME = "user_name";

    private EditText editNote;
    private Button buttonSaveFile, buttonLoadFile, buttonSaveNote;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        editNote = findViewById(R.id.edit_note);
        buttonSaveFile = findViewById(R.id.button_save_file);
        buttonLoadFile = findViewById(R.id.button_load_file);
        buttonSaveNote = findViewById(R.id.button_save_note);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Set up button click listeners
        buttonSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        buttonLoadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
            }
        });

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update toolbar title with username from SharedPreferences
        updateToolbarTitle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Auto-save if enabled in settings
        boolean autoSave = sharedPreferences.getBoolean(KEY_AUTO_SAVE, false);
        if (autoSave) {
            saveToFile();
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_notes) {
            Intent intent = new Intent(MainActivity.this, RecordListActivity.class);
            startActivity(intent);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void updateToolbarTitle() {
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        if (!username.isEmpty()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + username);
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.app_name);
            }
        }
    }

    private void saveToFile() {
        String text = editNote.getText().toString();
        
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, R.string.file_saved, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.error_saving, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            
            String text = new String(buffer);
            editNote.setText(text);
            Toast.makeText(this, "笔记加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.file_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToDatabase() {
        String content = editNote.getText().toString().trim();
        
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入一些文字", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Generate title from first 20 characters or less
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;
        
        // Insert into database
        long id = databaseHelper.insertNote(title, content);
        
        if (id != -1) {
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
            editNote.setText(""); // Clear the text field
        } else {
            Toast.makeText(this, R.string.error_saving, Toast.LENGTH_SHORT).show();
        }
    }
}