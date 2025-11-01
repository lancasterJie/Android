package com.dyk.assignments.assignment_4.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyk.assignments.R;

public class PersonFragment extends Fragment {

    private TextView tv_res;
    private String user_interest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        tv_res = view.findViewById(R.id.tv_res);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(user_interest!=null){
            receiveData(user_interest);
            user_interest=null;
        }
    }

    public void receiveData(String userInterest) {
        // 检查视图是否已经创建
        if (tv_res != null) {
            // 视图已创建，直接更新 UI
            tv_res.setText("根据你填写的搜索记录，可知你的兴趣/爱好为：" + userInterest);
            tv_res.setVisibility(View.VISIBLE);
        } else {
            this.user_interest = userInterest;
        }
    }
}