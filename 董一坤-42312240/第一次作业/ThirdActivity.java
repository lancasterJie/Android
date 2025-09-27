package com.dyk.activitynavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        et_input_text = findViewById(R.id.et_input_text);

        findViewById(R.id.btn_return_result).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_return_result){
            String input = et_input_text.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("result",input);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else if(v.getId() == R.id.btn_cancel){
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }
}