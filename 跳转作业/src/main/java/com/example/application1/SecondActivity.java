package com.example.application1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.application1.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 返回到主页的按钮点击事件
        binding.btnBackToMain.setOnClickListener(v -> {
            finish();
        });

        // 隐式跳转的按钮点击事件
        binding.btnImplicitJump.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.application1.VIEW_THIRD_ACTIVITY");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        });
    }
}