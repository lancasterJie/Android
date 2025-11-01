package com.dyk.assignments.assignment_4.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dyk.assignments.R;


public class HomeFragment extends Fragment {

    public interface FragmentCallback {
        void onFragmentResult(String result);
    }

    private FragmentCallback callback;
    private TextView tv_result;
    private Button btn_return;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle args = getArguments();
        tv_result = view.findViewById(R.id.tv_result);
        btn_return = view.findViewById(R.id.btn_return);
        if(args!=null){
            // 显示从宿主Activity接收的数据
            displayReceivedData();

            btn_return.setOnClickListener(v -> {
                processData();
            });
        }else{
            tv_result.setVisibility(View.GONE);
            btn_return.setVisibility(View.GONE);
        }

        return view;
    }

    private void displayReceivedData() {
        // 获取宿主Activity传递的数据
        Bundle args = getArguments();
        if (args != null) {
            String user_name = args.getString("user_name");
            String age = args.getString("age");
            boolean is_stu = args.getBoolean("is_stu");

            String show_text = "根据你填写的信息可知：\n" + "你在本应用上的用户名为：" + user_name + "\n 你的年龄为：" + age + "\n 你是否为一名学生：" + (is_stu?"是":"否");
            tv_result.setText("从Activity接收: " + show_text);
        }
    }

    private void processData() {
        Bundle args = getArguments();

        String user_name = args.getString("user_name");
        String age = args.getString("age");
        boolean is_stu = args.getBoolean("is_stu");

        // 处理数据（这里简单拼接）
        String processedResult = "根据你填写的信息可知：\n" + "你在本应用上的用户名为：" + user_name + "\n 你的年龄为：" + age + "\n 你是否为一名学生：" + (is_stu?"是":"否");

        // 返回处理结果给宿主Activity
        if (callback != null) {
            callback.onFragmentResult(processedResult);
        }
    }

    public void setFragmentCallback(FragmentCallback callback) {
        this.callback = callback;
    }
}