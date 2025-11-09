package com.example.fragmentdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements
        ProfileFragment.OnDataReturnListener,
        DataTransferFragment.OnFragmentDataListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private static final String KEY_SAVED_TEXT = "saved_text";

    private RadioGroup radioGroup;
    private TextView tvFragmentResult;

    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;
    private DataTransferFragment dataTransferFragment;

    private int currentFragmentId = R.id.radioHome;
    private String savedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate被调用");

        initViews();
        initFragments();

        // 恢复状态
        if (savedInstanceState != null) {
            currentFragmentId = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT, R.id.radioHome);
            savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");

            // 恢复RadioButton状态
            radioGroup.check(currentFragmentId);

            // 恢复保存的文本到SettingsFragment
            if (settingsFragment != null && !savedText.isEmpty()) {
                settingsFragment.setSavedText(savedText);
            }

            System.out.println("MainActivity恢复状态: currentFragmentId=" + currentFragmentId + ", savedText=" + savedText);
        } else {
            // 默认显示首页
            switchFragment(R.id.radioHome);
        }
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
        tvFragmentResult = findViewById(R.id.tvFragmentResult);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });
    }

    private void initFragments() {
        // 场景B: Activity向Fragment传递初始数据
        homeFragment = HomeFragment.newInstance("默认用户");
        profileFragment = new ProfileFragment();
        settingsFragment = SettingsFragment.newInstance("");
        dataTransferFragment = DataTransferFragment.newInstance(null);

        // 设置监听器
        profileFragment.setOnDataReturnListener(this);
        dataTransferFragment.setOnFragmentDataListener(this);
    }

    private void switchFragment(int fragmentId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;

        if (fragmentId == R.id.radioHome) {
            fragment = homeFragment;
        } else if (fragmentId == R.id.radioProfile) {
            fragment = profileFragment;
        } else if (fragmentId == R.id.radioSettings) {
            fragment = settingsFragment;
        } else if (fragmentId == R.id.radioData) {
            fragment = dataTransferFragment;
        }

        if (fragment != null) {
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
            currentFragmentId = fragmentId;
            System.out.println("切换到Fragment: " + fragmentId);
        }
    }

    // 场景B: Fragment → Activity 数据返回
    @Override
    public void onDataReturn(String userName, int age, boolean isStudent) {
        String result = String.format("Fragment返回的数据:\n用户名: %s\n年龄: %d\n是否学生: %s",
                userName, age, isStudent ? "是" : "否");
        tvFragmentResult.setText(result);
        System.out.println("MainActivity接收到Fragment返回数据: " + result);
    }

    // 场景C: Fragment → Fragment 数据传输 (通过Activity中转)
    @Override
    public void onSendDataToOtherFragment(String data) {
        // 将数据传递给HomeFragment
        if (homeFragment != null) {
            homeFragment.updateReceivedData(data);
        }

        // 同时更新UI显示
        tvFragmentResult.setText("Fragment间传输的数据: " + data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState被调用 - 在onStop之前，onPause之后");

        // 保存当前选中的Fragment
        outState.putInt(KEY_CURRENT_FRAGMENT, currentFragmentId);

        // 保存需要持久化的数据
        outState.putString(KEY_SAVED_TEXT, savedText);

        System.out.println("=== onSaveInstanceState生命周期阶段 ===");
        System.out.println("onSaveInstanceState在onStop之前，onPause之后被调用");
        System.out.println("保存了当前Fragment ID: " + currentFragmentId);
        System.out.println("保存的文本: " + savedText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState被调用");
        System.out.println("onRestoreInstanceState被调用，恢复保存的状态");
    }

    // 添加其他生命周期方法用于观察
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart被调用");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume被调用");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause被调用");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop被调用");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy被调用");
    }
}