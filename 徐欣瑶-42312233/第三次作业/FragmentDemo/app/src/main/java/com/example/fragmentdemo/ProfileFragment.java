package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvProfileData;
    private Button btnSendResult;

    private OnProfileInteractionListener listener;

    public interface OnProfileInteractionListener {
        void onProfileResult(String result);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        setupClickListeners();

        // 场景B: Activity → Fragment 接收数据
        receiveDataFromActivity();

        return view;
    }

    private void initViews(View view) {
        tvProfileData = view.findViewById(R.id.tvProfileData);
        btnSendResult = view.findViewById(R.id.btnSendResult);
    }

    private void setupClickListeners() {
        btnSendResult.setOnClickListener(v -> {
            // 场景B: Fragment → Activity 发送处理结果
            if (listener != null) {
                listener.onProfileResult("ProfileFragment处理完成，时间: " + System.currentTimeMillis());
            }
        });
    }

    private void receiveDataFromActivity() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String userName = bundle.getString("user_name", "未知");
            int userAge = bundle.getInt("user_age", 0);
            boolean isStudent = bundle.getBoolean("is_student", false);

            String profileInfo = String.format(
                    "从Activity接收的数据:\n用户名: %s\n年龄: %d\n是否学生: %s",
                    userName, userAge, isStudent ? "是" : "否"
            );
            tvProfileData.setText(profileInfo);
        }
    }

    @Override
    public void onAttach(android.content.Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileInteractionListener) {
            listener = (OnProfileInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProfileInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}