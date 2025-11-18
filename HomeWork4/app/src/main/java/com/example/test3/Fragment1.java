package com.example.test3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    private static final String ARG_USERNAME = "username";
    private String userName;
    private OnFragmentInteractionListener listener;

    public static Fragment1 newInstance(String userName) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USERNAME, "默认用户");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        TextView tvWelcome = view.findViewById(R.id.tv_welcome);
        Button btnSendToActivity = view.findViewById(R.id.btn_send_to_activity);

        tvWelcome.setText("欢迎, " + userName + "! 这是首页");

        btnSendToActivity.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDataReceived("来自Fragment1的数据: 处理完成");
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onDataReceived(String data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}