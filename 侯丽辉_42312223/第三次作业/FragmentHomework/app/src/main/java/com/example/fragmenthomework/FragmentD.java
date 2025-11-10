package com.example.fragmenthomework;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentD extends Fragment {
    private static final String TAG = "FragmentD";
    private TextView tvReceived;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_d, container, false);
        tvReceived = v.findViewById(R.id.tv_received);

        // 监听来自 Activity 的转发（fragment result）
        getParentFragmentManager().setFragmentResultListener("activity_to_D", this, (key, bundle) -> {
            String msg = bundle.getString("msg_to_d");
            tvReceived.setText("收到来自 C 的消息: " + msg);
            Log.i(TAG, "收到消息: " + msg);
        });

        return v;
    }
}
