package com.example.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {
    private TextView tvInitData;

    // 静态方法：接收Activity传递的初始数据
    public static FragmentHome newInstance(String initData) {
        FragmentHome fragment = new FragmentHome();
        Bundle bundle = new Bundle();
        bundle.putString("init_data", initData);
        fragment.setArguments(bundle);
        return fragment;
    }

    // 回调接口：向Activity返回结果
    public interface OnHomeResultListener {
        void onHomeResult(String result);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvInitData = view.findViewById(R.id.tv_home_init);

        // 接收Activity传递的初始数据
        Bundle bundle = getArguments();
        if (bundle != null) {
            String initData = bundle.getString("init_data");
            tvInitData.setText(initData);
        }

        // 按钮点击：向Activity返回结果
        view.findViewById(R.id.btn_home_send).setOnClickListener(v -> {
            if (getActivity() instanceof OnHomeResultListener) {
                ((OnHomeResultListener) getActivity()).onHomeResult("首页处理完成");
            }
        });

        return view;
    }
}