package com.example.myapplication.fragments;

import static android.view.View.inflate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        TextView messageText=view.findViewById(R.id.messageText);
        Button bntSentBack=view.findViewById(R.id.bntSentBack);
        //接收来自Activity的数据
        Bundle args=getArguments();
        if(args!=null){
            String message=args.getString("message");
            messageText.setText(message);
        }
        //当点击按钮时，把编辑结果返回给Acticity
        bntSentBack.setOnClickListener(v->{
            String replymessage = "Hi,MainActivity!我收到了你的信息！";
            Bundle result = new Bundle();
            result.putString("reply",replymessage);
            getParentFragmentManager().setFragmentResult("profilekey",result);
        });
        return view;
    }

}