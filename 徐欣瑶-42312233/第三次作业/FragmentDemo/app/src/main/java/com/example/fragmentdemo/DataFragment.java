package com.example.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class DataFragment extends Fragment {

    private TextView tvReceivedData;
    private Button btnStartDetailActivity;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        initViews(view);
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        tvReceivedData = view.findViewById(R.id.tvReceivedData);
        btnStartDetailActivity = view.findViewById(R.id.btnStartDetailActivity);
    }

    private void setupClickListeners() {
        btnStartDetailActivity.setOnClickListener(v -> {
            // 场景A: Activity → Activity 发送数据
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_name", "李四");
            bundle.putInt("user_age", 22);
            bundle.putBoolean("is_student", true);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    // 场景C: Fragment → Fragment 接收数据
    public void receiveData(String data) {
        if (tvReceivedData != null) {
            tvReceivedData.setText("从Settings Fragment接收的数据: " + data);
        }
    }
}