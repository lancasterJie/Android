package com.example.homework1;

import android.annotation.SuppressLint;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView tv1;

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

        // 正确初始化所有控件（必须在setContentView之后）
        Button b1 = findViewById(R.id.button1);
        Button showB = findViewById(R.id.button3);
        tv1 = findViewById(R.id.show_text);

        MyClickListener nl1 = new MyClickListener();
        MyLongClickListener nl2 = new MyLongClickListener();
        b1.setOnClickListener(nl1);
        showB.setOnClickListener(nl1);
        showB.setOnLongClickListener(nl2);
    }

    class MyLongClickListener implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            if(v.getId()==R.id.button3){
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.button1){
                Intent it=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
            }
            else if(v.getId()==R.id.button3){
                Intent it1=new Intent(MainActivity.this,ThirdActivit.class);
                startActivityForResult(it1,101);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == 101){
                String back = data.getStringExtra("result");
                tv1.setText(back);
            }
        }
        else if(resultCode == Activity.RESULT_CANCELED){
            String Show = "用户已取消返回值操作，请重新进行";
            tv1.setText(Show);
        }
    }
}