package com.example.fragmentexperiment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {

    private static final String KEY_EDIT_TEXT = "edit_text_content";

    private EditText etContent;
    private TextView tvSavedContent;

    public FragmentB() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        etContent = view.findViewById(R.id.et_content);
        tvSavedContent = view.findViewById(R.id.tv_saved_content);

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_EDIT_TEXT);
            tvSavedContent.setText("保存的内容: " + savedText);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("FragmentB", "onSaveInstanceState被调用 - 在onStop之前，onPause之后");

        String content = etContent.getText().toString();
        outState.putString(KEY_EDIT_TEXT, content);
    }
}