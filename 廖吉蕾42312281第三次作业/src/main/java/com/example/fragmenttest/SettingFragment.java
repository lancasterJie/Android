package com.example.fragmenttest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingFragment extends Fragment {

    private TextView textVersion, textDescription;
    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        textVersion = view.findViewById(R.id.textVersion);
        textDescription = view.findViewById(R.id.textDescription);
        textVersion.setText("版本: 1.0.0");
        textDescription.setText("这是一个使用 RadioGroup 控制 Fragment 切换的示例应用。\n\n" +
                "功能特点:\n" +
                "• 底部导航切换\n" +
                "• 四个不同功能的 Fragment\n" +
                "• 自定义 RadioButton 样式\n" +
                "• 流畅的切换动画\n"+
                "• 底部导航切换\n" +
                "• 四个不同功能的 Fragment\n" +
                "• 自定义 RadioButton 样式\n" +
                "• 流畅的切换动画\n"+
                "• 底部导航切换\n" +
                "• 四个不同功能的 Fragment\n" +
                "• 自定义 RadioButton 样式\n" +
                "• 流畅的切换动画\n"+
                "• 底部导航切换\n" +
                "• 四个不同功能的 Fragment\n" +
                "• 自定义 RadioButton 样式\n" +
                "• 流畅的切换动画\n"+
                "• 底部导航切换\n" +
                "• 四个不同功能的 Fragment\n" +
                "• 自定义 RadioButton 样式\n" +
                "• 流畅的切换动画"
        );

        return view;
    }
}