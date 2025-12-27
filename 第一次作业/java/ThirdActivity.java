package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    Button back;

    EditText text;

    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back=findViewById(R.id.返回结果);

        text=findViewById(R.id.et);

        cancel=findViewById(R.id.返回取消);

        back.setOnClickListener(new View.OnClickListener() {//为“返回结果”按钮设置点击事件
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.putExtra("result",text.getText().toString());
                setResult(ThirdActivity.RESULT_OK,it);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()//为“取消返回”按钮设置点击事件
        {
            @Override
            public void onClick(View v)
            {
                setResult(ThirdActivity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
