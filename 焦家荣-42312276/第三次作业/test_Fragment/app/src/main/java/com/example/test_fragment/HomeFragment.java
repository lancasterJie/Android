package com.example.test_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_INITIAL_DATA = "initial_data";

    private TextView textDataFromActivity;
    private Button buttonSendToActivity;

    public interface OnDataTransferListener {
        void onDataReceivedFromFragment(String data);
    }

    private OnDataTransferListener dataListener;

    public static HomeFragment newInstance(String initialData) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INITIAL_DATA, initialData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textDataFromActivity = view.findViewById(R.id.text_data_from_activity);
        buttonSendToActivity = view.findViewById(R.id.button_send_to_activity);

        // 场景B: 接收从Activity传递的初始数据
        if (getArguments() != null) {
            String initialData = getArguments().getString(ARG_INITIAL_DATA);
            textDataFromActivity.setText("从Activity接收: " + initialData);
        }

        // 场景B: Fragment向Activity返回数据
        buttonSendToActivity.setOnClickListener(v -> {
            if (dataListener != null) {
                dataListener.onDataReceivedFromFragment("来自HomeFragment的数据 - " + System.currentTimeMillis());
            }
        });

        return view;
    }

    // 设置数据监听器
    public void setOnDataTransferListener(OnDataTransferListener listener) {
        this.dataListener = listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof OnDataTransferListener) {
            setOnDataTransferListener((OnDataTransferListener) getActivity());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataListener = null;
    }
}