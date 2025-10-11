package com.example.activitynavigator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {
    protected static final String TAG = "Lifecycle";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);
        Log.d(TAG,"DialogActivity-onCrate");
        Button CloseButton = findViewById(R.id.CloseButton);
        CloseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(DialogActivity.this,"关闭对话框",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "DialogActivity- onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "DialogActivity- onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "DialogActivity- onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "DialogActivity- onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "DialogActivity- onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DialogActivity - onDestroy");
    }
}
