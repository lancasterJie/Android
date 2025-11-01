package com.example.fragmentdemo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragmentdemo.MainActivity;
import com.example.fragmentdemo.R;

public class SettingsFragment extends Fragment {

    private Button btnSendToOtherFragment;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btnSendToOtherFragment = view.findViewById(R.id.btnSendToOtherFragment);

        btnSendToOtherFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 场景C: Fragment → Fragment 数据传输
                if (getActivity() instanceof MainActivity) {
                    String data = "来自SettingsFragment的数据 - " + System.currentTimeMillis();
                    ((MainActivity) getActivity()).transferDataBetweenFragments(data, "ProfileFragment");
                }
            }
        });

        return view;
    }
}