package com.example.test;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SecondActivity extends AppCompatActivity {
    Button switch1,switch2;
    public static final String TAG = "liSecond" ;
    Button back, hide;
    @SuppressLint("MissingInflatedId")
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
        class MyListener1 implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button2) {
                    finish();
                } else if (view.getId() == R.id.button3) {
                    Intent itimp = new Intent("com.example.action.VIEW_THIRD_ACTIVITY");
                    startActivity(itimp);
                }
            }
        }
        MyListener1 ml = new MyListener1();
        back = findViewById(R.id.button2);
        back.setOnClickListener(ml);
        hide = findViewById(R.id.button3);
        hide.setOnClickListener(ml);
        Log.d(TAG, "onCreate");
        switch1 = findViewById(R.id.switchFrag1);
        switch1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFragment(new BlankFragment1());
            }
        });
        switch2 = findViewById(R.id.switchFrag2);
        switch2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFragment(new BlankFragment2());
            }
        });
    }
    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.switchContainer, fragment);

        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
