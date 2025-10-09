package com.example.application1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etInput = findViewById(R.id.et_input);
        Button btnReturnResult = findViewById(R.id.btn_return_result);

        btnReturnResult.setOnClickListener(v -> {
            returnResult();
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_CANCELED);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void returnResult() {
        String inputText = etInput.getText().toString().trim();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result_data", inputText);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}