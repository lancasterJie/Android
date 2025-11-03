package com.example.fragmentdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private EditText etSettingsInput;
    private TextView tvSavedState;
    private Button btnSendToDataFragment;

    private OnSettingsInteractionListener listener;

    private static final String KEY_SAVED_TEXT = "saved_text";

    public interface OnSettingsInteractionListener {
        void onSendDataToDataFragment(String data);
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initViews(view);
        setupClickListeners();

        // 恢复保存的状态
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_SAVED_TEXT);
            if (savedText != null) {
                tvSavedState.setText("恢复的内容: " + savedText);
            }
        }

        return view;
    }

    private void initViews(View view) {
        etSettingsInput = view.findViewById(R.id.etSettingsInput);
        tvSavedState = view.findViewById(R.id.tvSavedState);
        btnSendToDataFragment = view.findViewById(R.id.btnSendToDataFragment);
    }

    private void setupClickListeners() {
        btnSendToDataFragment.setOnClickListener(v -> {
            String inputText = etSettingsInput.getText().toString().trim();
            if (!inputText.isEmpty() && listener != null) {
                // 场景C: Fragment → Fragment 通过Activity中转
                listener.onSendDataToDataFragment(inputText);
                tvSavedState.setText("已发送数据: " + inputText);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SettingsFragment", "onSaveInstanceState");

        // 保存EditText内容
        String inputText = etSettingsInput.getText().toString();
        if (!inputText.isEmpty()) {
            outState.putString(KEY_SAVED_TEXT, inputText);
        }
    }

    @Override
    public void onAttach(android.content.Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsInteractionListener) {
            listener = (OnSettingsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}