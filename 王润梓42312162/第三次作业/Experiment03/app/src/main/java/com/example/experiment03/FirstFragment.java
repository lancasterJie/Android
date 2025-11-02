package com.example.experiment03;

import static androidx.compose.ui.platform.SemanticsUtils_androidKt.findById;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


public class FirstFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initViews(view);
        isStudent.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.yes) {
                flag = true;
            } else {
                flag =false;
            }
        });
        isStudent.check(R.id.yes);
        return view;
    }
    private void initViews(View view) {
        userName = view.findViewById(R.id.userName);
        age = view.findViewById(R.id.age);
        isStudent = view.findViewById(R.id.isStudent);
    }
    public Bundle getMessage() {
        Bundle bundle = new Bundle();
        bundle.putString("user_name", userName.getText().toString());
        String ageStr = age.getText().toString();
        if (ageStr.chars().allMatch(Character::isDigit)) {
            bundle.putInt("age", Integer.parseInt(ageStr));
        }
        else {
            bundle.putInt("age", 0);
        }
        bundle.putBoolean("is_student", flag);
        return bundle;
    }
    public void setMassage(Bundle bundle) {
        userName.setText(bundle.getString("user_name"));
        age.setText(Integer.toString(bundle.getInt("age")));
        if (bundle.getBoolean("is_student")) {
            isStudent.check(R.id.yes);
        }
        else {
            isStudent.check(R.id.no);
        }
    }
    private EditText userName, age;
    private RadioGroup isStudent;
    private  boolean flag = true;
}