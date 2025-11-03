package com.example.test_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnDataTransferListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_SELECTED_ITEM = "selected_item";
    private static final String KEY_EDIT_TEXT = "edit_text_content";

    private RadioGroup radioGroup;
    private FrameLayout fragmentContainer;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;

    // 用于保存状态的数据
    private String savedEditTextContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button testButton = findViewById(R.id.button_test_activity);
        testButton.setOnClickListener(v -> {
            sendDataToDetailActivity();
        });
        Log.d(TAG, "onCreate");

        // 恢复保存的状态
        if (savedInstanceState != null) {
            int selectedItem = savedInstanceState.getInt(KEY_SELECTED_ITEM, R.id.radio_home);
            savedEditTextContent = savedInstanceState.getString(KEY_EDIT_TEXT, "");
            Log.d(TAG, "恢复状态 - 选中项: " + selectedItem + ", 文本内容: " + savedEditTextContent);
        }

        initViews();
        initFragments();
        setupRadioGroup();
        if(homeFragment!=null){
            homeFragment.setOnDataTransferListener(this);
        }
        int defaultSelection = savedInstanceState != null ?
                savedInstanceState.getInt(KEY_SELECTED_ITEM, R.id.radio_home) : R.id.radio_home;
        radioGroup.check(defaultSelection);

        // 启动时传递数据到 DetailActivity
        sendDataToDetailActivity();
    }
    private void initViews() {
        radioGroup = findViewById(R.id.radio_group);
        fragmentContainer = findViewById(R.id.fragment_container);
    }

    private void initFragments() {
        homeFragment = HomeFragment.newInstance("来自MainActivity的初始数据");
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        settingsFragment = SettingsFragment.newInstance(savedEditTextContent);
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_home) {
                    switchFragment(homeFragment);
                } else if (checkedId == R.id.radio_search) {
                    switchFragment(searchFragment);
                } else if (checkedId == R.id.radio_profile) {
                    switchFragment(profileFragment);
                } else if (checkedId == R.id.radio_settings) {
                    switchFragment(settingsFragment);
                }
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    // 场景A: Activity → Activity 数据传输
    private void sendDataToDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("username", "张三");
        intent.putExtra("age", 20);
        intent.putExtra("isStudent", true);
        startActivity(intent);
    }

    // 场景B: Activity → Fragment 数据传输
    @Override
    public void onDataReceivedFromFragment(String data) {
        // 处理从 Fragment 返回的数据
        Toast.makeText(this, "从Fragment接收到的数据: " + data, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "从Fragment接收数据: " + data);

        // 场景C: Fragment → Fragment 数据传输（通过Activity中转）
        if (searchFragment != null) {
            searchFragment.updateSearchData("中转数据: " + data);
            Log.d(TAG, "已向SearchFragment发送中转数据: " + data);
        }
    }

    // 状态保存
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState - 在onStop之前，onPause之后调用");

        outState.putInt(KEY_SELECTED_ITEM, radioGroup.getCheckedRadioButtonId());

        // 保存EditText内容
        if (settingsFragment != null) {
            String editTextContent = settingsFragment.getEditTextContent();
            outState.putString(KEY_EDIT_TEXT, editTextContent);
            Log.d(TAG, "保存EditText内容: " + editTextContent);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
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