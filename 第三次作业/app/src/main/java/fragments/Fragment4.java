package com.example.fragmentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.fragmentapp.R;

public class Fragment4 extends Fragment {

    private EditText etDataToSend;

    public interface OnFragmentDataListener {
        void onDataSentToFragment3(String data);
    }

    private OnFragmentDataListener fragmentDataListener;

    public Fragment4() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        etDataToSend = view.findViewById(R.id.et_data_to_send);
        Button btnSendToFragment3 = view.findViewById(R.id.btn_send_to_fragment3);

        btnSendToFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etDataToSend.getText().toString();
                if (fragmentDataListener != null && !data.isEmpty()) {
                    fragmentDataListener.onDataSentToFragment3(data);
                }
            }
        });

        return view;
    }

    public void setOnFragmentDataListener(OnFragmentDataListener listener) {
        this.fragmentDataListener = listener;
    }
}