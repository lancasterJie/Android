package com.example.app1;

import android.app.Activity;
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
    Button b1,b2;
    TextView tv;
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
        //初始化按钮并且设置点击监听器
        b1=findViewById(R.id.button1);
        MyListener listener=new MyListener();
        b1.setOnClickListener(listener);
        b2=findViewById(R.id.button2);
        b2.setOnClickListener(listener);
        // 加分项：为 button2 添加**长按监听器**
        b2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                return true; // 消费长按事件，避免触发点击事件
            }
        });
        tv=findViewById(R.id.tv);
    }
    private  class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.button1) {
                //显示
                Intent it =new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
            } else if (view.getId()==R.id.button2) {
                Intent its =new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(its,1);

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // 只处理“启动 ThirdActivity”的返回（请求码为 1）
            if (resultCode == Activity.RESULT_OK) {
                // 正常返回：获取并显示结果
                String result = data.getStringExtra("result");
                tv.setText("获取到的结果：" + result);
                Toast.makeText(this, "ThirdActivity 正常返回结果", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                tv.setText("ThirdActivity 操作已取消");
                Toast.makeText(this, "ThirdActivity 取消了操作", Toast.LENGTH_SHORT).show();
            }
        }
    }
}