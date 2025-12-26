package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        EditText etDataFromA = view.findViewById(R.id.etDataFromA);
        Button btnSendToFragmentB = view.findViewById(R.id.btnSendToFragmentB);

        btnSendToFragmentB.setOnClickListener(v -> {
            String data = etDataFromA.getText().toString();
            if (!data.isEmpty()) {
                // 通过Activity中转将数据传递给Fragment B
                if (getActivity() instanceof BundleActivity) {
                    ((BundleActivity) getActivity()).passDataToFragmentB(data);
                }
            }
        });

        return view;
    }
}
