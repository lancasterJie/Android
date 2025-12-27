package com.example.myproject3;

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

    private FrameLayout mFrameLayout;//容器，用于显示Fragment

    private RadioGroup mRg;
    private RadioButton home;

    private RadioButton community;

    private RadioButton message;

    private RadioButton me;

    private List<Fragment> mFragments=new ArrayList<>();

    private Fragment1 homeFragment;

    private Fragment2 communityFragment;

    private Fragment3 messageFragment;

    private Fragment4 meFragment;

    private FragmentManager mSupportFragmntManagger;

    private FragmentTransaction mTansaction;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mFrameLayout = findViewById(R.id.frameLayout);
        mRg=findViewById(R.id.rg_main);
        home=findViewById(R.id.rb_home);
        community=findViewById(R.id.rb_community);
        message=findViewById(R.id.rb_message);
        me=findViewById(R.id.rb_me);
        initView();//用于初始化界面
    }

    private void initView()
    {
        mSupportFragmntManagger=getSupportFragmentManager();
        mTansaction=mSupportFragmntManagger.beginTransaction();
        mRg.check(R.id.rb_home);//默认选择“首页”按钮
        homeFragment=new Fragment1();
        mFragments.add(homeFragment);
        hideOthersFragment(homeFragment,true);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId)
            {
                if(checkedId==R.id.rb_home)
                {
                    hideOthersFragment(homeFragment, false);
                }
                else if (checkedId==R.id.rb_community)
                {
                    if(communityFragment==null)
                    {
                        communityFragment=new Fragment2();
                        mFragments.add(communityFragment);
                        hideOthersFragment(communityFragment,true);
                    }
                    else
                    {
                        hideOthersFragment(communityFragment,false);
                    }
                }
                else if (checkedId==R.id.rb_message)
                {
                    if(messageFragment==null)
                    {
                        messageFragment=new Fragment3();
                        mFragments.add(messageFragment);
                        hideOthersFragment(messageFragment,true);
                    }
                    else
                    {
                        hideOthersFragment(messageFragment,false);
                    }
                }
                else if (checkedId==R.id.rb_me)
                {
                    if(meFragment==null)
                    {
                        meFragment=new Fragment4();
                        mFragments.add(meFragment);
                        hideOthersFragment(meFragment,true);
                    }
                    else
                    {
                        hideOthersFragment(meFragment,false);
                    }
                }
            }
        });
    }

    private void hideOthersFragment(Fragment showFragment,boolean add)//核心Fragment切换逻辑
    {
        mTansaction=mSupportFragmntManagger.beginTransaction();
        if(add)
        {
            mTansaction.add(R.id.frameLayout,showFragment);
        }
        for(Fragment fragment:mFragments)
        {
            if(showFragment.equals(fragment))
            {
                mTansaction.show(fragment);
            }
            else
            {
                mTansaction.hide(fragment);
            }
        }
        mTansaction.commit();
    }
}
