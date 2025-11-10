package com.example.fragmentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmentapp.R;

public class Fragment2 extends Fragment {

    private static final String ARG_INITIAL_DATA = "initial_data";
    private TextView tvReceivedData;

    public interface OnDataReturnListener {
        void onDataReturn(String data);
    }

    private OnDataReturnListener dataReturnListener;

    public Fragment2() {}

    public static Fragment2 newInstance(String initialData) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_INITIAL_DATA, initialData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        tvReceivedData = view.findViewById(R.id.tv_received_data);
        Button btnSendBack = view.findViewById(R.id.btn_send_back);

        if (getArguments() != null) {
            String initialData = getArguments().getString(ARG_INITIAL_DATA);
            tvReceivedData.setText("从Activity接收的数据: " + initialData);
        }

        btnSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataReturnListener != null) {
                    dataReturnListener.onDataReturn("这是从Fragment2返回的数据");
                }
            }
        });

        return view;
    }

    public void setOnDataReturnListener(OnDataReturnListener listener) {
        this.dataReturnListener = listener;
    }
}