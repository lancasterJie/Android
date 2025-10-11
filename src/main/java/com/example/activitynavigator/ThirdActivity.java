package com.example.activitynavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    private EditText etInput;//保存输入的信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        etInput = findViewById(R.id.et_input);
        Button ReturnResultButton = findViewById(R.id.return_result);
        Button ReturnCancel = findViewById(R.id.return_cancel);

        ReturnResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText =etInput.getText().toString();
                //创建Intent携带返回数据
                Intent data=new Intent();
                data.putExtra("result_data",inputText);
                //设置返回结果
                setResult(RESULT_OK,data);
                finish();
            }
        });
        //取消返回
        ReturnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
