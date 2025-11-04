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
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    private TextView tv;
    private Button b;
    private int clickCount = 0;
    public HomeFragment() {
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv = view.findViewById(R.id.textView);
        b = view.findViewById(R.id.button);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                tv.setText("首页按钮被点击了 " + clickCount + " 次");
                Toast.makeText(getActivity(), "这是首页!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
