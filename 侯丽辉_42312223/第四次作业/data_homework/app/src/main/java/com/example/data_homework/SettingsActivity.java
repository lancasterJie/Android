package com.example.data_homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "settings";
    private static final String KEY_AUTO_SAVE = "auto_save";
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_PASSWORD = "password";

    private CheckBox checkBoxAutoSave;
    private EditText editTextUsername, editTextPassword;
    private Button buttonSave;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_settings);
        }

        // Initialize views
        checkBoxAutoSave = findViewById(R.id.checkbox_auto_save);
        editTextUsername = findViewById(R.id.edit_username);
        editTextPassword = findViewById(R.id.edit_password);
        buttonSave = findViewById(R.id.button_save_settings);

        // Get SharedPreferences instance
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Load saved settings
        loadSettings();

        // Set click listener for save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void loadSettings() {
        // Load saved settings or use default values
        boolean autoSave = sharedPreferences.getBoolean(KEY_AUTO_SAVE, false);
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        String password = sharedPreferences.getString(KEY_PASSWORD, "");

        // Update UI
        checkBoxAutoSave.setChecked(autoSave);
        editTextUsername.setText(username);
        editTextPassword.setText(password);
    }

    private void saveSettings() {
        // Get values from UI
        boolean autoSave = checkBoxAutoSave.isChecked();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Save to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_AUTO_SAVE, autoSave);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();

        // Update MainActivity title if username is not empty
        if (!username.isEmpty()) {
            // Notify MainActivity to update the title
            setResult(RESULT_OK);
        }

        // Show confirmation
        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
