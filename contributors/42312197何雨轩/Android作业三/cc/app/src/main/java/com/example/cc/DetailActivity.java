package com.example.cc;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 从Intent获取数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name", "");
            String age = extras.getString("age", "");
            String job = extras.getString("job", "");

            // 显示数据
            TextView tvName = findViewById(R.id.tvName);
            TextView tvAge = findViewById(R.id.tvAge);
            TextView tvJob = findViewById(R.id.tvJob);

            tvName.setText("姓名: " + name);
            tvAge.setText("年龄: " + age);
            tvJob.setText("职业: " + job);
        }
    }
}