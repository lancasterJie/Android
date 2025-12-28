package com.example.bundledemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements Fragment2.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_ACTIVITY_DATA = "activity_saved_data";
    private static final String KEY_RADIO_CHECKED_ID = "radio_checked_id";

    private RadioGroup radioGroup;
    private TextView tvFragmentResult;
    private String fragmentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate被调用");

        // 恢复Activity保存的状态
        if (savedInstanceState != null) {
            fragmentData = savedInstanceState.getString(KEY_ACTIVITY_DATA, "");
            Log.d(TAG, "从savedInstanceState恢复数据: " + fragmentData);
        }

        radioGroup = findViewById(R.id.radio_group);
        tvFragmentResult = findViewById(R.id.tv_fragment_result);

        // 显示恢复的数据
        if (!fragmentData.isEmpty()) {
            tvFragmentResult.setText("恢复的Fragment数据: " + fragmentData);
        }

        // 设置RadioGroup监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;

                if (checkedId == R.id.radio_fragment1) {
                    fragment = new Fragment1();
                } else if (checkedId == R.id.radio_fragment2) {
                    fragment = new Fragment2();
                    // 向Fragment传递初始数据（场景B: Activity → Fragment）
                    if (fragment != null) {
                        Bundle args = new Bundle();
                        args.putString("initial_data", "这是来自Activity的初始数据");
                        fragment.setArguments(args);
                    }
                } else if (checkedId == R.id.radio_fragment3) {
                    fragment = new Fragment3();
                } else if (checkedId == R.id.radio_fragment4) {
                    fragment = new Fragment4();
                }

                if (fragment != null) {
                    replaceFragment(fragment);
                }
            }
        });

        // 默认选中第一个Fragment
        if (savedInstanceState == null) {
            ((RadioButton) findViewById(R.id.radio_fragment1)).setChecked(true);
        } else {
            // 恢复RadioButton的选择状态
            int savedCheckedId = savedInstanceState.getInt(KEY_RADIO_CHECKED_ID, -1);
            if (savedCheckedId != -1) {
                radioGroup.check(savedCheckedId);
            }
        }

        // 场景A: Activity → Activity 数据传输按钮
        findViewById(R.id.btn_goto_detail).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            // 创建Bundle并添加数据
            Bundle bundle = new Bundle();
            bundle.putString("user_name", "张三");
            bundle.putInt("user_age", 25);
            bundle.putBoolean("is_student", true);

            // 将Bundle放入Intent
            intent.putExtras(bundle);

            startActivity(intent);
        });

        // Fragment间通信按钮（场景C: Fragment → Fragment）
        findViewById(R.id.btn_send_to_fragment4).setOnClickListener(v -> {
            // 通过Activity中转，将数据发送到Fragment4
            if (!fragmentData.isEmpty()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment4 = fragmentManager.findFragmentByTag("FRAGMENT4");
                if (fragment4 instanceof Fragment4) {
                    ((Fragment4) fragment4).updateReceivedData(fragmentData);
                    Toast.makeText(this, "数据已发送到Fragment4", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请先切换到Fragment4", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请先通过Fragment2发送数据", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        String tag = null;
        if (fragment instanceof Fragment1) {
            tag = "FRAGMENT1";
        } else if (fragment instanceof Fragment2) {
            tag = "FRAGMENT2";
        } else if (fragment instanceof Fragment3) {
            tag = "FRAGMENT3";
        } else if (fragment instanceof Fragment4) {
            tag = "FRAGMENT4";
        }

        transaction.replace(R.id.fragment_container, fragment, tag);
        // 使用commitNow来避免异步问题
        transaction.commitNowAllowingStateLoss();
    }

    // 实现Fragment2的接口方法（场景B: Fragment → Activity）
    @Override
    public void onDataFromFragment(String data) {
        fragmentData = data;
        tvFragmentResult.setText("Fragment返回的数据: " + data);
        Toast.makeText(this, "收到Fragment的数据: " + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState被调用 - 生命周期阶段: onPause之后，onStop之前");
        Log.d(TAG, "当前保存的数据: " + fragmentData);

        // 保存Activity的数据
        outState.putString(KEY_ACTIVITY_DATA, fragmentData);
        // 保存当前选中的RadioButton ID
        outState.putInt(KEY_RADIO_CHECKED_ID, radioGroup.getCheckedRadioButtonId());

        System.out.println("MainActivity - onSaveInstanceState被调用，保存数据到Bundle");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState被调用");
        fragmentData = savedInstanceState.getString(KEY_ACTIVITY_DATA, "");
        if (tvFragmentResult != null && !fragmentData.isEmpty()) {
            tvFragmentResult.setText("恢复的Fragment数据: " + fragmentData);
        }
    }
}