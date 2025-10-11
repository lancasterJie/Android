package com.example.activitynavigator;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {
    EditText et_input;

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
        et_input = findViewById(R.id.et_input);
        setupClickListeners();
    }
    private void setupClickListeners() {
        //返回结果按钮
        Button b_return_result = findViewById(R.id.return_result);
        b_return_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult();
            }
        });
        //返回取消按钮
        Button b_return_cancle = findViewById(R.id.return_cancle);
        b_return_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }


    private void returnResult(){
        String inputText= et_input.getText().toString().trim();

        //创建返回数据的Intent
        Intent resultIntent=new Intent();
        resultIntent.putExtra("result_data",inputText);

        //设置结果并结束Activity
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    //处理返回键，设置取消操作
    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}