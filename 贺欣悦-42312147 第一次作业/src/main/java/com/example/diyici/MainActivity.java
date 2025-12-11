package com.example.diyici;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;//引入Button
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4, b5;//声明变量
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

        b1 = findViewById(R.id.button1);//赋值按钮
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        tv = findViewById(R.id.tv);
        MyListener1 nl = new MyListener1();
        //MyListener n = new MyListener();
        b1.setOnClickListener(nl);//设置监听器
        b2.setOnClickListener(nl);
        b3.setOnClickListener(nl);
        b4.setOnClickListener(nl);
        b5.setOnClickListener(nl);
        //b5.setOnClickListener(n);
        //b1.setOnClickListener();

    }

    class MyListener1 implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button1) {
                Toast.makeText(MainActivity.this, "Android", Toast.LENGTH_LONG).show();
            } else if (view.getId() == R.id.button2) {
                Intent it = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
                //Toast.makeText(MainActivity.this, "Harmony", Toast.LENGTH_LONG).show();
            } else if (view.getId() == R.id.button3) {
                Intent itbro = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.snnu.edu.cn"));
                startActivity(itbro);

            } else if (view.getId() == R.id.button4) {
                Intent itimp = new Intent("com.example.aedfrgve.avtivitythird");
                startActivity(itimp);

            } else if (view.getId() == R.id.button5) {
                Intent itresult = new Intent(MainActivity.this, ForthActivity.class);
                startActivityForResult(itresult, 101);

            }
        }
    }//设置监听器

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101) {
                String result = data.getStringExtra("result_data");
                tv.setText(result);
            }
        } else if(resultCode == Activity.RESULT_CANCELED){
            if (requestCode == 101) {
                finish();
            }
        }

    }


    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button5) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_LONG).show();
            }
        }
    }
}