package com.example.three;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

	private int counter = 0;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		TextView tv = view.findViewById(R.id.tv_home);
		Button btn = view.findViewById(R.id.btn_inc);
		btn.setOnClickListener(v -> {
			counter++;
			tv.setText("首页计数：" + counter);
		});
		return view;
	}
}


