package com.example.cc;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Locale;

public class FragmentB extends Fragment {

    private TextView tvReceivedFromActivity, tvReturnToActivity;
    private OnDataReturnListener dataReturnListener;
    private String receivedData;

    public interface OnDataReturnListener {
        void onDataReturn(String data);
    }

    public void setOnDataReturnListener(OnDataReturnListener listener) {
        this.dataReturnListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        tvReceivedFromActivity = view.findViewById(R.id.tvReceivedFromActivity);
        tvReturnToActivity = view.findViewById(R.id.tvReturnToActivity);

        // 设置数据返回监听器
        if (getActivity() instanceof OnDataReturnListener) {
            setOnDataReturnListener((OnDataReturnListener) getActivity());
        }

        // 如果有已接收的数据，显示它
        if (receivedData != null) {
            displayReceivedData(receivedData);
        }

        return view;
    }

    // 从Activity接收数据
    public void receiveDataFromActivity(String data) {
        this.receivedData = data;
        if (tvReceivedFromActivity != null) {
            displayReceivedData(data);
        }
    }

    private void displayReceivedData(String data) {
        if (tvReceivedFromActivity != null) {
            tvReceivedFromActivity.setText("接收: " + data);

            // 反转字符串并返回给Activity
            String reversed = new StringBuilder(data).reverse().toString();
            tvReturnToActivity.setText("返回: " + reversed);

            // 回调给Activity
            if (dataReturnListener != null) {
                dataReturnListener.onDataReturn(reversed);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (receivedData != null) {
            outState.putString("received_data", receivedData);
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            receivedData = savedInstanceState.getString("received_data");
            if (receivedData != null && tvReceivedFromActivity != null) {
                displayReceivedData(receivedData);
            }
        }
    }
}