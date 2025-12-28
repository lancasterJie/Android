package com.example.bundledemo;

import android.content.Context;
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

public class Fragment2 extends Fragment {

    private EditText etInput;
    private TextView tvInitialData;
    private Button btnSendToActivity;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onDataFromFragment(String data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        etInput = view.findViewById(R.id.et_fragment2_input);
        tvInitialData = view.findViewById(R.id.tv_fragment2_initial);
        btnSendToActivity = view.findViewById(R.id.btn_fragment2_send);

        // 接收从Activity传递过来的初始数据
        if (getArguments() != null) {
            String initialData = getArguments().getString("initial_data", "未收到初始数据");
            tvInitialData.setText("来自Activity的初始数据: " + initialData);
        }

        btnSendToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputData = etInput.getText().toString();
                if (listener != null && !inputData.isEmpty()) {
                    listener.onDataFromFragment(inputData);
                    etInput.setText("");
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}