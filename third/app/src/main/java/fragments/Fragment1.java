package com.example.fragmentapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmentapp.DetailActivity;
import com.example.fragmentapp.R;
import com.example.fragmentapp.models.User;

public class Fragment1 extends Fragment {

    private EditText etDataToSave;
    private TextView tvSavedContent;

    public Fragment1() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        etDataToSave = view.findViewById(R.id.et_data_to_save);
        tvSavedContent = view.findViewById(R.id.tv_saved_content);
        Button btnGoToDetail = view.findViewById(R.id.btn_go_to_detail);

        btnGoToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                User user = new User("张三", 20, true);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (etDataToSave != null) {
            String content = etDataToSave.getText().toString();
            outState.putString("saved_content", content);
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String savedContent = savedInstanceState.getString("saved_content");
            if (savedContent != null && tvSavedContent != null) {
                tvSavedContent.setText("保存的内容: " + savedContent);
            }
        }
    }
}