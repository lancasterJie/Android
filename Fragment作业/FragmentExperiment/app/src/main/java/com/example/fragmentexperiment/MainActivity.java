package com.example.fragmentexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements FragmentA.OnFragmentInteractionListener {

    private RadioGroup radioGroup;
    private FragmentA fragmentA;
    private FragmentB fragmentB;
    private FragmentC fragmentC;
    private FragmentD fragmentD;

    private TextView tvFragmentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate");

        initViews();
        setupFragments();
        setupRadioGroup();

        // Activity → Activity 数据传输示例按钮
        Button btnStartDetail = findViewById(R.id.btn_start_detail);
        btnStartDetail.setOnClickListener(v -> startDetailActivity());
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radio_group);
        tvFragmentResponse = findViewById(R.id.tv_fragment_response);
    }

    private void setupFragments() {
        fragmentA = FragmentA.newInstance("张三", 20);
        fragmentB = new FragmentB();
        fragmentC = new FragmentC();
        fragmentD = new FragmentD();

        // 默认显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentA)
                .commit();
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment selectedFragment = null;

            if (checkedId == R.id.radio_btn1) {
                selectedFragment = fragmentA;
            } else if (checkedId == R.id.radio_btn2) {
                selectedFragment = fragmentB;
            } else if (checkedId == R.id.radio_btn3) {
                selectedFragment = fragmentC;
            } else if (checkedId == R.id.radio_btn4) {
                selectedFragment = fragmentD;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
        });
    }

    // Activity ↔ Fragment 数据传输：Fragment向Activity返回数据
    @Override
    public void onDataFromFragment(String data) {
        if (tvFragmentResponse != null) {
            tvFragmentResponse.setText("Fragment返回的数据: " + data);
        }
    }

    // Fragment → Fragment 数据传输中转
    public void sendDataToFragmentD(String message) {
        if (fragmentD != null) {
            fragmentD.updateMessage(message);
            // 切换到FragmentD显示结果
            radioGroup.check(R.id.radio_btn4);
        }
    }

    // Activity → Activity 数据传输
    private void startDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("username", "李四");
        bundle.putInt("age", 25);
        bundle.putBoolean("isStudent", true);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState被调用 - 在onStop之前，onPause之后");

        // 保存EditText内容等需要保存的状态
        if (tvFragmentResponse != null) {
            outState.putString("fragment_response", tvFragmentResponse.getText().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity", "onRestoreInstanceState");

        String savedResponse = savedInstanceState.getString("fragment_response");
        if (savedResponse != null && tvFragmentResponse != null) {
            tvFragmentResponse.setText(savedResponse);
        }
    }
}