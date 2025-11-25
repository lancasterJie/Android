package com.example.fragmentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fragmentapp.R;

public class Fragment3 extends Fragment {

    private TextView tvFragment3Data;

    public Fragment3() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        tvFragment3Data = view.findViewById(R.id.tv_fragment3_data);
        return view;
    }

    public void receiveDataFromFragment4(String data) {
        if (tvFragment3Data != null) {
            tvFragment3Data.setText("从Fragment4接收的数据: " + data);
        }
    }
}