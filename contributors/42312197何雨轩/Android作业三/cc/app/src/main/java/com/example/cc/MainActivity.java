package com.example.cc;

import android.os.Bundle;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentB.OnDataReturnListener {

    private FragmentA fragmentA;
    private FragmentB fragmentB;
    private FragmentC fragmentC;
    private FragmentD fragmentD;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        setupRadioGroup();

        // 显示默认Fragment
        showFragment(fragmentA);
    }

    private void initFragments() {
        fragmentA = new FragmentA();
        fragmentB = new FragmentB();
        fragmentC = new FragmentC();
        fragmentD = new FragmentD();

        // 设置FragmentB的数据返回监听器
        fragmentB.setOnDataReturnListener(this);
        // 设置Fragment间的通信
        fragmentC.setTargetFragmentD(fragmentD);
    }

    private void setupRadioGroup() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioFragmentA) {
                    showFragment(fragmentA);
                } else if (checkedId == R.id.radioFragmentB) {
                    showFragment(fragmentB);
                    // 传递数据到FragmentB
                    sendDataToFragmentB();
                } else if (checkedId == R.id.radioFragmentC) {
                    showFragment(fragmentC);
                } else if (checkedId == R.id.radioFragmentD) {
                    showFragment(fragmentD);
                }
            }
        });

        // 默认选中第一个
        radioGroup.check(R.id.radioFragmentA);
    }

    private void showFragment(Fragment fragment) {
        if (fragment == currentFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (!fragment.isAdded()) {
            transaction.add(R.id.fragmentContainer, fragment);
        } else {
            transaction.show(fragment);
        }

        transaction.commit();
        currentFragment = fragment;
    }

    // 场景B: 向FragmentB传递数据
    private void sendDataToFragmentB() {
        if (fragmentA != null) {
            String inputFromA = fragmentA.getFourthInput();
            if (inputFromA != null && !inputFromA.isEmpty()) {
                fragmentB.receiveDataFromActivity(inputFromA);
            }
        }
    }

    // 场景B: 接收从FragmentB返回的数据
    @Override
    public void onDataReturn(String reversedData) {
        if (fragmentA != null) {
            fragmentA.displayReturnedData(reversedData);
        }
    }
}