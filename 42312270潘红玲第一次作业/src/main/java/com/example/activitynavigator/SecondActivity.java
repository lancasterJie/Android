package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private static final String ACTION_VIEW_THIRD_ACTIVITY = "com.example.action.VIEW_THIRD_ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Button BackButton1 = findViewById(R.id.back1);//返回主页按钮
        Button ImplicitButton = findViewById(R.id.implicitButton);//隐式跳转按钮

        BackButton1.setOnClickListener(v->{
            finish();
        });

        ImplicitButton.setOnClickListener(v->{//隐式跳转
            Intent intent =new Intent();
            intent.setAction(ACTION_VIEW_THIRD_ACTIVITY);//设置action
            intent.addCategory(Intent.CATEGORY_DEFAULT);//设置category

            startActivity(intent);
        });
    }
}
