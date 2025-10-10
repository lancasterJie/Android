package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    private EditText etInput;
    private Button btnReturnResult, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etInput = findViewById(R.id.et_input);
        btnReturnResult = findViewById(R.id.btn_return_result);
        btnCancel = findViewById(R.id.btn_cancel);

        btnReturnResult.setOnLongClickListener(v -> {
            Toast.makeText(ThirdActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            String inputText = etInput.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result_data", inputText);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            return true;
        });

        btnReturnResult.setOnClickListener(v -> {
            String inputText = etInput.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result_data", inputText);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}
