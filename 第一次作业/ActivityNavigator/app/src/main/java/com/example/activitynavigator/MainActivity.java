package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button b1, b2;


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

        tv = findViewById(R.id.tv);

        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new MyListener1());

        b2 = findViewById(R.id.button4);
        b2.setOnClickListener(new MyListener1());
        b2.setOnLongClickListener(new MyListener2());

    }

    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view == b1){
                Intent it = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(it);
            }
            else if(view == b2){
                Intent it = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(it, 101);
            }

        }
    }

    class MyListener2 implements View.OnLongClickListener{
        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == -1){
            String resultString = data.getStringExtra("result_data");
            tv.setText(resultString);
        }
        else if (requestCode == 101 && resultCode == 0){
            Toast.makeText(MainActivity.this, "取消了返回", Toast.LENGTH_LONG).show();
        }

    }
}