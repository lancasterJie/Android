package com.example.activitynavigator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    //action常量
    public static final String ACTION_VIEW_THIRD_ACTIVITY="com.example.activitynavigator.ACTION_VIEW_THIRD_ACTIVITY";

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
        setupClickListener();
    }
    private void setupClickListener(){
//返回主页的按钮
        Button bm=findViewById(R.id.btn_back_to_main);
        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前Activity，返回main
                finish();
            }
        });
        Button bj=findViewById(R.id.btn_implicit_jump);
        bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建隐式的Intent
                Intent intent=new Intent();
                intent.setAction(ACTION_VIEW_THIRD_ACTIVITY);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                startActivity(intent);
            }
        });
    }
}