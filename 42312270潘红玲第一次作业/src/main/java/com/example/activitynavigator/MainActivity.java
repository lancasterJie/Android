package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_THIRD=101;//请求码
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button JumpButton1 = findViewById(R.id.JumpButton1);//显示跳转按钮
        JumpButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        Button JumpButton2 = findViewById(R.id.JumpButton2);//带结果返回按钮
        tvResult = findViewById(R.id.tv_result);//存储返回的输入信息

        JumpButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent,REQUEST_CODE_THIRD);//带结果返回
            }
        });
        JumpButton2.setOnLongClickListener(new View.OnLongClickListener(){//长按
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_THIRD){
            if (resultCode==RESULT_OK && data!=null) {
                String resultData = data.getStringExtra("result_data");//正常带结果返回
                tvResult.setText("返回结果" + resultData);
            }else if(resultCode==RESULT_CANCELED){//用户取消操作
                tvResult.setText("用户取消了操作");
            }
        }
    }
}