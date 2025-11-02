package com.example.experiment03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

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

        initViews();
        initFragments();
        setupRadioGroup();
        detailActivity.setOnClickListener(this::onClick);
    }
    private void initViews() {
        radioGroup = findViewById(R.id.radio_group);
        fragmentContainer = findViewById(R.id.fragment_container);
        detailActivity = findViewById(R.id.detail_activity);
    }

    private void initFragments() {
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        forthFragment = new ForthFragment();

        selectedFragment = firstFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment, "first")
                .add(R.id.fragment_container, secondFragment, "second")
                .add(R.id.fragment_container, thirdFragment, "third")
                .add(R.id.fragment_container, forthFragment, "forth")
                .hide(secondFragment)
                .hide(thirdFragment)
                .hide(forthFragment)
                .show(selectedFragment)
                .commit();
    }
    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Bundle bundle = result.getData().getExtras();
            if (bundle != null) {
                firstFragment.setMassage(bundle);
            }
        }
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_first) {
                switchFragment(firstFragment);
            } else if (checkedId == R.id.radio_second) {
                secondFragment.setMessage( firstFragment.getMessage());
                switchFragment(secondFragment);
            } else if (checkedId == R.id.radio_third) {
                switchFragment(thirdFragment);
            } else if (checkedId == R.id.radio_forth) {
                switchFragment(forthFragment);
            }
        });
    }
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(selectedFragment)
                .show(fragment)
                .commit();
        selectedFragment = fragment;
    }
    private void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = firstFragment.getMessage();
        intent.putExtras(bundle);
        startActivityForResult(intent,0,bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                firstFragment.setMassage(bundle);
            }
        }
    }

    private RadioGroup radioGroup;
    private FrameLayout fragmentContainer;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private ForthFragment forthFragment;
    private Fragment selectedFragment = null;
    private Button detailActivity;


}