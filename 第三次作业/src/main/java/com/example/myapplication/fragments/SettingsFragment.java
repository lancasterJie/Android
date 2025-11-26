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

public class SettingsFragment extends Fragment {
    private boolean canReply=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        Button sendBackButton=view.findViewById(R.id.bntreply);
        TextView recievedmessage=view.findViewById(R.id.recievedmessage);
//        sendBackButton.setEnabled(false);
        getParentFragmentManager().setFragmentResultListener(
                "AtoS",getViewLifecycleOwner(),((requestKey, result) -> {
                    String recieve=result.getString("data");
                    recievedmessage.setText("收到消息："+recieve);
                    canReply=true;
//                    sendBackButton.setEnabled(true);
                })
        );
        sendBackButton.setOnClickListener(v->{
            if(!canReply){
                Toast.makeText(getContext(),"请先等待接收AboutFragment的消息",Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle=new Bundle();
            bundle.putString("replymessage","你好AboutFragment，我是SettingFragment，我已经接收到了你的信息");
            getParentFragmentManager().setFragmentResult("StoA",bundle);
            Toast.makeText(getContext(),"已回复！",Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}