package com.example.cc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Locale;

public class FragmentD extends Fragment {

    private TextView tvReceivedFromC;
    private OnDataReturnListener returnListener;

    public interface OnDataReturnListener {
        void onDataReturn(String data);
    }

    public void setReturnListener(OnDataReturnListener listener) {
        this.returnListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        tvReceivedFromC = view.findViewById(R.id.tvReceivedFromC);
        return view;
    }

    public void receiveDataFromFragmentC(String data) {
        if (tvReceivedFromC != null) {
            tvReceivedFromC.setText("从FragmentC接收: " + data);

            // 反转字符串并返回给FragmentC
            String reversed = new StringBuilder(data).reverse().toString();

            if (returnListener != null) {
                returnListener.onDataReturn(reversed);
            }
        }
    }
}