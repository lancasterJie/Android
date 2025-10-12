package com.snowerwwww.application2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button b1;
    Button b2;
    //activity在用户可见时调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle","MainActivity - onStart");
    }

    //和用户交互时调用
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle","MainActivity - onResume");
    }

    //其他activity获得焦点时调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle","MainActivity - onPause");
    }

    //activity不再可见时调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","MainActivity - onStop");
    }

    //停止的activity重新启动时调用
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle","MainActivity - onRestart");
    }

    //被销毁前调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","MainActivity - onDestroy");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //首次创建时调用
        Log.d("LifeCycle","MainActivity - onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(this);

        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            Log.d("LifeCycle","MainActivity - onCreate");
            Toast.makeText(this, "按钮已触发", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(it);
        } if (v.getId() == R.id.button2) {
            Log.d("LifeCycle","MainActivity - onCreate");
            Toast.makeText(this, "按钮已触发", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(MainActivity.this,DialogActivity.class);
            startActivity(it);
        }



    }




}