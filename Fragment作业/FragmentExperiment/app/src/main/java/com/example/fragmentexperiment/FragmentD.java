package com.example.fragmentexperiment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentD extends Fragment {

    private TextView tvReceivedMessage;

    public FragmentD() {
        // Required empty public constructor
    }

    public void updateMessage(String message) {
        if (tvReceivedMessage != null) {
            tvReceivedMessage.setText("来自FragmentC的消息: " + message);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        tvReceivedMessage = view.findViewById(R.id.tv_received_message);
        return view;
    }
}