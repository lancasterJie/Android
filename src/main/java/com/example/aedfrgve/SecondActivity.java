package com.example.aedfrgve;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    Button button_back, button_im;

    @SuppressLint("MissingInflatedId")
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

        button_back = findViewById(R.id.button2_back_to_main);//赋值按钮
        button_im = findViewById(R.id.button2_im);
        MyListener2 nl1 = new MyListener2();
        button_back.setOnClickListener(nl1);//设置监听器
        button_im.setOnClickListener(nl1);
    }

    class MyListener2 implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button2_back_to_main) {
                finish();
            } else if (view.getId() == R.id.button2_im) {
                Intent itimp = new Intent("com.example.action.VIEW_FORTH_ACTIVITY");
                itimp.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(itimp);
            }
        }//设置监听器
    }
}