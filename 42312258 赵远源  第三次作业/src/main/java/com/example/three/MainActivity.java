package com.example.three;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INPUT_TEXT = "key_input_text";

    private EditText inputEditText;
    private TextView restoredTextView;
    private RadioGroup navRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputEditText = findViewById(R.id.edit_input);
        restoredTextView = findViewById(R.id.text_restored);
        navRadioGroup = findViewById(R.id.rg_nav);

        setupFragmentResultMediations();
        setupRadioGroup();
        setupMainToDetailButton();

        if (savedInstanceState != null) {
            String restored = savedInstanceState.getString(KEY_INPUT_TEXT, "");
            restoredTextView.setText(restored);
        }

        // 默认选中第一个，触发加载 Fragment
        if (navRadioGroup.getCheckedRadioButtonId() == -1) {
            navRadioGroup.check(R.id.rb_home);
        }
    }

    private void setupMainToDetailButton() {
        findViewById(R.id.btn_main_to_detail).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("username", "Alice");
            intent.putExtra("age", 23);
            intent.putExtra("isStudent", true);
            startActivity(intent);
        });
    }

    private void setupRadioGroup() {
        navRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment fragment = null;
            String tag = null;
            if (checkedId == R.id.rb_home) {
                fragment = new HomeFragment();
                tag = "HomeFragment";
            } else if (checkedId == R.id.rb_discover) {
                DiscoverFragment f = new DiscoverFragment();
                Bundle args = new Bundle();
                args.putString("init_msg", "来自 MainActivity 的初始数据");
                f.setArguments(args);
                fragment = f;
                tag = "DiscoverFragment";
            } else if (checkedId == R.id.rb_notify) {
                fragment = new NotifyFragment();
                tag = "NotifyFragment";
            } else if (checkedId == R.id.rb_profile) {
                fragment = new ProfileFragment();
                tag = "ProfileFragment";
            }
            if (fragment != null) {
                replaceFragment(fragment, tag);
            }
        });
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();
    }

    private void setupFragmentResultMediations() {
        // Fragment -> Activity 返回结果（场景B的一半）
        getSupportFragmentManager().setFragmentResultListener(
                "result_from_fragment",
                this,
                (requestKey, bundle) -> {
                    String result = bundle.getString("result", "");
                    Log.d(TAG, "收到 Fragment 返回结果: " + result);
                    restoredTextView.setText(result);
                }
        );

        // FragmentA -> Activity -> FragmentB 中转（场景C）
        getSupportFragmentManager().setFragmentResultListener(
                "to_fragment_b",
                this,
                (requestKey, bundle) -> {
                    // Activity 收到 A 的数据后，立刻转发为另一个 resultKey，B 正在监听
                    getSupportFragmentManager().setFragmentResult("from_activity_to_b", bundle);
                }
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState 被调用（生命周期：在 onStop 之前或旋转前）");
        String text = inputEditText != null ? inputEditText.getText().toString() : "";
        outState.putString(KEY_INPUT_TEXT, text);
    }
}