package com.example.three;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

public class ProfileFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container, false);
		EditText etUser = view.findViewById(R.id.et_username);
		EditText etAge = view.findViewById(R.id.et_age);
		EditText etStudent = view.findViewById(R.id.et_student);
		TextView tvFromA = view.findViewById(R.id.tv_from_a);

		// 监听 Activity 转发给 B（本 ProfileFragment） 的数据（场景C）
		getParentFragmentManager().setFragmentResultListener("from_activity_to_b", this, new FragmentResultListener() {
			@Override
			public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
				String msg = result.getString("data", "");
				tvFromA.setText("来自 Discover->Activity->Profile: " + msg);
			}
		});

		Button btnLaunch = view.findViewById(R.id.btn_launch_detail);
		btnLaunch.setOnClickListener(v -> {
			// 场景A：Activity→Activity 由 Fragment 触发在 Activity 里更常见，但这里直接起一个 Activity 也可
			Intent intent = new Intent(requireContext(), DetailActivity.class);
			intent.putExtra("username", etUser.getText().toString());
			try {
				intent.putExtra("age", Integer.parseInt(etAge.getText().toString()));
			} catch (Exception e) {
				intent.putExtra("age", 0);
			}
			boolean isStudent = "true".equalsIgnoreCase(etStudent.getText().toString()) || "是".equals(etStudent.getText().toString());
			intent.putExtra("isStudent", isStudent);
			startActivity(intent);
		});
		return view;
	}
}


