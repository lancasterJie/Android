package com.example.activityradiogroup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentD extends Fragment {

    private EditText etInput;
    private TextView tvSavedText;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        etInput = view.findViewById(R.id.et_input);
        tvSavedText = view.findViewById(R.id.tv_saved_text);

        return view;
    }

    public String getInputText() {
        return etInput != null ? etInput.getText().toString() : "";
    }

    public void displaySavedText(String text) {
        if (tvSavedText != null) {
            tvSavedText.setText("保存的内容: " + text);
        }
    }
}