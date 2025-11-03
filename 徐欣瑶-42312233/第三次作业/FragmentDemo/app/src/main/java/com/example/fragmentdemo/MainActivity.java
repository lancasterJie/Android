package com.example.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements
        ProfileFragment.OnProfileInteractionListener,
        SettingsFragment.OnSettingsInteractionListener {

    private static final String TAG = "MainActivity";
    private RadioGroup radioGroup;

    // Fragment tags
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_DATA = "data";

    private String currentFragmentTag = TAG_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        initViews();
        setupRadioGroup();

        // 显示默认Fragment
        if (savedInstanceState == null) {
            showFragment(new HomeFragment(), TAG_HOME);
        }
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioHome) {
                    showFragment(new HomeFragment(), TAG_HOME);
                } else if (checkedId == R.id.radioProfile) {
                    // 场景B: Activity → Fragment 传递数据
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("user_name", "张三");
                    bundle.putInt("user_age", 25);
                    bundle.putBoolean("is_student", true);
                    profileFragment.setArguments(bundle);
                    showFragment(profileFragment, TAG_PROFILE);
                } else if (checkedId == R.id.radioSettings) {
                    showFragment(new SettingsFragment(), TAG_SETTINGS);
                } else if (checkedId == R.id.radioData) {
                    showFragment(new DataFragment(), TAG_DATA);
                }
            }
        });
    }

    private void showFragment(Fragment fragment, String tag) {
        currentFragmentTag = tag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment, tag);
        transaction.commit();
    }

    // 场景B: Fragment → Activity 返回处理结果
    @Override
    public void onProfileResult(String result) {
        Log.d(TAG, "收到来自ProfileFragment的结果: " + result);
        // 可以在这里处理Fragment返回的结果
    }

    // 场景C: Fragment → Fragment 通过Activity中转
    @Override
    public void onSendDataToDataFragment(String data) {
        Log.d(TAG, "中转数据到DataFragment: " + data);
        DataFragment dataFragment = (DataFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_DATA);
        if (dataFragment != null) {
            dataFragment.receiveData(data);
        }
        // 如果DataFragment不在前台，可以保存数据等它显示时再传递
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState - 在onStop之前，onPause之后调用");

        // 保存当前Fragment标签
        outState.putString("current_fragment", currentFragmentTag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");

        if (savedInstanceState != null) {
            String savedFragmentTag = savedInstanceState.getString("current_fragment");
            if (savedFragmentTag != null) {
                currentFragmentTag = savedFragmentTag;
                // 根据保存的tag恢复对应的RadioButton选中状态
                switch (savedFragmentTag) {
                    case TAG_HOME:
                        radioGroup.check(R.id.radioHome);
                        break;
                    case TAG_PROFILE:
                        radioGroup.check(R.id.radioProfile);
                        break;
                    case TAG_SETTINGS:
                        radioGroup.check(R.id.radioSettings);
                        break;
                    case TAG_DATA:
                        radioGroup.check(R.id.radioData);
                        break;
                }
            }
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("key",ediTextTest,getTest(),toString());
//        Log.d(TAG,"");
//    }

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