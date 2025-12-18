package com.example.homework3;  // 根据你的包名调整

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements SettingsFragment.OnSettingChangeListener {

    private RadioGroup radioGroup;
    private FrameLayout fragmentContainer;
    private EditText etInput;
    private TextView tvSavedData;

    private String fragmentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Lifecycle", "onCreate");  // 修复Log语句

        initViews();
        setupRadioGroup();
        setupFragment();

        /// 场景A：启动DetailActivity传递数据
        Button btnStartDetail = findViewById(R.id.btnStartDetail);
        if (btnStartDetail != null) {
            btnStartDetail.setOnClickListener(v -> startDetailActivity());
        }

// 恢复保存的状态
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString("SAVED_TEXT", "");
            tvSavedData.setText("恢复的数据: " + savedText);
            fragmentData = savedInstanceState.getString("FRAGMENT_DATA", "");
        }
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        etInput = findViewById(R.id.etInput);
        tvSavedData = findViewById(R.id.tvSavedData);
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment fragment = null;

            if (checkedId == R.id.radioHome) {
                // 场景B: Activity向Fragment传递数据
                fragment = HomeFragment.newInstance("张三");
            } else if (checkedId == R.id.radioProfile) {
                fragment = new ProfileFragment();
            } else if (checkedId == R.id.radioSettings) {
                fragment = new SettingsFragment();
            } else if (checkedId == R.id.radioAbout) {
                fragment = new AboutFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
            }
        });

        // 默认选中首页
        radioGroup.check(R.id.radioHome);
    }

    private void setupFragment() {
        // 初始显示HomeFragment并传递数据
        HomeFragment homeFragment = HomeFragment.newInstance("张三");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .commit();
    }

    // 场景A: 启动DetailActivity
    private void startDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);

        // 创建Bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString("USER_NAME", "张三");
        bundle.putInt("USER_AGE", 25);
        bundle.putBoolean("IS_STUDENT", true);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 场景B: Fragment向Activity返回结果
    public void onFragmentResult(String result) {
        Toast.makeText(this, "收到Fragment结果: " + result, Toast.LENGTH_SHORT).show();
        fragmentData = result;
    }

    // 场景C: Fragment之间通过Activity中转数据
    @Override
    public void onSettingChanged(String setting) {
        // 这里可以将数据传递给其他Fragment
        Toast.makeText(this, "设置变更: " + setting, Toast.LENGTH_SHORT).show();
        fragmentData = setting;

        // 例如传递给AboutFragment
        AboutFragment aboutFragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString("SETTING_UPDATE", setting);
        aboutFragment.setArguments(args);
    }

    // 3. 屏幕旋转状态保存
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "onSaveInstanceState - 在onStop之前，onPause之后调用");

        // 保存EditText内容
        String inputText = etInput.getText().toString();
        outState.putString("SAVED_TEXT", inputText);
        outState.putString("FRAGMENT_DATA", fragmentData);

        Log.d("StateSave", "保存的数据: " + inputText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle", "onRestoreInstanceState");

        String savedText = savedInstanceState.getString("SAVED_TEXT");
        if (savedText != null && !savedText.isEmpty()) {
            tvSavedData.setText("恢复的数据: " + savedText);
        }
    }
}
