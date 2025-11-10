package com.example.fragmenthomework;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {
    private static final String TAG = "FragmentA";
    private static final String KEY_TEXT = "fragment_a_text";
    private EditText etInput;
    private TextView tvSaved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        etInput = v.findViewById(R.id.et_input);
        tvSaved = v.findViewById(R.id.tv_saved);
        Button btn = v.findViewById(R.id.btn_show_saved);

        // 如果 activity/fragment 恢复时提供了 savedInstanceState，这里可以取出
        if (savedInstanceState != null) {
            String saved = savedInstanceState.getString(KEY_TEXT);
            if (saved != null) {
                tvSaved.setText("恢复自 onSaveInstanceState: " + saved);
            }
        }

        btn.setOnClickListener(view -> {
            String s = etInput.getText().toString();
            tvSaved.setText("按钮触发显示: " + s);
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 记录日志，观察 onSaveInstanceState 在生命周期的哪个阶段被调用
        Log.i(TAG, "FragmentA.onSaveInstanceState called");
        if (etInput != null) {
            outState.putString(KEY_TEXT, etInput.getText().toString());
        }
    }
}
