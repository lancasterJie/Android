package com.example.expriment04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

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
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isSave = sharedPreferences.getBoolean("is_save", false);
        if (isSave) {
            title.setText(sharedPreferences.getString("account", title.getText().toString()));
        }
    }

    void initComponents() {
        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        toSecondActivity = findViewById(R.id.to_second_activity);
        toThirdActivity = findViewById(R.id.to_third_activity);
        fileContent = findViewById(R.id.file_content);
        title = findViewById(R.id.title);
        save.setOnClickListener((v) -> {
            try {
                FileOutputStream fileOutputStream = openFileOutput("note.txt", MODE_PRIVATE);
                fileOutputStream.write(fileContent.getText().toString().getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        load.setOnClickListener((v) -> {
            try {
                FileInputStream fileInputStream = openFileInput("note.txt");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    byte[] bytes = fileInputStream.readAllBytes();
                    fileContent.setText(new String(bytes));
                }
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        toSecondActivity.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
        toThirdActivity.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        });
    }
    private Button save, load, toSecondActivity, toThirdActivity;
    private EditText fileContent;
    private TextView title;
}