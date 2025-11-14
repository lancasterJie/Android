package com.example.three;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		TextView tv = findViewById(R.id.tv_detail);
		String username = getIntent().getStringExtra("username");
		int age = getIntent().getIntExtra("age", 0);
		boolean isStudent = getIntent().getBooleanExtra("isStudent", false);

		String show = "用户名: " + username + "\n年龄: " + age + "\n是否学生: " + isStudent;
		tv.setText(show);
	}
}


