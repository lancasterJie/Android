package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

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
        Button b2=findViewById(R.id.back1);
        Button b3=findViewById(R.id.button2);
        MyClickListener nl2 = new MyClickListener();
        b2.setOnClickListener(nl2);
        b3.setOnClickListener(nl2);
    }


    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.back1){
                finish();
            }
            else if(v.getId()==R.id.button2){
                Intent it = new Intent("com.example.action.VIEW_THIRD_ACTIVITY");
                startActivity(it);
            }
        }
    }
}