package com.example.fragmenttest;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageFragment extends Fragment {

    private EditText editcontent,sendto;
    private Button buttonsend;
    public static final String TAG="annie";
    private static final String KEY_EDIT_CONTENT = "edit_content";
    private static final String KEY_SEND_TO = "send_to";

    public MessageFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        Log.d(TAG,"onCreate");
        editcontent = view.findViewById(R.id.editContent);
        sendto=view.findViewById(R.id.sendTo);
        buttonsend = view.findViewById(R.id.buttonSend);

        if(savedInstanceState!=null){
            String savedText1= savedInstanceState.getString(KEY_EDIT_CONTENT);
            String savedText2= savedInstanceState.getString(KEY_SEND_TO);
            if(savedText1!=null){
                editcontent.setText(savedText1);
            }
            if(savedText2!=null){
                sendto.setText(savedText2);
            }
        }

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editcontent.getText().toString();
                String name = sendto.getText().toString();
                if (!name.isEmpty()&& !content.isEmpty()) {
                    Toast.makeText(getActivity(),
                            "信息发送成功!\n发送给: " + name + "\n内容: " + content,
                            Toast.LENGTH_LONG).show();
                } else if(name.isEmpty()) {
                    Toast.makeText(getActivity(), "收信人不能为空", Toast.LENGTH_SHORT).show();
                }else if(content.isEmpty()) {
                    Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_EDIT_CONTENT,editcontent.getText().toString());
        outState.putString(KEY_SEND_TO,sendto.getText().toString());
        Log.d(TAG,"onSaveInstanceState");
    }
}