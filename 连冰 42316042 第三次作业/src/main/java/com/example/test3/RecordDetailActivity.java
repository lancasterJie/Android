package com.example.test3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        TextView textTitle = findViewById(R.id.textDetailTitle);
        TextView textTime = findViewById(R.id.textDetailTime);
        TextView textContent = findViewById(R.id.textDetailContent);

        String title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");
        String content = getIntent().getStringExtra("content");

        textTitle.setText(title);
        textTime.setText(time);
        textContent.setText(content);
    }
}

