package com.example.fragmentapp;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragmentapp.fragments.Fragment1;
import com.example.fragmentapp.fragments.Fragment2;
import com.example.fragmentapp.fragments.Fragment3;
import com.example.fragmentapp.fragments.Fragment4;

public class MainActivity extends AppCompatActivity
        implements Fragment2.OnDataReturnListener, Fragment4.OnFragmentDataListener {

    private RadioGroup radioGroup;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        initRadioGroup();

        switchFragment(fragment1);

        if (fragment2 != null) {
            Bundle args = new Bundle();
            args.putString("initial_data", "这是从Activity传递的初始数据");
            fragment2.setArguments(args);
            fragment2.setOnDataReturnListener(this);
        }

        if (fragment4 != null) {
            fragment4.setOnFragmentDataListener(this);
        }
    }

    private void initFragments() {
        fragment1 = new Fragment1();
        fragment2 = Fragment2.newInstance("默认数据");
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
    }

    private void initRadioGroup() {
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1) {
                    switchFragment(fragment1);
                } else if (checkedId == R.id.radioButton2) {
                    switchFragment(fragment2);
                } else if (checkedId == R.id.radioButton3) {
                    switchFragment(fragment3);
                } else if (checkedId == R.id.radioButton4) {
                    switchFragment(fragment4);
                }
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        if (fragment == null || fragment == currentFragment) {
            return;
        }

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

    @Override
    public void onDataReturn(String data) {
        Toast.makeText(this, "从Fragment2返回的数据: " + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSentToFragment3(String data) {
        if (fragment3 != null) {
            fragment3.receiveDataFromFragment4(data);
            switchFragment(fragment3);
            radioGroup.check(R.id.radioButton3);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int checkedId = radioGroup.getCheckedRadioButtonId();
        outState.putInt("checked_radio", checkedId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int checkedId = savedInstanceState.getInt("checked_radio", R.id.radioButton1);
        radioGroup.check(checkedId);
    }
}