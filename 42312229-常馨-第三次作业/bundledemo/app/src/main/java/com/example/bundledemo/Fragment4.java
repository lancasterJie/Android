package com.example.bundledemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {

    private TextView tvReceivedData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        tvReceivedData = view.findViewById(R.id.tv_fragment4_received);

        // 接收从其他Fragment传递过来的数据
        if (getArguments() != null) {
            String fragmentData = getArguments().getString("fragment_data", "未收到数据");
            tvReceivedData.setText("来自其他Fragment的数据: " + fragmentData);
        }

        return view;
    }

    // 更新显示数据的方法
    public void updateReceivedData(String data) {
        if (tvReceivedData != null) {
            tvReceivedData.setText("来自其他Fragment的数据: " + data);
        }
    }
}