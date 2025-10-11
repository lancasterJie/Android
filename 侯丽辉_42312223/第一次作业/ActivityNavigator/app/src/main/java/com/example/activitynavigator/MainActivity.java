package com.example.activitynavigator;

//import android.app.ComponentCaller;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    Button b1, b2;
    TextView tvResult;

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
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        MyListener1 nl = new MyListener1();
        b1.setOnClickListener(nl);
        b2.setOnClickListener(nl);
//加分项：长按监听器
        b2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button1) {//显式跳转SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.button2) {//启动带返回结果的跳转
                Intent it = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(it, 1);

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==REQUEST_CODE){
            if(requestCode==RESULT_OK){
                //成功返回结果
                String resultstring = data.getStringExtra("result");
                tvResult.setText("返回结果:"+resultstring);
                Toast.makeText(this,"成功接收返回数据",Toast.LENGTH_SHORT).show();
            }else if(resultCode==RESULT_CANCELED){
                tvResult.setText("操作已取消");
                Toast.makeText(this,"用户取消了操作",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
