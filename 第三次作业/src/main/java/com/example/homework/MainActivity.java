package com.example.homework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements FragmentHome.OnHomeResultListener,
        FragmentMessage.OnMessageTransferListener {

    private static final String TAG = "MainActivity";
    private Fragment fragmentHome, fragmentMessage, fragmentSetting, fragmentProfile;
    private Fragment currentFragment;
    private RadioGroup radioGroup;
    private String transferData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 生命周期触发");
        // 全局异常捕获
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            Toast.makeText(this, "跳转异常：" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        });
        setContentView(R.layout.activity_main);

        initFragments();
        initRadioGroup();
        initJumpButton();
    }

    private void initFragments() {
        fragmentHome = FragmentHome.newInstance("欢迎来到首页（来自 Activity）");
        fragmentMessage = new FragmentMessage();
        fragmentSetting = FragmentSetting.newInstance("");
        fragmentProfile = new FragmentProfile();

        currentFragment = fragmentHome;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragmentHome)
                .commit();
    }

    private void initRadioGroup() {
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment target = null;
            if (checkedId == R.id.rb_home) {
                target = fragmentHome;
            } else if (checkedId == R.id.rb_message) {
                target = fragmentMessage;
            } else if (checkedId == R.id.rb_setting) {
                target = fragmentSetting;
            } else if (checkedId == R.id.rb_profile) {
                target = fragmentProfile;
            }

            if (target != null && target != currentFragment) {
                switchFragment(currentFragment, target);
                currentFragment = target;
            }
        });
    }

    private void switchFragment(Fragment from, Fragment to) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!to.isAdded()) {
            ft.hide(from).add(R.id.fragment_container, to).commit();
        } else {
            ft.hide(from).show(to).commit();
        }
    }

    private void initJumpButton() {
        findViewById(R.id.btn_jump_detail).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("user_name", "张三");
            bundle.putInt("user_age", 20);
            bundle.putBoolean("is_student", true);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    public void onHomeResult(String result) {
        Toast.makeText(this, "收到首页返回：" + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransferData(String data) {
        transferData = data;
        Toast.makeText(this, "MainActivity 收到数据：" + data, Toast.LENGTH_SHORT).show();

        FragmentSetting newSettingFragment = FragmentSetting.newInstance(data);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newSettingFragment)
                .commitNow();

        currentFragment = newSettingFragment;
        fragmentSetting = newSettingFragment;
        radioGroup.check(R.id.rb_setting);
    }

    // 打印 MainActivity 的 onSaveInstanceState 生命周期
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: 生命周期触发（屏幕旋转前，保存Activity状态）");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 生命周期触发");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 生命周期触发");
    }
}