package com.example.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class FragmentSetting extends Fragment {
    private TextView tvReceivedData;

    public static FragmentSetting newInstance(String data) {
        FragmentSetting fragment = new FragmentSetting();
        Bundle bundle = new Bundle();
        bundle.putString("transfer_data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvReceivedData = view.findViewById(R.id.tv_setting_receive);

        // 接收传递的数据（包括旋转后恢复的内容）
        Bundle bundle = getArguments();
        if (bundle != null) {
            String data = bundle.getString("transfer_data", "暂无数据");
            tvReceivedData.setText("收到消息页数据：" + data);
        } else {
            tvReceivedData.setText("暂无数据（未收到传递）");
        }

        return view;
    }
}