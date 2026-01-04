package com.example.fragmentexperiment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    private static final String ARG_USERNAME = "username";
    private static final String ARG_AGE = "age";

    private TextView tvData;
    private EditText etResponse;
    private Button btnSendToActivity;

    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onDataFromFragment(String data);
    }

    public FragmentA() {
        // Required empty public constructor
    }

    public static FragmentA newInstance(String username, int age) {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putInt(ARG_AGE, age);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        tvData = view.findViewById(R.id.tv_data);
        etResponse = view.findViewById(R.id.et_response);
        btnSendToActivity = view.findViewById(R.id.btn_send_to_activity);

        if (getArguments() != null) {
            String username = getArguments().getString(ARG_USERNAME);
            int age = getArguments().getInt(ARG_AGE);
            tvData.setText("用户名: " + username + "\n年龄: " + age);
        }

        btnSendToActivity.setOnClickListener(v -> {
            String response = etResponse.getText().toString();
            if (listener != null) {
                listener.onDataFromFragment(response);
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