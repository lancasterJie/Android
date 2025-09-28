package com.dyk.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.btn_go_back_to_main_activity).setOnClickListener(this);
        findViewById(R.id.btn_go_to_third_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_go_back_to_main_activity){
            finish();
        }else if(v.getId() == R.id.btn_go_to_third_activity){
            Intent intent = new Intent("android.intent.action.VIEW_THIRD_ACTIVITY");
            startActivity(intent);
        }
    }
}