package com.example.experiment03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        save.setOnClickListener((v) -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("user_name", userName.getText().toString());
            String ageStr = age.getText().toString();
            if (ageStr.chars().allMatch(Character::isDigit)) {
                bundle.putInt("age", Integer.parseInt(ageStr));
            }
            else {
                bundle.putInt("age", 0);
            }
            bundle.putBoolean("is_student", flag);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            userName.setText(bundle.getString("user_name"));
            age.setText(Integer.toString(bundle.getInt("age")));
            if (bundle.getBoolean("is_student")) {
                isStudent.check(R.id.yes);
            }
            else {
                isStudent.check(R.id.no);
            }
        }
    }
    private void initViews() {
        userName = findViewById(R.id.userName);
        age = findViewById(R.id.age);
        isStudent = findViewById(R.id.isStudent);
        save = findViewById(R.id.save);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user_name", userName.getText().toString());
        String ageStr = age.getText().toString();
        if (ageStr.chars().allMatch(Character::isDigit)) {
            outState.putInt("age", Integer.parseInt(ageStr));
        }
        else {
            outState.putInt("age", 0);
        }
        outState.putBoolean("is_student", flag);
    }

    private EditText userName, age;
    private RadioGroup isStudent;
    private Button save;
    private  boolean flag;
}