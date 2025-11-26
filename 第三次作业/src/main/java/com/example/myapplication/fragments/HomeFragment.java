package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    private EditText etUsername;
    private EditText etAge ;
    private SwitchCompat switchIsStudent;
    private Button btnAA;
    private static final String TAG ="HomeFragment";
    public  HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        //获取控件引用
        etUsername = view.findViewById(R.id.etUsername);
        etAge = view.findViewById(R.id.etAge);
        switchIsStudent = view.findViewById(R.id.switchIsStudent);
        btnAA = view.findViewById(R.id.btnAA);
        if(savedInstanceState!=null){
            etUsername.setText(savedInstanceState.getString("name",""));
            etAge.setText(savedInstanceState.getString("age",""));
            boolean isStudent=savedInstanceState.getBoolean("isStudent",false);
            switchIsStudent.setChecked(isStudent);

        }        //设置点击按钮事件
        btnAA.setOnClickListener(v->{
            String name = etUsername.getText().toString();
            String age= etAge.getText().toString();
            boolean isStudent = switchIsStudent.isChecked();
            //创建Intent传递数据
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("age",age);
            intent.putExtra("isStudent",isStudent);
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return view;
    }
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState called-保存EditText文本");
        //取得EditText当前内容并保存到bundle
        outState.putString("name",etUsername.getText().toString());
        outState.putString("age",etAge.getText().toString());
        outState.putBoolean("isStudent",switchIsStudent.isChecked());
    }
}