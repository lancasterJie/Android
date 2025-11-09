package com.example.activityradiogroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements
        FragmentA.FragmentAListener,
        FragmentB.FragmentBListener {

    private static final String TAG = "lzd";
    private static final String KEY_SAVED_TEXT = "saved_text";

    private FragmentA fragmentA;
    private FragmentB fragmentB;
    private FragmentC fragmentC;
    private FragmentD fragmentD;
    private Fragment currentFragment;

    private String savedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        // 恢复保存的状态
        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
            Log.d(TAG, "恢复保存的文本: " + savedText);
        }

        initFragments();
        setupRadioGroup();
        setupButton();

        // 默认显示第一个fragment
        showFragment(fragmentA);
    }

    private void initFragments() {
        fragmentA = FragmentA.newInstance("初始用户名");
        fragmentB = new FragmentB();
        fragmentC = new FragmentC();
        fragmentD = new FragmentD();

        fragmentB.setFragmentBListener(this);
    }

    private void setupRadioGroup() {
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_fragment_a) {
                showFragment(fragmentA);
            } else if (checkedId == R.id.radio_fragment_b) {
                showFragment(fragmentB);
            } else if (checkedId == R.id.radio_fragment_c) {
                showFragment(fragmentC);
            } else if (checkedId == R.id.radio_fragment_d) {
                showFragment(fragmentD);
                // 显示之前保存的文本
                if (!savedText.isEmpty()) {
                    fragmentD.displaySavedText(savedText);
                }
            }
        });

        // 默认选中第一个
        radioGroup.check(R.id.radio_fragment_a);
    }

    private void setupButton() {
        findViewById(R.id.btn_go_to_detail).setOnClickListener(v -> {
            // Activity → Activity 数据传递
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("username", "lzd");
            intent.putExtra("age", 18);
            intent.putExtra("isStudent", true);
            startActivity(intent);
        });
    }

    private void showFragment(Fragment fragment) {
        if (fragment == currentFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment);
        } else {
            transaction.show(fragment);
        }

        transaction.commit();
        currentFragment = fragment;
    }

    // FragmentA.FragmentAListener 实现
    @Override
    public void onDataSent(String data) {
        Toast.makeText(this, "从FragmentA接收到的数据: " + data, Toast.LENGTH_SHORT).show();
    }

    // FragmentB.FragmentBListener 实现
    @Override
    public void sendMessageToFragmentC(String message) {
        fragmentC.updateMessage(message);
        Toast.makeText(this, "消息已发送到FragmentC", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState - 保存状态");

        // 保存EditText中的内容
        if (fragmentD != null) {
            String inputText = fragmentD.getInputText();
            if (!inputText.isEmpty()) {
                outState.putString(KEY_SAVED_TEXT, inputText);
                Log.d(TAG, "保存的文本: " + inputText);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState - 恢复状态");

        savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
        if (!savedText.isEmpty() && fragmentD != null) {
            fragmentD.displaySavedText(savedText);
        }
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