package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        TextView tvDataFromA = view.findViewById(R.id.tvDataFromA);

        // 获取从Fragment A通过Activity中转传递的数据
        Bundle args = getArguments();
        if (args != null) {
            String dataFromA = args.getString("data_from_a");
            tvDataFromA.setText("从Fragment A接收的数据: " + dataFromA);
        }

        return view;
    }
}
