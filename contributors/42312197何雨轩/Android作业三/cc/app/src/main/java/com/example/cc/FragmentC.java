package com.example.cc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class FragmentC extends Fragment {

    private EditText etInputC;
    private TextView tvReturnFromD;
    private FragmentD fragmentD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);

        etInputC = view.findViewById(R.id.etInputC);
        tvReturnFromD = view.findViewById(R.id.tvReturnFromD);

        Button btnSendToD = view.findViewById(R.id.btnSendToD);
        btnSendToD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToFragmentD();
            }
        });

        return view;
    }

    public void setTargetFragmentD(FragmentD fragmentD) {
        this.fragmentD = fragmentD;
        if (fragmentD != null) {
            fragmentD.setReturnListener(new FragmentD.OnDataReturnListener() {
                @Override
                public void onDataReturn(String data) {
                    displayReturnedData(data);
                }
            });
        }
    }

    private void sendDataToFragmentD() {
        String input = etInputC.getText().toString();
        if (!input.isEmpty() && fragmentD != null) {
            fragmentD.receiveDataFromFragmentC(input);
        }
    }

    public void displayReturnedData(String data) {
        if (tvReturnFromD != null) {
            tvReturnFromD.setText("从FragmentD返回: " + data);
        }
    }
}