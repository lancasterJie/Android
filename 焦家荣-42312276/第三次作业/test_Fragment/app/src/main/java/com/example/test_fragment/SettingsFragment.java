package com.example.test_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private static final String ARG_EDIT_TEXT_CONTENT = "edit_text_content";
    private static final String KEY_SWITCH_STATE = "switch_state";

    private TextView textSettings;
    private SwitchCompat notificationSwitch;
    private EditText editTextSettings;
    private TextView textSavedContent;
    public static SettingsFragment newInstance(String editTextContent) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EDIT_TEXT_CONTENT, editTextContent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        textSettings = view.findViewById(R.id.text_settings);
        notificationSwitch = view.findViewById(R.id.switch_notifications);
        editTextSettings = view.findViewById(R.id.edit_text_settings);
        textSavedContent = view.findViewById(R.id.text_saved_content);

        // 恢复保存的状态
        if (savedInstanceState != null) {
            boolean switchState = savedInstanceState.getBoolean(KEY_SWITCH_STATE, false);
            notificationSwitch.setChecked(switchState);
            updateSettingsText(switchState);
        }

        // 设置初始的EditText内容
        if (getArguments() != null) {
            String initialContent = getArguments().getString(ARG_EDIT_TEXT_CONTENT);
            if (initialContent != null && !initialContent.isEmpty()) {
                editTextSettings.setText(initialContent);
                textSavedContent.setText("恢复的内容: " + initialContent);
            }
        }

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSettingsText(isChecked);
        });

        return view;
    }

    private void updateSettingsText(boolean isChecked) {
        textSettings.setText("通知设置: " + (isChecked ? "开启" : "关闭"));
    }

    // 状态保存
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存Switch状态
        outState.putBoolean(KEY_SWITCH_STATE, notificationSwitch.isChecked());
    }

    // 获取EditText内容，供Activity保存状态使用
    public String getEditTextContent() {
        if (editTextSettings != null) {
            return editTextSettings.getText().toString();
        }
        return "";
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String content = editTextSettings.getText().toString();
            if (!content.isEmpty()) {
                textSavedContent.setText("当前内容: " + content);
            }
        }
    }
}