package com.example.activityradiogroup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentC extends Fragment {

    private TextView tvReceivedMessage;

    public void updateMessage(String message) {
        if (tvReceivedMessage != null) {
            tvReceivedMessage.setText("来自FragmentB的消息: " + message);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);

        tvReceivedMessage = view.findViewById(R.id.et_message);

        return view;
    }
}