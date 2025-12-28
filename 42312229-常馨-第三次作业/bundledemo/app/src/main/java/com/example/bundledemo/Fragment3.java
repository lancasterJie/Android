package com.example.bundledemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {

    private static final String KEY_SAVED_TEXT = "saved_text";

    private EditText etRotateInput;
    private TextView tvRotateDisplay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        etRotateInput = view.findViewById(R.id.et_rotate_input);
        tvRotateDisplay = view.findViewById(R.id.tv_rotate_display);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 恢复保存的状态
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
            if (!savedText.isEmpty()) {
                etRotateInput.setText(savedText);
                tvRotateDisplay.setText("恢复的文本: " + savedText);
            }
        } else {
            tvRotateDisplay.setText("输入文本后旋转屏幕测试");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存EditText的内容
        if (etRotateInput != null) {
            String currentText = etRotateInput.getText().toString();
            outState.putString(KEY_SAVED_TEXT, currentText);
            System.out.println("Fragment3 - onSaveInstanceState被调用，保存的文本: " + currentText);
        }
    }
}