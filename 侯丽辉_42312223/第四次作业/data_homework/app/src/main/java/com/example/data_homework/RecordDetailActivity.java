package com.example.data_homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordDetailActivity extends AppCompatActivity {
    private EditText editTitle, editContent;
    private TextView textTimestamp;
    private Button buttonSave, buttonDelete;
    private DatabaseHelper databaseHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_record_detail);
        }

        // Initialize views
        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        textTimestamp = findViewById(R.id.text_timestamp);
        buttonSave = findViewById(R.id.button_save);
        buttonDelete = findViewById(R.id.button_delete);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get data from intent
        noteId = getIntent().getIntExtra("note_id", -1);
        String title = getIntent().getStringExtra("note_title");
        String content = getIntent().getStringExtra("note_content");
        String timestamp = getIntent().getStringExtra("note_time");

        // Display data
        editTitle.setText(title);
        editContent.setText(content);
        textTimestamp.setText("创建时间：" + formatDate(timestamp));

        // Set up button listeners
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper.Note note = new DatabaseHelper.Note();
        note.setId(noteId);
        note.setTitle(title);
        note.setContent(content);

        int result = databaseHelper.updateNote(note);
        
        if (result > 0) {
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_saving, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void deleteNote() {
        databaseHelper.deleteNote(noteId);
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

    private String formatDate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return timestamp;
        }
    }
}
