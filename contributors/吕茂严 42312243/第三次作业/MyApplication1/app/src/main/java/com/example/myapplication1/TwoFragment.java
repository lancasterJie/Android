package com.example.myapplication1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TwoFragment extends Fragment {
    private String message_1;
    private String message_2;
    private TextView tv;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        tv = view.findViewById(R.id.tv);


        view.findViewById(R.id.btn_submit).setOnClickListener(v -> handleBusiness());

        return view;
    }


    //回调接口，用于向Activitry返回信息
    public interface OnFragmentResultListener{
        void onResult(Boolean isSuccess, String message);
    }

    private OneFragment.OnFragmentResultListener mResultListener;

    public void setResultListener(OneFragment.OnFragmentResultListener listener) {
        this.mResultListener = listener;
    }

    @Override
    public void onDetach(){
        super.onDetach();

        mResultListener = null;
    }


    private void handleBusiness(){
        boolean isSuccess = true;

        Bundle bundle = getArguments();
        if (bundle != null){
            message_1 = bundle.getString("msg");
            message_2 = bundle.getString("fragment");
        }

        if (!TextUtils.isEmpty(message_2)) {
            tv.setText(message_2); // 确保按钮点击后也能显示
        }

        if (mResultListener != null){
            mResultListener.onResult(isSuccess, message_1);
        }
    }


}