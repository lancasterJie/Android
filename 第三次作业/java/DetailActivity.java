package com.example.myproject3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    TextView textView;

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

        textView=findViewById(R.id.text);

        Intent detail=getIntent();
        Bundle bundle=detail.getExtras();

        if(bundle!=null)
        {
            String student_name=bundle.getString("student_name");
            int age=bundle.getInt("student_age");
            String student=bundle.getString("is_student");

            String displayString="学生姓名:"+student_name+"\n学生年龄:"+age+"\n是否为学生:"+student;
            textView.setText(displayString);
        }
    }
}
