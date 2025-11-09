package com.example.activityradiogroup;

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

import com.example.activityradiogroup.R;

public class FragmentA extends Fragment {

    private static final String ARG_USERNAME = "username";
    private String initialData;
    private FragmentAListener listener;

    private TextView tvDataFromActivity;
    private EditText etDataToActivity;
    private Button btnSendToActivity;

    public interface FragmentAListener {
        void onDataSent(String data);
    }

    public static FragmentA newInstance(String username) {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentAListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        tvDataFromActivity = view.findViewById(R.id.tv_data_from_activity);
        etDataToActivity = view.findViewById(R.id.et_data_to_activity);
        btnSendToActivity = view.findViewById(R.id.btn_send_to_activity);

        // 接收来自Activity的数据
        if (getArguments() != null) {
            initialData = getArguments().getString(ARG_USERNAME);
            tvDataFromActivity.setText("来自Activity的数据: " + initialData);
        }

        btnSendToActivity.setOnClickListener(v -> {
            String data = etDataToActivity.getText().toString();
            if (listener != null) {
                listener.onDataSent(data);
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