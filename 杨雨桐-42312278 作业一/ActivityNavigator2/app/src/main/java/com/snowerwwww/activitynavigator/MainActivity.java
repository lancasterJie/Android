package com.snowerwwww.activitynavigator;

import android.annotation.SuppressLint;
import android.content.Context;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;
    TextView text;

    //重写onActivityResult方法,这个方法会自动调用
    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            if(requestCode == 1){
                String result = data.getStringExtra("result");
                text.setText(result);
                System.out.println("返回结果");
            }if(requestCode == 2){
                System.out.println("返回取消");
            }
        }
    }

    @SuppressLint("MissingInflatedId")
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






        class lister implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button1){
                    Toast.makeText(MainActivity.this, "触发成功", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(it);
                }if(v.getId() == R.id.button2){
                    Intent it = new Intent(MainActivity.this,ThirtyActivity.class);
                    startActivityForResult(it,1);
                    Toast.makeText(MainActivity.this, "触发成功", Toast.LENGTH_SHORT).show();
                }


            }

        }

        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new lister());
        b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new lister());
        text = findViewById(R.id.back);

        // 为b2单独设置长按监听器
        b2.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            return true; // 表示长按事件已被处理
        });

    }



}