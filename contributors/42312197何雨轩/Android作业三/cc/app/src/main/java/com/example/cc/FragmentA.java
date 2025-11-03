package com.example.cc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    private EditText etName, etAge, etJob, etFourthInput, etRotationTest;
    private TextView tvSavedContent, tvReturnFromB;
    private Button btnSave;
    private String returnedDataFromB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        initViews(view);
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        etName = view.findViewById(R.id.etName);
        etAge = view.findViewById(R.id.etAge);
        etJob = view.findViewById(R.id.etJob);
        etFourthInput = view.findViewById(R.id.etFourthInput);
        etRotationTest = view.findViewById(R.id.etRotationTest);
        tvSavedContent = view.findViewById(R.id.tvSavedContent);
        tvReturnFromB = view.findViewById(R.id.tvReturnFromB);
        btnSave = view.findViewById(R.id.btnSave);
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 场景A: 跳转到DetailActivity
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("age", etAge.getText().toString());
                intent.putExtra("job", etJob.getText().toString());
                startActivity(intent);
            }
        });
    }

    // 获取第四行输入内容（用于场景B）
    public String getFourthInput() {
        return etFourthInput != null ? etFourthInput.getText().toString() : "";
    }

    // 获取屏幕旋转测试内容
    public String getRotationTestContent() {
        return etRotationTest != null ? etRotationTest.getText().toString() : "";
    }

    // 显示保存的内容（屏幕旋转后）
    public void displaySavedContent(String content) {
        if (tvSavedContent != null) {
            tvSavedContent.setText("保存的内容: " + content);
        }
    }

    // 显示从FragmentB返回的数据
    public void displayReturnedData(String data) {
        if (tvReturnFromB != null) {
            tvReturnFromB.setText("从FragmentB返回: " + data);
        } else {
            returnedDataFromB = data;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (returnedDataFromB != null) {
            displayReturnedData(returnedDataFromB);
        }
    }
}