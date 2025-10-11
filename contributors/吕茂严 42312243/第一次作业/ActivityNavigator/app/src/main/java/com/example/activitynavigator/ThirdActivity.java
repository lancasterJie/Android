package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {
    EditText et;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et = findViewById(R.id.et);
        b1 = findViewById(R.id.button5);
        b2 = findViewById(R.id.button6);

        b1.setOnClickListener(new MyListener());
        b2.setOnClickListener(new MyListener());


    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == b1) {
                Intent it = new Intent();
                it.putExtra("result_data", et.getText().toString());
                setResult(ThirdActivity.RESULT_OK, it);
                finish();
            } else if (v == b2) {
                setResult(ThirdActivity.RESULT_CANCELED);
                finish();
            }
        }
    }

}