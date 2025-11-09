package com.example.fragmentdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private static final String KEY_SAVED_TEXT = "saved_text";
    private static final String KEY_EDIT_TEXT = "edit_text";
    private static final String ARG_SAVED_TEXT = "arg_saved_text";

    private EditText etInput;
    private TextView tvSavedText;
    private String savedText = "";
    private String editTextContent = "";

    // 使用静态方法创建Fragment并传递参数
    public static SettingsFragment newInstance(String savedText) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SAVED_TEXT, savedText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SettingsFragment.onCreate被调用");

        // 从参数获取初始数据
        if (getArguments() != null) {
            savedText = getArguments().getString(ARG_SAVED_TEXT, "");
            editTextContent = savedText;
        }

        // 恢复保存的状态
        if (savedInstanceState != null) {
            String restoredText = savedInstanceState.getString(KEY_SAVED_TEXT, "");
            if (!TextUtils.isEmpty(restoredText)) {
                savedText = restoredText;
                editTextContent = restoredText;
                System.out.println("onCreate恢复文本: " + restoredText);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        etInput = view.findViewById(R.id.etInput);
        tvSavedText = view.findViewById(R.id.tvSavedText);

        System.out.println("SettingsFragment.onCreateView被调用，editTextContent: " + editTextContent);

        // 设置EditText的文本
        if (!TextUtils.isEmpty(editTextContent)) {
            etInput.setText(editTextContent);
            tvSavedText.setText("当前内容: " + editTextContent);
        }

        // 使用TextWatcher实时监听文本变化
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 实时更新保存的文本
                String currentText = s.toString();
                savedText = currentText;
                editTextContent = currentText;

                if (!TextUtils.isEmpty(currentText)) {
                    tvSavedText.setText("当前内容: " + currentText);
                } else {
                    tvSavedText.setText("保存的内容将显示在这里");
                }

                System.out.println("文本变化: " + currentText);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // 保存当前文本
        String currentText = savedText;
        if (etInput != null) {
            currentText = etInput.getText().toString();
        }

        outState.putString(KEY_SAVED_TEXT, currentText);
        outState.putString(KEY_EDIT_TEXT, currentText);

        System.out.println("=== onSaveInstanceState被调用 ===");
        System.out.println("保存的文本: " + currentText);
        System.out.println("调用栈:");
        Thread.dumpStack();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        System.out.println("SettingsFragment.onViewStateRestored被调用");

        if (savedInstanceState != null) {
            String restoredText = savedInstanceState.getString(KEY_EDIT_TEXT, "");
            if (!TextUtils.isEmpty(restoredText)) {
                editTextContent = restoredText;
                savedText = restoredText;
                if (etInput != null) {
                    etInput.setText(restoredText);
                }
                if (tvSavedText != null) {
                    tvSavedText.setText("当前内容: " + restoredText);
                }
                System.out.println("onViewStateRestored恢复文本: " + restoredText);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("SettingsFragment.onDestroyView被调用");
    }

    // 提供方法让Activity设置保存的文本
    public void setSavedText(String text) {
        this.savedText = text;
        this.editTextContent = text;
        if (tvSavedText != null) {
            tvSavedText.setText("保存的内容: " + text);
        }
        if (etInput != null) {
            etInput.setText(text);
        }

        // 更新参数
        if (getArguments() != null) {
            getArguments().putString(ARG_SAVED_TEXT, text);
        }
    }

    // 获取当前保存的文本
    public String getSavedText() {
        return savedText;
    }
}