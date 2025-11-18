package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private static final String KEY_EDIT_TEXT = "editTextValue";

    private RadioGroup mRadioGroup;
    private Fragment Fg1, Fg2, Fg3, Fg4;
    private EditText etInput;
    private TextView tvState;
    private String fragmentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate被调用");

        // 恢复保存的状态
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_EDIT_TEXT, "");
            fragmentData = savedInstanceState.getString("fragmentData", "");
            etInput = findViewById(R.id.et_input);
            tvState = findViewById(R.id.tv_state);
            etInput.setText(savedText);
            tvState.setText("恢复的内容: " + savedText + "\nFragment数据: " + fragmentData);
        }

        initFragments();
        initView();
    }

    private void initFragments() {
        // 使用newInstance方法传递数据
        Fg1 = Fragment1.newInstance("张三");
        Fg2 = new Fragment2();
        Fg3 = new Fragment3();
        Fg4 = new Fragment4();
    }

    public void initView(){
        etInput = findViewById(R.id.et_input);
        tvState = findViewById(R.id.tv_state);

        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment selectedFragment = null;

                if(checkedId == R.id.button1){
                    selectedFragment = Fg1;
                }
                else if(checkedId == R.id.button2){
                    selectedFragment = Fg2;
                    // 场景C: Fragment间通信 - 传递数据到Fragment2
                    if (selectedFragment instanceof Fragment2) {
                        ((Fragment2) selectedFragment).updateData(fragmentData);
                    }
                }
                else if(checkedId == R.id.button3){
                    selectedFragment = Fg3;
                }
                else if(checkedId == R.id.button4){
                    selectedFragment = Fg4;
                }

                if (selectedFragment != null) {
                    transaction.replace(R.id.myFragment, selectedFragment).commit();
                }
            }
        });

        // 设置默认选中的RadioButton
        mRadioGroup.check(R.id.button1);

        // 场景A: Activity到Activity的数据传递
        setupActivityLaunch();
    }

    private void setupActivityLaunch() {
        // 可以在某个Fragment中添加按钮来启动DetailActivity
        // 这里简化实现，实际中可以放在Fragment的按钮点击事件中
    }

    // 场景B: Fragment向Activity返回数据
    @Override
    public void onDataReceived(String data) {
        fragmentData = data;
        tvState.setText("从Fragment接收: " + data);
    }

    // 屏幕旋转状态保存
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState被调用 - 保存状态");

        String inputText = etInput.getText().toString();
        outState.putString(KEY_EDIT_TEXT, inputText);
        outState.putString("fragmentData", fragmentData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState被调用 - 恢复状态");
    }

    // 启动DetailActivity的方法（场景A）
    private void launchDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userName", "张三");
        bundle.putInt("age", 25);
        bundle.putBoolean("isStudent", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}