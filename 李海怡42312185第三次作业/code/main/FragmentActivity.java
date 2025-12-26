package com.example.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivity extends AppCompatActivity {
    private RadioGroup mRg;
    private RadioButton home;
    private RadioButton community;
    private RadioButton message;
    private RadioButton me;
    private FrameLayout mframeLayout;
    private List<Fragment> mFragments = new ArrayList<>();
    private HomeFragment homeFragment;
    private CommunityFragment communityFragment;
    private MessageFragment messageFragment;
    private MeFragment meFragment;
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mTransaction;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frag_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mframeLayout = findViewById(R.id.frameLayout);
        mRg = findViewById(R.id.rg_main);
        home = findViewById(R.id.b_home);
        community = findViewById(R.id.b_community);
        message = findViewById(R.id.b_message);
        me = findViewById(R.id.b_me);
        initView();
    }

    private void initView() {
        mSupportFragmentManager = getSupportFragmentManager();
        mTransaction = mSupportFragmentManager.beginTransaction();

        mRg.check(R.id.b_home);
        homeFragment = new HomeFragment();
        mFragments.add(homeFragment);
        hideOtherFragment(homeFragment,true);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.b_home) {
                    hideOtherFragment(homeFragment, false);
                } else if(checkedId == R.id.b_community) {
                    if (communityFragment == null) {
                        communityFragment = new CommunityFragment();
                        mFragments.add(communityFragment);
                        hideOtherFragment(communityFragment, true);
                    } else {
                        hideOtherFragment(communityFragment, false);}
                } else if(checkedId == R.id.b_message){
                    if(messageFragment == null){
                        messageFragment = new MessageFragment();
                        mFragments.add(messageFragment);
                        hideOtherFragment(messageFragment,true);
                    } else {
                        hideOtherFragment(messageFragment,false);
                    }
                } else if(checkedId == R.id.b_me){
                    if(meFragment == null){
                        meFragment = new MeFragment();
                        mFragments.add(meFragment);
                        hideOtherFragment(meFragment,true);
                    } else {
                        hideOtherFragment(meFragment,false);
                    }

                }

            }
        });
    }
    private void hideOtherFragment(Fragment showFragment, boolean add){
        mTransaction = mSupportFragmentManager.beginTransaction();
        if(add){
            mTransaction.add(R.id.frameLayout, showFragment);
        }

        for( Fragment fragment : mFragments){
            if(showFragment.equals(fragment)){
                mTransaction.show(fragment);
            } else {
                mTransaction.hide(fragment);
            }
        }
        mTransaction.commit();
    }
}
