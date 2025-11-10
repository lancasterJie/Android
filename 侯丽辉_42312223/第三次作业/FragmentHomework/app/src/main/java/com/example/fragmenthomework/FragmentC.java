package com.example.fragmenthomework;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentC extends Fragment {
    private static final String TAG = "FragmentC";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_c, container, false);
        EditText et = v.findViewById(R.id.et_msg);
        Button btn = v.findViewById(R.id.btn_to_d);
        btn.setOnClickListener(view -> {
            String msg = et.getText().toString();
            Bundle b = new Bundle();
            b.putString("msg", msg);
            // 先把信息交给 Activity（通过 FragmentResult 给 Activity）
            getParentFragmentManager().setFragmentResult("C_to_activity", b);
            Log.i(TAG, "FragmentC 发送: " + msg);
        });
        return v;
    }
}
