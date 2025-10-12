package com.snowerwwww.activitynavigator;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.google.android.material.animation.AnimatableView;

import org.w3c.dom.Text;

public class ThirtyActivity extends AppCompatActivity {

    //返回数据按钮
    Button b3;
    //取消按钮
    Button b4;
    //输入文本框
    EditText text;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thirty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text  = findViewById(R.id.insert);
        b3 = findViewById(R.id.buttonReturn);
        b4 = findViewById(R.id.consule);

        //带结果返回，返回码为1
        b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent();
                    it.putExtra("result" , text.getText().toString());
                    setResult(1,it);
                    finish();
                }
            });

        //取消返回，返回码为2
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                setResult(2,it);
                finish();
            }
        });
    }

}




