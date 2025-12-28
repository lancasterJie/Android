package com.example.bundledemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    private TextView tvContent;
    private Button btnChange;
    private int clickCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvContent = view.findViewById(R.id.tv_fragment1_content);
        btnChange = view.findViewById(R.id.btn_fragment1_change);

        // 恢复保存的状态
        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt("click_count", 0);
            tvContent.setText("Fragment 1 - 按钮点击次数: " + clickCount);
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                tvContent.setText("Fragment 1 - 按钮点击次数: " + clickCount);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("click_count", clickCount);
    }
}