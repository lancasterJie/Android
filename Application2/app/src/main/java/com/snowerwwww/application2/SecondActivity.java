package com.snowerwwww.application2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    //activity在用户可见时调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle","SecondActivity - onStart");
    }

    //和用户交互时调用
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle","SecondActivity - onResume");
    }

    //其他activity获得焦点时调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle","SecondActivity - onPause");
    }

    //activity不再可见时调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","SecondActivity - onStop");
    }

    //停止的activity重新启动时调用
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle","SecondActivity - onRestart");
    }

    //被销毁前调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","SecondActivity - onDestroy");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LifeCycle","SecondActivity - onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}