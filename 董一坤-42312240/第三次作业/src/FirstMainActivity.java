package com.dyk.assignments.assignment_4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dyk.assignments.R;
import com.dyk.assignments.assignment_4.fragment.ExportFragment;
import com.dyk.assignments.assignment_4.fragment.HomeFragment;
import com.dyk.assignments.assignment_4.fragment.MessageFragment;
import com.dyk.assignments.assignment_4.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

public class FirstMainActivity extends AppCompatActivity {

    private FrameLayout fl_insert;
    private RadioGroup rg_main;
    private HomeFragment mHomeFragment;
    private ExportFragment mExportFragment;
    private MessageFragment mMessageFragment;
    private PersonFragment mPersonFragment;
    List<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private RadioButton rb_home;
    private RadioButton rb_person;
    private RadioButton rb_export;
    private RadioButton rb_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_main);

        fl_insert = findViewById(R.id.fl_insert);
        rg_main = findViewById(R.id.rg_main);
        rb_home = findViewById(R.id.rb_home);
        rb_person = findViewById(R.id.rb_person);
        rb_export = findViewById(R.id.rb_export);
        rb_message = findViewById(R.id.rb_message);

        initView();

        rg_main.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.rb_home) {
                switchFragment(mHomeFragment);
                rb_home.setChecked(true);
            }else if(checkedId == R.id.rb_export) {
                switchFragment(mExportFragment);
                rb_export.setChecked(true);
            }else if(checkedId == R.id.rb_message) {
                    switchFragment(mMessageFragment);
                    rb_message.setChecked(true);
            }else if(checkedId == R.id.rb_person) {
                switchFragment(mPersonFragment);
                rb_person.setChecked(true);
            }else if(checkedId ==  -1) {
                Toast.makeText(FirstMainActivity.this, "点错了", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }

    private void initView() {
        mPersonFragment = new PersonFragment();
        mExportFragment = new ExportFragment();
        mMessageFragment = new MessageFragment();
        mHomeFragment = new HomeFragment();

        fragments.add(mPersonFragment);
        fragments.add(mExportFragment);
        fragments.add(mMessageFragment);
        fragments.add(mHomeFragment);

        rb_home.setChecked(true);

        Bundle info = getIntent().getBundleExtra("info");
        mHomeFragment.setArguments(info);
        if(info!=null){
            mHomeFragment.setFragmentCallback(this::returnToFirstActivity);
        }
        switchFragment(mHomeFragment);
    }

    protected void switchFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        if(!fragment.isAdded()){
            transaction.add(R.id.fl_insert,fragment);
        }
        for(Fragment frag: fragments){
            if(frag.equals(fragment)){
                transaction.show(frag);
            }else{
                transaction.hide(frag);
            }
        }
        transaction.commit();
    }

    private void returnToFirstActivity(String result) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("final_result", result);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onDataFromExport(String userInterest) {
        mPersonFragment.receiveData(userInterest);
        switchFragment(mPersonFragment);
        rb_person.setChecked(true);
    }
}