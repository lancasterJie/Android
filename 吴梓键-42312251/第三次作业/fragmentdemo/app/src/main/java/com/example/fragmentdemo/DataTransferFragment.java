package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DataTransferFragment extends Fragment {

    private static final String ARG_TRANSFER_DATA = "transfer_data";

    private TextView tvReceivedData;
    private Button btnSendToOtherFragment;
    private EditText etDataInput;

    // 场景C: Fragment间数据传输接口
    public interface OnFragmentDataListener {
        void onSendDataToOtherFragment(String data);
    }

    private OnFragmentDataListener dataListener;

    public void setOnFragmentDataListener(OnFragmentDataListener listener) {
        this.dataListener = listener;
    }

    // 场景C: 创建Fragment时传递数据
    public static DataTransferFragment newInstance(String data) {
        DataTransferFragment fragment = new DataTransferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRANSFER_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_transfer, container, false);

        tvReceivedData = view.findViewById(R.id.tvReceivedData);
        btnSendToOtherFragment = view.findViewById(R.id.btnSendToOtherFragment);
        etDataInput = view.findViewById(R.id.etDataInput);

        // 显示从其他Fragment传来的数据
        if (getArguments() != null) {
            String receivedData = getArguments().getString(ARG_TRANSFER_DATA);
            if (receivedData != null) {
                tvReceivedData.setText("接收到的数据: " + receivedData);
                System.out.println("DataTransferFragment接收到数据: " + receivedData);
            }
        }

        btnSendToOtherFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 场景C: Fragment → Fragment 数据传输
                if (dataListener != null) {
                    String data = etDataInput.getText().toString(); // 获取用户输入的数据
                    dataListener.onSendDataToOtherFragment(data);
                    System.out.println("DataTransferFragment发送数据: " + data);
                }
            }
        });

        return view;
    }

    // 更新显示数据的方法
    public void updateReceivedData(String data) {
        if (tvReceivedData != null) {
            tvReceivedData.setText("接收到的数据: " + data);
        }
        // 同时更新arguments以便在重建时保持数据
        if (getArguments() != null) {
            getArguments().putString(ARG_TRANSFER_DATA, data);
        }
    }
}