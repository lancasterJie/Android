package com.example.fragmentdemo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragmentdemo.MainActivity;
import com.example.fragmentdemo.R;

public class HomeFragment extends Fragment {

    private TextView tvData;
    private Button btnSendToActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化视图
        tvData = view.findViewById(R.id.tvData);
        btnSendToActivity = view.findViewById(R.id.btnSendToActivity);

        Log.d("HomeFragment", "onCreateView - TextView初始化: " + (tvData != null));

        // 设置按钮点击事件
        btnSendToActivity.setOnClickListener(v -> {
            // 场景B: Fragment向Activity返回数据
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onFragmentResult("来自HomeFragment的处理结果: " + System.currentTimeMillis());
            }
        });

        return view;
    }

    // 场景B: Activity向Fragment传递数据的方法
    public void receiveDataFromActivity(String data) {
        Log.d("HomeFragment", "接收到数据: " + data);
        Log.d("HomeFragment", "TextView状态: " + (tvData != null));

        if (tvData != null) {
            String displayText = "从Activity接收的数据: " + data + "\n时间: " + System.currentTimeMillis();
            tvData.setText(displayText);
            Log.d("HomeFragment", "TextView已更新: " + displayText);
        } else {
            Log.e("HomeFragment", "TextView为null! 无法显示数据");
        }
    }
}