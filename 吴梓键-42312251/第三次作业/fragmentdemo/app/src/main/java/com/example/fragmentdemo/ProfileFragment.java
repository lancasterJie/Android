package com.example.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText etUserName, etAge;
    private CheckBox cbIsStudent;
    private Button btnSendToActivity;

    // 场景B: Fragment向Activity返回数据的接口
    public interface OnDataReturnListener {
        void onDataReturn(String userName, int age, boolean isStudent);
    }

    private OnDataReturnListener dataReturnListener;

    public void setOnDataReturnListener(OnDataReturnListener listener) {
        this.dataReturnListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etUserName = view.findViewById(R.id.etUserName);
        etAge = view.findViewById(R.id.etAge);
        cbIsStudent = view.findViewById(R.id.cbIsStudent);
        btnSendToActivity = view.findViewById(R.id.btnSendToActivity);

        btnSendToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 场景B: Fragment → Activity 数据传输
                String userName = etUserName.getText().toString();
                int age = 0;
                try {
                    age = Integer.parseInt(etAge.getText().toString());
                } catch (NumberFormatException e) {
                    age = 0;
                }
                boolean isStudent = cbIsStudent.isChecked();

                if (dataReturnListener != null) {
                    dataReturnListener.onDataReturn(userName, age, isStudent);
                    System.out.println("ProfileFragment向Activity发送数据: " + userName + ", " + age + ", " + isStudent);
                }
            }
        });

        Button btnGoToDetail = view.findViewById(R.id.btnGoToDetail);
        btnGoToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                int age = 0;
                try {
                    age = Integer.parseInt(etAge.getText().toString());
                } catch (NumberFormatException e) {
                    age = 0;
                }
                boolean isStudent = cbIsStudent.isChecked();

                // 场景A: Activity → Activity 数据传输
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("user_name", userName);
                intent.putExtra("user_age", age);
                intent.putExtra("is_student", isStudent);
                startActivity(intent);
            }
        });

        return view;
    }
}