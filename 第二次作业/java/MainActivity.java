package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b1;

    Button b2;

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

        class MyListener implements View.OnClickListener
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.SecondActivity)
                {
                    Intent it=new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(it);
                }
                else if(view.getId()==R.id.DialogActivity)
                {
                    Intent dialog=new Intent(MainActivity.this,DialogActivity.class);
                    startActivity(dialog);
                }
            }
        }

        b1=findViewById(R.id.SecondActivity);
        b1.setOnClickListener(new MyListener());
        b2=findViewById(R.id.DialogActivity);
        b2.setOnClickListener(new MyListener());

        Log.d("Lifecycle", "MainActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle","MainActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle","MainActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle","MainActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle","MainActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle","MainActivity - onDestroy");
    }
}
