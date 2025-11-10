package com.example.fragmenthomework;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {
    private static final String TAG = "FragmentB";
    private static final String ARG_INIT = "arg_init";
    private String initData;
    private FragmentBListener listener;

    public interface FragmentBListener {
        void onFragmentBAction(String processed);
    }

    public static FragmentB newInstance(String init) {
        FragmentB f = new FragmentB();
        Bundle args = new Bundle();
        args.putString(ARG_INIT, init);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBListener) {
            listener = (FragmentBListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container, false);
        TextView tvInit = v.findViewById(R.id.tv_init);
        Button btnSend = v.findViewById(R.id.btn_send_result);

        if (getArguments() != null) {
            initData = getArguments().getString(ARG_INIT);
            tvInit.setText("收到 Activity 初始数据: " + initData);
        }

        btnSend.setOnClickListener(view -> {
            String processed = "已处理: " + (initData == null ? "null" : initData.toUpperCase());
            // 方法1：通过 FragmentResult 返回（Activity 可以 setFragmentResultListener）
            Bundle result = new Bundle();
            result.putString("result", processed);
            getParentFragmentManager().setFragmentResult("fromFragmentB", result);

            // 方法2：接口回调
            if (listener != null) {
                listener.onFragmentBAction(processed);
            }

            Log.i(TAG, "向 Activity 返回: " + processed);
        });

        return v;
    }
}
