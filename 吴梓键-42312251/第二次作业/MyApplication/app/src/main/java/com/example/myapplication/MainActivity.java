package com.example.myapplication;

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

import ads_mobile_sdk.b1;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6;
    TextView tv,tvResult;
    public static final String TAG="MainActivityLog";

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

        Log.d(TAG,"onCreate");

        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new MyListener1());
        b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new MyListener1());
        b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new MyListener1());
        b4 = findViewById(R.id.button4);
        b4.setOnClickListener(new MyListener1());
        b5 = findViewById(R.id.button5);
        b5.setOnClickListener(new MyListener1());
        tv = findViewById(R.id.tv);
        b6 = findViewById(R.id.button6);
        b6.setOnClickListener(new MyListener1());
    }


    class MyListener1 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.button1)
            {
                Toast.makeText(MainActivity.this, "Android", Toast.LENGTH_LONG).show();
            }
            else if(view.getId()==R.id.button2)
            {
                Intent it = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
                //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_LONG).show();
            }
            else if(view.getId()==R.id.button3)
            {
                Intent itbro = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com"));
                startActivity(itbro);
            }
            else if(view.getId()==R.id.button4)
            {
                Intent itimp = new Intent("com.package.helloworld.thirdactivity");
                startActivity(itimp);
            }
            else if(view.getId()==R.id.button5)
            {
                Intent itresult = new Intent(MainActivity.this,ForthActivity.class);
                startActivityForResult(itresult,1);
            }
            else if(view.getId()==R.id.button6)
            {
                Intent indialog=new Intent(MainActivity.this,DialogActivity.class);
                startActivity(indialog);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==3)
        {
            if(requestCode==1)
            {
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }
}

