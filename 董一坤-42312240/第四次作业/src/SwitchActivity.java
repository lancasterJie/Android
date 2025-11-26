package com.dyk.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SwitchActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_switch);

        findViewById(R.id.btn_file).setOnClickListener(this);
        findViewById(R.id.btn_pref).setOnClickListener(this);
        findViewById(R.id.btn_sql).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int v_id = v.getId();

        Intent intent = null;
        if(v_id == R.id.btn_file){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(v_id == R.id.btn_pref){
            intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
        }else if(v_id == R.id.btn_sql){
            intent = new Intent(this,SQLActivity.class);
            startActivity(intent);
        }
    }
}