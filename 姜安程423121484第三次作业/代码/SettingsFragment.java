package com.example.homework3;  // 根据你的包名调整

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private OnSettingChangeListener listener;

    public interface OnSettingChangeListener {
        void onSettingChanged(String setting);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 修复inflate方法调用
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btnSendToOtherFragment = view.findViewById(R.id.btnSendToOtherFragment);

        // 修复Lambda表达式和逻辑判断
        btnSendToOtherFragment.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSettingChanged("设置已更新: " + System.currentTimeMillis());
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingChangeListener) {
            listener = (OnSettingChangeListener) context;
        }
    }
}
