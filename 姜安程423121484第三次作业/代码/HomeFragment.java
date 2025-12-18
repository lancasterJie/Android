package com.example.homework3;  // 根据你的包名调整

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private static final String ARG_USERNAME = "username";
    private String username;

    public static HomeFragment newInstance(String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 修复inflate方法调用
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvWelcome = view.findViewById(R.id.tvWelcome);
        Button btnSendToActivity = view.findViewById(R.id.btnSendToActivity);

        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME, "用户");
            tvWelcome.setText("欢迎，" + username + "！");
        }

        // 修复Lambda表达式语法
        btnSendToActivity.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onFragmentResult("来自HomeFragment的处理结果：" + System.currentTimeMillis());
            }
        });

        return view;
    }
}
