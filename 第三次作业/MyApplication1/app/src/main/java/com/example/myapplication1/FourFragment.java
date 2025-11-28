package com.example.myapplication1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FourFragment extends Fragment {
    private String message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
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
            message = bundle.getString("msg");
        }
        if (mResultListener != null){
            mResultListener.onResult(isSuccess, message);
        }
    }

}