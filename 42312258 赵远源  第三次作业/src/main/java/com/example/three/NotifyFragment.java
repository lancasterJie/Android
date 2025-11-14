package com.example.three;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotifyFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notify, container, false);
		TextView tv = view.findViewById(R.id.tv_notify);
		Switch sw = view.findViewById(R.id.sw_push);
		sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
			tv.setText(isChecked ? "通知已开启" : "通知已关闭");
		});
		return view;
	}
}


