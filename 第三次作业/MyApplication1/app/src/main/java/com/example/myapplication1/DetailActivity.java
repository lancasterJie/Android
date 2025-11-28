package com.example.myapplication1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_msg = findViewById(R.id.tv_msg);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String name = bundle.getString("name", "默认名称");
            int age = bundle.getInt("age", 0);
            boolean isStudent = bundle.getBoolean("isStudent", false);

            if(isStudent) {
                tv_msg.setText("姓名：" + name + "，年龄：" + age + "，学生");
            }
            else{
                tv_msg.setText("姓名：" + name + "，年龄：" + age + "，非学生");
            }
        }



    }
}