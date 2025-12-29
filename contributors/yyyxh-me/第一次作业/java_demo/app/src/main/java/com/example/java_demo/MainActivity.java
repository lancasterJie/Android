package com.example.java_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    TextView tv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            if(requestCode == 1){
                String result = data.getStringExtra("result");
                tv.setText(result);
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



        class Mylister1 implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button1) {
                    Toast.makeText(MainActivity.this, "已点击", Toast.LENGTH_LONG).show();
                } if (v.getId() == R.id.button2) {
                   Intent it = new Intent (MainActivity.this,SecondActivity.class);
                    startActivity(it);
                    //Toast.makeText(MainActivity.this, "按钮2", Toast.LENGTH_LONG).show();

                } if(v.getId()==R.id.button3){
                    Intent it = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.snnu.edu.cn"));
                    startActivity(it);
                }if(v.getId()==R.id.button4){
                    Intent it = new Intent (MainActivity.this,MainActivity2.class);
                    startActivity(it);
                }if(v.getId()==R.id.button5){
//                    Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_LONG).show();
                    Intent itresult = new Intent (MainActivity.this,FourActivity.class);
                    //Intent itresult = new Intent (MainActivity.this.SecondActivity.class);
                    startActivityForResult(itresult,1);
                }
            }




//        class MyLister2 implements View.OnClickListener{
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "第二个按钮", Toast.LENGTH_LONG).show();
//            }
//        }
//        //匿名内部类
//        b1.OnClickListener(View.OnClickListener){
//
//        }
//        //lamuda
//        b1.setOnClickListener(v ->
//                Toast.makeText(this, "按钮w", Toast.LENGTH_SHORT).show());
//    }


        }

        //插入按钮
        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new Mylister1());

        //插入按钮
        b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new Mylister1());

        b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new Mylister1());

        b4 = findViewById(R.id.button4);
        b4.setOnClickListener(new Mylister1());

        b5 = findViewById(R.id.button5);
        b5.setOnClickListener(new Mylister1());

        tv=findViewById(R.id.tv);

    }
}
