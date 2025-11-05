package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    Button b1, b2, b3, b4, b5, b6, b7, b8;
    TextView tv;
    EditText editText;
    RadioGroup radioGroup;
    public static final String TAG = "MainActivity_Log";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "OnCreate");
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);

        b7 = new Button(this);
        b7.setText("button7");
        ((LinearLayout) findViewById(R.id.main)).addView(b7);
        tv = findViewById(R.id.tv);
        editText = findViewById(R.id.editTextText);
        radioGroup = findViewById(R.id.radioGroup);



        b8 = findViewById(R.id.button_sedmsg);

        b1.setOnClickListener(new MyListener1());
        b2.setOnClickListener(new MyListener1());
        b3.setOnClickListener(new MyListener1());
        b4.setOnClickListener(new MyListener1());
        b5.setOnClickListener(new MyListener1());
        b6.setOnClickListener(new MyListener1());
        b8.setOnClickListener(new MyListener1());

        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, oneFragment);

            fragmentTransaction.commit();
        }


        Bundle msg = new Bundle();
        Bundle msgForTwo = new Bundle();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: checkedId = " + checkedId);


                if (checkedId == R.id.radioButtonOne) {
                    msg.putString("msg", "传入fragmentOne");
                    oneFragment.setArguments(msg);

                    oneFragment.setResultListener((isSuccess, resultMsg) -> {
                        if (isSuccess) {
                            ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                            msgForTwo.putString("fragment", "将传入fragmentOne数据传入fragmentTwo");
                        } else {
                            Toast.makeText(MainActivity.this, "失败：" + resultMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    hideOtherFragment(oneFragment);
                }
                else if (checkedId == R.id.radioButtonTwo) {
//                    msg.putString("msg", "传入fragmentTwo");
//                    twoFragment.setArguments(msg);

                    if (msgForTwo.getString("fragment") != null){
                        Log.d("fragment", "非空的");
                        twoFragment.setArguments(msgForTwo);
                    }
                    else{
                        Log.d("fragment", "空的");
                    }

                    hideOtherFragment(twoFragment);
                    twoFragment.setResultListener((isSuccess, resultMsg) -> {
                        if (isSuccess && resultMsg != null) {
                            ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                        } else {
                            ((TextView) findViewById(R.id.tv)).setText("未传入");
                        }
                    });
                }
                else if (checkedId == R.id.radioButtonThree) {
                    msg.putString("msg", "传入fragmentThree");
                    threeFragment.setArguments(msg);
                    hideOtherFragment(threeFragment);
                    threeFragment.setResultListener((isSuccess, resultMsg) -> {
                        if (isSuccess) {
                            ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                        } else {
                            Toast.makeText(MainActivity.this, "失败：" + resultMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (checkedId == R.id.radioButtonFour) {
                    msg.putString("msg", "传入fragmentFour");
                    fourFragment.setArguments(msg);
                    hideOtherFragment(fourFragment);
                    fourFragment.setResultListener((isSuccess, resultMsg) -> {
                        if (isSuccess) {
                            ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                        } else {
                            Toast.makeText(MainActivity.this, "失败：" + resultMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString("editText");
            tv.setText(savedText);
            int checkedRadioButtonId = savedInstanceState.getInt("checkedRadioButtonId", -1);
            if (checkedRadioButtonId != -1) {
                radioGroup.check(checkedRadioButtonId);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editText", editText.getText().toString());
        outState.putInt("checkedRadioButtonId", radioGroup.getCheckedRadioButtonId());
        Log.d(TAG, "onSaveInstanceState");
    }

    public void hideOtherFragment(Fragment fragmentToShow) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!fragmentToShow.isAdded()) {
            fragmentTransaction.add(R.id.fragmentContainer, fragmentToShow);
        }

        for (Fragment fragment : fragmentList) {
            if (fragment != fragmentToShow) {
                fragmentTransaction.hide(fragment);
            } else {
                fragmentTransaction.show(fragmentToShow);
                Log.d(TAG, "hideOtherFragment: fragmentToShow = " + fragmentToShow);
            }
        }

        fragmentTransaction.commit();
    }

    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button1) {
                Toast.makeText(MainActivity.this, "peace and love", Toast.LENGTH_LONG).show();
            }
            else if (view.getId() == R.id.button2) {
                Intent it = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(it);
            }
            else if (view.getId() == R.id.button3) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.snnu.edu.cn"));
                startActivity(it);
            }
            else if (view.getId() == R.id.button4) {
                Intent it = new Intent("com.package.myapplication1.ThirdActivity");
                startActivity(it);
            }
            else if (view.getId() == R.id.button5) {
                Intent it = new Intent(MainActivity.this, ForceActivity.class);
                startActivityForResult(it, 1);
            }
            else if (view.getId() == R.id.button6) {
                Intent it = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(it);
            }
            else if (view.getId() == R.id.button_sedmsg) {
                Intent it = new Intent(MainActivity.this, DetailActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("name", "张三");
                bundle.putInt("age", 20);
                bundle.putBoolean("isStudent", true);

                it.putExtras(bundle);
                startActivity(it);

            }
        }
    }
}