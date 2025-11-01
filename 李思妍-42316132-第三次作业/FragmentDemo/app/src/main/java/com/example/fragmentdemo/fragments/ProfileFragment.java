package com.example.fragmentdemo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragmentdemo.R;

public class ProfileFragment extends Fragment {

    private TextView tvReceivedData;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvReceivedData = view.findViewById(R.id.tvReceivedData);

        return view;
    }

    // 场景C: 从其他Fragment接收数据
    public void receiveDataFromOtherFragment(String data) {
        if (tvReceivedData != null) {
            tvReceivedData.setText("从其他Fragment接收的数据: " + data + "\n时间: " + System.currentTimeMillis());
        }
    }
}