package com.dyk.assignments.assignment_4.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dyk.assignments.R;
import com.dyk.assignments.assignment_4.FirstMainActivity;

public class ExportFragment extends Fragment {
    private EditText et_user_interest;
    private Button btn_send;
    private final static String TAG = "DYK";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);

        et_user_interest = view.findViewById(R.id.et_user_interest);
        btn_send = view.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(v->{
            String user_interest = et_user_interest.getText().toString();
            ((FirstMainActivity) requireActivity()).onDataFromExport(user_interest);
            Log.d(TAG, "onCreateView: " + user_interest);
        });

        return view;
    }
}