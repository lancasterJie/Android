package com.example.experiment01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
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
        textView = findViewById(R.id.textView);
        btnGetResult = findViewById(R.id.btnGetResult);
        btnToSecondActivity = findViewById(R.id.btnToSecondActivity);
        btnToSecondActivity.setOnClickListener((v) ->
                startActivity(new Intent(MainActivity.this, SecondActivity.class)));
        btnGetResult.setOnClickListener((v)-> {
            Intent it = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(it, 102);
        });
        btnGetResult.setOnLongClickListener((v)->{
            Intent it = new Intent(MainActivity.this, ThirdActivity.class);
            Toast.makeText(this, "Long Click For Result", Toast.LENGTH_SHORT).show();
            startActivityForResult(it, 102);
            return true;
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == 101) {
            textView.setText(data.getStringExtra("result"));
        }
        else {
            Toast.makeText(this, "No Result", Toast.LENGTH_SHORT).show();
        }
    }

    private Button btnToSecondActivity, btnGetResult;
    private TextView textView;
}