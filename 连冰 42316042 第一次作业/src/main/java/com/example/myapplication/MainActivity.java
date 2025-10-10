package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
    public static final String TAB = "ajkhwdq";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAB,"ONSTART");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAB,"ONSTOP");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAB,"ONResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAB,"ONDESTROY");
    }

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
        Log.d(TAB,"oncreate");
        b1 = findViewById(R.id.button1);
        MyListener1 listener1 = new MyListener1();
        b1.setOnClickListener(listener1);
        b2 = findViewById(R.id.button2);
        b2.setOnClickListener(listener1);
        tv=findViewById(R.id.tv);
        b2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                return true; // 消费长按事件，避免触发点击事件
            }
        });

    }

    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button1) {
                Intent it = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(it);
                Toast.makeText(MainActivity.this, "已经跳转到SecondActivity", Toast.LENGTH_LONG).show();
            } else if (v.getId()==R.id.button2) {
                Intent intent=new Intent(MainActivity.this,ThirdActivity.class);
                startActivityForResult(intent,101);

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==101){
                String result=data.getStringExtra("result");
                tv.setText(result);
            }
        }
    }
}