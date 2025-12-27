package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    Button back;

    Button imply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back = findViewById(R.id.返回到主页);
        back.setOnClickListener(new View.OnClickListener() {//在 secondActivity 中，为该按钮设置点击事件，并使用 finish()方法结束当前Activity并返回MainActivity
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        class Listener implements View.OnClickListener//在 secondActivity 的代码中，为名为"隐式跳转"的按钮设置点击事件监听器
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.隐式跳转)
                {
                    Intent itimply =new Intent("com.example.action.VIEW_THIRD ACTIVITY");
                    startActivity(itimply);
                }
            }
        }
        imply=findViewById(R.id.隐式跳转);
        imply.setOnClickListener(new Listener());
    }
}
