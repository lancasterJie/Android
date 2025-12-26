package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BundleActivity extends AppCompatActivity implements ExampleFragment.OnFragmentInteractionListener {

    private String TAG = "BundleActivity";

    private TextView tvFragmentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);

        Log.d(TAG, "onCreate");
        initViews();
        setupClickListeners();

    }

    private void initViews() {
        tvFragmentResult = findViewById(R.id.tvFragmentResult);
    }

    private void setupClickListeners() {
        // 场景A: Activity → Activity
        Button btnStartDetailActivity = findViewById(R.id.btnStartDetailActivity);
        btnStartDetailActivity.setOnClickListener(v -> startDetailActivity());

        // 场景B: Activity ↔ Fragment
        Button btnAddFragment = findViewById(R.id.btnAddFragment);
        btnAddFragment.setOnClickListener(v -> addExampleFragment());

        // 场景C: Fragment → Fragment
        Button btnStartFragmentCommunication = findViewById(R.id.btnStartFragmentCommunication);
        btnStartFragmentCommunication.setOnClickListener(v -> startFragmentCommunication());
    }

    // 场景A: Activity → Activity
    private void startDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);

        // 创建Bundle并添加数据
        Bundle bundle = new Bundle();
        bundle.putString("user_name", "张三");
        bundle.putInt("user_age", 25);
        bundle.putBoolean("is_student", true);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 场景B: Activity ↔ Fragment - 添加Fragment
    private void addExampleFragment() {
        ExampleFragment fragment = new ExampleFragment();

        // 创建Bundle传递初始数据给Fragment
        Bundle args = new Bundle();
        args.putString("initial_data", "这是从Activity传递到Fragment的初始数据");
        args.putInt("initial_number", 200);
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    // 场景B: Activity ↔ Fragment - 接收Fragment返回的数据
    @Override
    public void onDataReceived(String data) {
        tvFragmentResult.setText("Fragment返回结果: " + data);
        Log.d(TAG, "从Fragment接收到的数据: " + data);
    }

    // 场景C: Fragment → Fragment
    private void startFragmentCommunication() {
        // 添加Fragment A
        AFragment fragmentA = new AFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragmentA)
                .commit();
    }

    // 场景C: Fragment → Fragment - 中转方法
    public void passDataToFragmentB(String data) {
        // 创建Fragment B并传递数据
        BFragment fragmentB = new BFragment();

        Bundle args = new Bundle();
        args.putString("data_from_a", data);
        fragmentB.setArguments(args);

        // 替换当前Fragment为Fragment B
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragmentB)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
