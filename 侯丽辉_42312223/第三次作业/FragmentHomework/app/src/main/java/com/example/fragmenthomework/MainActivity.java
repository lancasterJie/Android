package com.example.fragmenthomework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentB.FragmentBListener {
    private static final String TAG = "MainActivity";
    private RadioGroup radioGroup;
    private Button btnToDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.radioGroup);
        btnToDetail = findViewById(R.id.btn_to_detail);

        // 默认显示 Fragment A
        replaceFragment(new FragmentA());

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment fragment;
            if (checkedId == R.id.rb_a) fragment = new FragmentA();
            else if (checkedId == R.id.rb_b) fragment = FragmentB.newInstance("来自Activity的初始数据");
            else if (checkedId == R.id.rb_c) fragment = new FragmentC();
            else fragment = new FragmentD();
            replaceFragment(fragment);
        });

        btnToDetail.setOnClickListener(v -> {
            // 场景 A 的演示：Activity -> Activity（传用户数据）
            Intent it = new Intent(MainActivity.this, DetailActivity.class);
            it.putExtra("username", "侯丽辉");
            it.putExtra("age", 22);
            it.putExtra("isStudent", true);
            startActivity(it);
        });

        // 监听来自 Fragment 的返回（使用 FragmentResult API）
        getSupportFragmentManager().setFragmentResultListener("fromFragmentB", this, (requestKey, bundle) -> {
            String result = bundle.getString("result");
            // 这里处理 Fragment B 返回的数据
            Log.i(TAG, "收到 FragmentB 返回: " + result);
        });

        // FragmentC -> FragmentD 的中转: 监听来自 C 的请求
        getSupportFragmentManager().setFragmentResultListener("C_to_activity", this, (requestKey, bundle) -> {
            String msg = bundle.getString("msg");
            // 将消息转发给 FragmentD（通过 fragmentResult）
            Bundle forward = new Bundle();
            forward.putString("msg_to_d", msg);
            getSupportFragmentManager().setFragmentResult("activity_to_D", forward);
        });

        Log.i(TAG, "onCreate called");
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onFragmentBAction(String processed) {
        // 接口回调方式（FragmentB -> Activity）
        Log.i(TAG, "接口回调收到：" + processed);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "MainActivity.onSaveInstanceState called");
        // 如果需要保存额外状态，可以放在这里
    }
}
