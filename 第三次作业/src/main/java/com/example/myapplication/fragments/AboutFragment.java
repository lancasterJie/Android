package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        Button sendbutton=view.findViewById(R.id.sendmessage);
        sendbutton.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            bundle.putString("data","Hello，SettingFragment！我是AboutFragment！");
            getParentFragmentManager().setFragmentResult("AtoS",bundle);
            Toast.makeText(getContext(), "已发送", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        TextView replymessage=view.findViewById(R.id.replymessage);
        getParentFragmentManager().setFragmentResultListener("StoA",getViewLifecycleOwner(),
                (key,result)->{
                      String reply=result.getString("replymessage");
                      replymessage.setText("收到来自SettingFragment的回复："+reply);
                });
    }
}