package com.example.fragmentdemo;

import android.content.Intent;
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

    private static final String ARG_USER_NAME = "user_name";
    private String userName;
    private TextView tvReceivedData;

    // 场景B: Activity向Fragment传递初始数据
    public static HomeFragment newInstance(String userName) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 从Activity接收初始数据
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USER_NAME);
            System.out.println("HomeFragment接收到初始数据: " + userName);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvReceivedData = view.findViewById(R.id.tvReceivedData);
        Button btnGoToDetail = view.findViewById(R.id.btnGoToDetail);
        btnGoToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 场景A: Activity → Activity 数据传输
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("user_name", "张三");
                intent.putExtra("user_age", 25);
                intent.putExtra("is_student", false);
                startActivity(intent);
            }
        });

        return view;
    }
    public void updateReceivedData(String data) {
        if (tvReceivedData != null) {
            tvReceivedData.setText("接收到的数据: " + data);
        }
    }
}