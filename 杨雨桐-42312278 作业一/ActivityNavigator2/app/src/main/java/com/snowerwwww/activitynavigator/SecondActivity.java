package com.snowerwwww.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    Button b2;
    Button b3;
    public final String action = "com.snowerwwww.activitynavigator.OPENTHIRTY";
    public final String category = "android.intent.category.DEFAULT";
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


        class SecondLister implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "已点击", Toast.LENGTH_SHORT).show();
                if(v.getId() == R.id.button2){
                    Intent it = new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(it);
                }
                if(v.getId() == R.id.button3){
                    Toast.makeText(SecondActivity.this, "已点击", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent();
                    it.setAction(action);
                    it.addCategory(category);
                    startActivity(it);

                }
            }
        }

        b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new SecondLister());

        b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new SecondLister());
    }
}