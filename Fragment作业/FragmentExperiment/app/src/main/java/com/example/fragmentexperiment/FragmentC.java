package com.example.fragmentexperiment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class FragmentC extends Fragment {

    public FragmentC() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);

        Button btnSendToD = view.findViewById(R.id.btn_send_to_d);
        EditText etMessage = view.findViewById(R.id.et_message);

        btnSendToD.setOnClickListener(v -> {
            String message = etMessage.getText().toString();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).sendDataToFragmentD(message);
            }
        });

        return view;
    }
}