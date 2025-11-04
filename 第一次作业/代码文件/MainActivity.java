package com.example.myapplication1;

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
    Button b1,b2,b3,b4,b5;
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
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        tv = findViewById(R.id.tv);
        b1.setOnClickListener(new MyListener1());
        b2.setOnClickListener(new MyListener1());
        b3.setOnClickListener(new MyListener1());
        b4.setOnClickListener(new MyListener1());
        b5.setOnClickListener(new MyListener1());


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3)
            if(requestCode == 1){
                String resultString = data.getStringExtra("result");
                tv.setText(resultString);
            }

    }


    class MyListener1 implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(view.getId() == R.id.button1){
                Toast.makeText(MainActivity.this,"peace and love",Toast.LENGTH_LONG).show();
            }
            else if(view.getId() == R.id.button2){
                Intent it = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
                //Toast.makeText(MainActivity.this,"Android",Toast.LENGTH_LONG).show();

            }
            else if(view.getId() == R.id.button3){

                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.snnu.edu.cn"));
                startActivity(it);
            }
            else if(view.getId() == R.id.button4){
                Intent it = new Intent("com.package.myapplication1.ThirdActivity");
                startActivity(it);

            }
            else if(view.getId() == R.id.button5){
                Intent it = new Intent(MainActivity.this,ForceActivity.class);
                startActivityForResult(it,1);
            }

        }
    }
}