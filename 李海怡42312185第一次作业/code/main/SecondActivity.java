package com.example.test;

import android.annotation.SuppressLint;
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
    Button back, hide;
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
        class MyListener1 implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button2) {
                    finish();
                }
                else if(view.getId() == R.id.button3){
                    Intent itimp=new Intent("com.example.action.VIEW_THIRD_ACTIVITY");
                    startActivity(itimp);
                }
            }
        }
        MyListener1 ml = new MyListener1();
        back = findViewById(R.id.button2);
        back.setOnClickListener(ml);
        hide = findViewById(R.id.button3);
        hide.setOnClickListener(ml);
    }
}
