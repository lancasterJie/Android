package com.example.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fragmentdemo.fragments.DataTransferFragment;
import com.example.fragmentdemo.fragments.HomeFragment;
import com.example.fragmentdemo.fragments.ProfileFragment;
import com.example.fragmentdemo.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText etInput;
    private TextView tvSavedData;

    // 用于保存状态的键
    private static final String KEY_SAVED_TEXT = "saved_text";

    // 添加数据暂存变量
    private String pendingDataForHomeFragment = null;
    private String pendingDataForProfileFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Lifecycle", "onCreate");

        initViews();
        setupRadioGroup();

        // 默认显示首页Fragment
        if (savedInstanceState == null) {
            showFragment(new HomeFragment(), "HomeFragment");
            radioGroup.check(R.id.radioHome);
        }

        // 恢复保存的数据
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
            tvSavedData.setText("恢复的数据: " + savedText);
        }
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
        etInput = findViewById(R.id.etInput);
        tvSavedData = findViewById(R.id.tvSavedData);
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment fragment = null;
            String tag = "";

            if (checkedId == R.id.radioHome) {
                fragment = new HomeFragment();
                tag = "HomeFragment";
            } else if (checkedId == R.id.radioProfile) {
                fragment = new ProfileFragment();
                tag = "ProfileFragment";
            } else if (checkedId == R.id.radioSettings) {
                fragment = new SettingsFragment();
                tag = "SettingsFragment";
            } else if (checkedId == R.id.radioData) {
                fragment = new DataTransferFragment();
                tag = "DataTransferFragment";
            }

            if (fragment != null) {
                showFragment(fragment, tag);
            }
        });
    }

    private void showFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();

        // 延迟检查暂存数据
        new android.os.Handler().postDelayed(() -> {
            if ("HomeFragment".equals(tag) && pendingDataForHomeFragment != null) {
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                        .findFragmentByTag("HomeFragment");
                if (homeFragment != null) {
                    homeFragment.receiveDataFromActivity(pendingDataForHomeFragment);
                    pendingDataForHomeFragment = null;
                    Log.d("DataTransfer", "暂存数据已发送到首页");
                }
            }

            if ("ProfileFragment".equals(tag) && pendingDataForProfileFragment != null) {
                ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager()
                        .findFragmentByTag("ProfileFragment");
                if (profileFragment != null) {
                    profileFragment.receiveDataFromOtherFragment(pendingDataForProfileFragment);
                    pendingDataForProfileFragment = null;
                    Log.d("FragmentTransfer", "暂存数据已发送到个人资料");
                }
            }
        }, 300);
    }

    // 场景B: Activity向Fragment传递数据
    public void sendDataToFragment(String data) {
        Log.d("DataTransfer", "准备发送数据到首页: " + data);

        // 如果首页Fragment正在显示，直接发送
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        if (currentFragment instanceof HomeFragment) {
            Log.d("DataTransfer", "首页正在显示，直接发送数据");
            ((HomeFragment) currentFragment).receiveDataFromActivity(data);
        } else {
            // 如果首页不在前台，保存数据并提示用户
            Log.d("DataTransfer", "首页不在前台，数据已暂存");
            pendingDataForHomeFragment = data;

            // 显示提示信息
            tvSavedData.setText("数据已发送！请切换到首页查看");
        }
    }

    // 场景A: Activity → Activity 数据传输
    public void navigateToDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("user_name", "张三");
        bundle.putInt("user_age", 25);
        bundle.putBoolean("is_student", true);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 场景B: Fragment向Activity返回数据的回调
    public void onFragmentResult(String result) {
        Log.d("FragmentResult", "接收到Fragment返回的数据: " + result);
        tvSavedData.setText("Fragment返回: " + result);
    }

    // 场景C: Fragment之间数据传输的中转方法
    public void transferDataBetweenFragments(String data, String targetFragmentTag) {
        Log.d("FragmentTransfer", "准备传输数据到: " + targetFragmentTag + ", 数据: " + data);

        if ("ProfileFragment".equals(targetFragmentTag)) {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentContainer);

            if (currentFragment instanceof ProfileFragment) {
                Log.d("FragmentTransfer", "个人资料Fragment正在显示，直接发送数据");
                ((ProfileFragment) currentFragment).receiveDataFromOtherFragment(data);
            } else {
                // 如果个人资料不在前台，保存数据
                Log.d("FragmentTransfer", "个人资料Fragment不在前台，数据已暂存");
                pendingDataForProfileFragment = data;

                // 显示提示信息
                tvSavedData.setText("数据已发送到个人资料！请切换到个人资料页面查看");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "onSaveInstanceState被调用 - 在onStop之前，onPause之后");

        // 保存EditText内容
        String inputText = etInput.getText().toString();
        outState.putString(KEY_SAVED_TEXT, inputText);

        Log.d("SavedData", "保存的数据: " + inputText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle", "onRestoreInstanceState被调用 - 在onStart之后");

        String savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
        tvSavedData.setText("恢复的数据: " + savedText);
    }
}

/*
package com.example.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fragmentdemo.fragments.DataTransferFragment;
import com.example.fragmentdemo.fragments.HomeFragment;
import com.example.fragmentdemo.fragments.ProfileFragment;
import com.example.fragmentdemo.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText etInput;
    private TextView tvSavedData;

    // 用于保存状态的键
    private static final String KEY_SAVED_TEXT = "saved_text";

    // 添加数据暂存变量
    private String pendingDataForHomeFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Lifecycle", "onCreate");

        initViews();
        setupRadioGroup();

        // 默认显示首页Fragment
        if (savedInstanceState == null) {
            showFragment(new HomeFragment());
            radioGroup.check(R.id.radioHome);
        }

        // 恢复保存的数据
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
            tvSavedData.setText(getString(R.string.restored_data, savedText));
        }
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
        etInput = findViewById(R.id.etInput);
        tvSavedData = findViewById(R.id.tvSavedData);
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment fragment = null;

            if (checkedId == R.id.radioHome) {
                fragment = new HomeFragment();
            } else if (checkedId == R.id.radioProfile) {
                fragment = new ProfileFragment();
            } else if (checkedId == R.id.radioSettings) {
                fragment = new SettingsFragment();
            } else if (checkedId == R.id.radioData) {
                fragment = new DataTransferFragment();
            }

            if (fragment != null) {
                final Fragment finalFragment = fragment; // 创建final变量
                showFragment(finalFragment);

                // 当切换到首页时，检查是否有暂存数据
                if (finalFragment instanceof HomeFragment && pendingDataForHomeFragment != null) {
                    // 延迟执行，确保Fragment视图已创建
                    new android.os.Handler().postDelayed(() -> {
                        ((HomeFragment) finalFragment).receiveDataFromActivity(pendingDataForHomeFragment);
                        pendingDataForHomeFragment = null;
                        Log.d("DataTransfer", "暂存数据已发送到首页");
                    }, 100);
                }
            }
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    // 场景B: Activity向Fragment传递数据
    public void sendDataToFragment(String data) {
        Log.d("DataTransfer", "准备发送数据到首页: " + data);

        // 如果首页Fragment正在显示，直接发送
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        if (currentFragment instanceof HomeFragment) {
            Log.d("DataTransfer", "首页正在显示，直接发送数据");
            ((HomeFragment) currentFragment).receiveDataFromActivity(data);
        } else {
            // 如果首页不在前台，保存数据并提示用户
            Log.d("DataTransfer", "首页不在前台，数据已暂存");
            pendingDataForHomeFragment = data;

            // 显示提示信息
            tvSavedData.setText(R.string.data_sent_please_switch);
        }
    }

    // 场景A: Activity → Activity 数据传输
    public void navigateToDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("user_name", "张三");
        bundle.putInt("user_age", 25);
        bundle.putBoolean("is_student", true);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 场景B: Fragment向Activity返回数据的回调
    public void onFragmentResult(String result) {
        Log.d("FragmentResult", "接收到Fragment返回的数据: " + result);
        tvSavedData.setText(getString(R.string.fragment_return, result));
    }

    // 场景C: Fragment之间数据传输的中转方法
    public void transferDataBetweenFragments(String data, String targetFragmentTag) {
        Log.d("FragmentTransfer", "准备传输数据到: " + targetFragmentTag + ", 数据: " + data);

        if ("ProfileFragment".equals(targetFragmentTag)) {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentContainer);

            if (currentFragment instanceof ProfileFragment) {
                Log.d("FragmentTransfer", "个人资料Fragment正在显示，直接发送数据");
                ((ProfileFragment) currentFragment).receiveDataFromOtherFragment(data);
            } else {
                Log.d("FragmentTransfer", "个人资料Fragment不在前台，数据已暂存");
                // 这里可以添加数据暂存逻辑，类似首页的处理方式
                // 暂时先提示用户切换到个人资料页面
                tvSavedData.setText("数据已发送到个人资料！请切换到个人资料页面查看");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "onSaveInstanceState被调用 - 在onStop之前，onPause之后");

        // 保存EditText内容
        String inputText = etInput.getText().toString();
        outState.putString(KEY_SAVED_TEXT, inputText);

        Log.d("SavedData", "保存的数据: " + inputText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle", "onRestoreInstanceState被调用 - 在onStart之后");

        String savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
        tvSavedData.setText(getString(R.string.restored_data, savedText));
    }
}*/
