package com.example.test;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101)
        {
            if(resultCode== MainActivity.RESULT_CANCELED){
                Toast.makeText(this, "用户取消了操作", Toast.LENGTH_SHORT).show();
            }else if(resultCode == 3)
            {
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);
            }
        }
    }//有返回值的跳转
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        class MyListener1 implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button1) {
                    Intent it = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(it);
                }
                else if(view.getId() == R.id.button4){
                    Intent itresult = new Intent(MainActivity.this, ThirdActivity.class);
                    startActivityForResult(itresult,101);
                }
            }
        }
        MyListener1 ml = new MyListener1();
        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(ml);
        b2 = findViewById(R.id.button4);
        b2.setOnClickListener(ml);
        b2.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View view) {
                // 显示Toast提示
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转!", Toast.LENGTH_SHORT).show();

                // 启动ThirdActivity并期待返回结果
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, 101);

                return true; // 返回true表示已处理长按事件
            }
        });
        tv = findViewById(R.id.backtext);
    }
}
