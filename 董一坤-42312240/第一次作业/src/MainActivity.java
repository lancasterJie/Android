package com.dyk.activitynavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);

        findViewById(R.id.btn_go_to_second_activity).setOnClickListener(this);
        findViewById(R.id.btn_go_to_third_activity).setOnClickListener(this);
        findViewById(R.id.btn_go_to_third_activity).setOnLongClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123 ) {
            if(resultCode == Activity.RESULT_OK && data != null){
                String result = data.getStringExtra("result");
                tv_result.setText("返回的数据为："+result);
            }else if(resultCode == Activity.RESULT_CANCELED){
                tv_result.setText("什么数据也没有返回");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_go_to_second_activity){
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.btn_go_to_third_activity){
            Intent intent = new Intent(this,ThirdActivity.class);
            startActivityForResult(intent,123);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
        return false;
    }
}