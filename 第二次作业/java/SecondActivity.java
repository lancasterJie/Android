package com.example.myapplication;

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

    Button back;

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

        class MyListener implements View.OnClickListener
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.back)
                {
                    finish();
                }
            }
        }

        back=findViewById(R.id.back);
        back.setOnClickListener(new MyListener());

        Log.d("Lifecycle", "SecondActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle","SecondActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle","SecondActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle","SecondActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle","SecondActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle","SecondActivity - onDestroy");
    }
}
