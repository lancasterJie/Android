package com.example.myapplication1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    Button b1,b2,b3,b4,b5,b6;
    TextView tv;
    public static String TAG = "Lifecycle";

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
        b6 = findViewById(R.id.button6);
        tv = findViewById(R.id.tv);

        b1.setOnClickListener(new MyListener1());
        b2.setOnClickListener(new MyListener1());
        b3.setOnClickListener(new MyListener1());
        b4.setOnClickListener(new MyListener1());
        b5.setOnClickListener(new MyListener1());
        b6.setOnClickListener(new MyListener1());

        Log.d(TAG, "MainActivity - onCreate");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "MainActivity - onStart");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "MainActivity - onStop");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "MainActivity - onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity - onRestart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "MainActivity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity - onDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3){
            if(requestCode == 1){
                assert data != null;
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);
            }
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
                Intent itresult = new Intent(MainActivity.this, ForthActivity.class);
                startActivityForResult(itresult,1);
            }
            else if(view.getId() == R.id.button6){
                Intent it = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(it);
            }
        }
    }


}