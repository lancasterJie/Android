package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    Button b2;
    Button b3;
    public static final String TAG="lifecycle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b2=findViewById(R.id.button2);
        b2.setOnClickListener(new MyListener());
        b3=findViewById(R.id.button3);
        b3.setOnClickListener(new MyListener());
        Log.d(TAG,"SecondActivity-onCreate");
    }
    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(view.getId()==R.id.button2){
                Intent it=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(it);
            }
            else if (view.getId()==R.id.button3) {
                Intent it=new Intent("com.open.ThirdActivity");
                startActivity(it);
            }

        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"SecondActivity-onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"SecondActivity-onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"SecondActivity-onPause");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"SecondActivity-onStop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"SecondActivity-onDestroy");
    }
}