package com.example.work4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView textTitle, textContent, textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        initViews();
        displayRecordDetails();
    }

    private void initViews() {
        textTitle = findViewById(R.id.textTitle);
        textContent = findViewById(R.id.textContent);
        textTime = findViewById(R.id.textTime);
    }

    private void displayRecordDetails() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String time = intent.getStringExtra("time");

        textTitle.setText(title);
        textContent.setText(content);
        textTime.setText("创建时间: " + time);
    }
}