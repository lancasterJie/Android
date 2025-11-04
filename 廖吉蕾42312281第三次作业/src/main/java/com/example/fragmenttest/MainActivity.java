package com.example.fragmenttest;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private SelfFragment selfFragment;
    private SettingFragment settingFragment;

    // 当前显示的 Fragment
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        initViews();
        setupRadioGroupListener();

        // 默认显示首页
        showFragment(homeFragment);
        radioGroup.check(R.id.radiohome);
    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        selfFragment = new SelfFragment();
        settingFragment = new SettingFragment();
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void setupRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiohome) {
                    showFragment(homeFragment);
                } else if (checkedId == R.id.radiomessge) {
                    showFragment(messageFragment);
                } else if (checkedId == R.id.radioself) {
                    showFragment(selfFragment);
                } else if (checkedId == R.id.radiosetting) {
                    showFragment(settingFragment);
                }
            }
        });
    }

    private void showFragment(Fragment fragment) {
        if (fragment == currentFragment) {
            return; // 如果已经是当前 Fragment，则不重复添加
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 如果当前有显示的 Fragment，先隐藏
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        // 如果 Fragment 已经添加过，就显示；否则添加
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fragmentContainer, fragment);
        }

        transaction.commit();
        currentFragment = fragment;
    }
}