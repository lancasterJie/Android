package com.example.notepadapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        TextView textContent = findViewById(R.id.textContent);
        String content = getIntent().getStringExtra("content");
        textContent.setText(content);
    }
}