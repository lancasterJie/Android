package com.example.fragmenttest;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SelfFragment extends Fragment {
    private TextView tvUsername, tvId, tvPassword;
    private EditText etUsername, etId, etPassword;
    private Button buttonsure;
    public static final String TAG="annie";
    public SelfFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_self, container, false);
        Log.d(TAG,"onCreate");
        tvUsername = view.findViewById(R.id.tvUsername);
        tvId = view.findViewById(R.id.tvId);
        tvPassword = view.findViewById(R.id.tvPassword);
        etUsername = view.findViewById(R.id.etUsername);
        etId = view.findViewById(R.id.etId);
        etPassword = view.findViewById(R.id.etPassword);
        buttonsure = view.findViewById(R.id.buttonSure);
        if(savedInstanceState!=null){
            String savedText1= savedInstanceState.getString("edit_username");
            String savedText2= savedInstanceState.getString("edit_id");
            String savedText3= savedInstanceState.getString("edit_password");
            if(savedText1!=null){
                etUsername.setText(savedText1);
            }
            if(savedText2!=null){
                etId.setText(savedText2);
            }if(savedText3!=null){
                etPassword.setText(savedText2);
            }
        }

        buttonsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUsername.getText().toString();
                String id = etId.getText().toString();
                String password = etPassword.getText().toString();

                if (!name.isEmpty()&& !id.isEmpty()&& !password.isEmpty()) {
                    Toast.makeText(getActivity(), "确认!", Toast.LENGTH_LONG).show();
                } else if(name.isEmpty()) {
                    Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if(id.isEmpty()) {
                    Toast.makeText(getActivity(), "ID号不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("edit_username",etUsername.getText().toString());
        outState.putString("edit_id",etId.getText().toString());
        outState.putString("edit_password",etPassword.getText().toString());
        Log.d(TAG,"onSaveInstanceState");
    }
}