package com.example.experiment03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_second, container, false);
        initViews(view);

        return view;
    }
    private void initViews(View view) {
        userName = view.findViewById(R.id.userName);
        age = view.findViewById(R.id.age);
        isStudent = view.findViewById(R.id.isStudent);
    }
    public void setMessage(Bundle bundle) {
        userName.setText(bundle.getString("user_name"));

        age.setText(Integer.toString(bundle.getInt("age")));
        if (bundle.getBoolean("is_student")) {
            isStudent.setText("是");
        }
        else {
            isStudent.setText("否");
        }
    }
    private TextView userName, age, isStudent;
}