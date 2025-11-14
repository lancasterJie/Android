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

public class DiscoverFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discover, container, false);
		TextView tv = view.findViewById(R.id.tv_discover);
		String init = getArguments() != null ? getArguments().getString("init_msg", "") : "";
		tv.setText("发现页，初始数据：" + init);

		// 向 Activity 返回处理结果（场景B）
		Button btnReturn = view.findViewById(R.id.btn_return_activity);
		btnReturn.setOnClickListener(v -> {
			Bundle result = new Bundle();
			result.putString("result", "发现页处理完成：" + System.currentTimeMillis());
			requireActivity().getSupportFragmentManager().setFragmentResult("result_from_fragment", result);
		});

		// 通过 Activity 中转向 ProfileFragment 发送数据（场景C）
		Button btnToB = view.findViewById(R.id.btn_to_profile);
		btnToB.setOnClickListener(v -> {
			Bundle data = new Bundle();
			data.putString("data", "Hello Profile, 来自 Discover 的问候");
			requireActivity().getSupportFragmentManager().setFragmentResult("to_fragment_b", data);
		});
		return view;
	}
}


