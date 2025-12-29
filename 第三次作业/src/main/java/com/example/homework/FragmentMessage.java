package com.example.homework;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class FragmentMessage extends Fragment {
    private static final String TAG = "FragmentMessage";
    private static final String KEY_INPUT_CONTENT = "permanent_input_content";
    private static final String SP_NAME = "MessageInputSP";
    private EditText etInput;
    private SharedPreferences sp;

    public interface OnMessageTransferListener {
        void onTransferData(String data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 生命周期触发");
        // 初始化永久存储
        sp = requireActivity().getSharedPreferences(SP_NAME, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: 生命周期触发");
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        etInput = view.findViewById(R.id.et_input_data);
        View sendBtn = view.findViewById(R.id.btn_message_send);

        // 1. 恢复永久存储的内容（旋转/重启都能拿到）
        restoreSavedContent();

        // 2. 关键修复：添加输入框实时监听，输入时就保存（不用等点击发送）
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入内容变化时，实时存入 SharedPreferences
                sp.edit().putString(KEY_INPUT_CONTENT, s.toString().trim()).apply();
                Log.d(TAG, "实时保存内容：" + s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // 3. 原有发送按钮逻辑（保持不变）
        sendBtn.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "消息页按钮已点击！", Toast.LENGTH_SHORT).show();
            String newContent = etInput.getText().toString().trim();
            if (newContent.isEmpty()) {
                newContent = "来自消息页的默认通知";
                // 空内容时也更新存储
                sp.edit().putString(KEY_INPUT_CONTENT, newContent).apply();
            }

            if (getActivity() instanceof OnMessageTransferListener) {
                Toast.makeText(getActivity(), "开始传递数据：" + newContent, Toast.LENGTH_SHORT).show();
                ((OnMessageTransferListener) getActivity()).onTransferData(newContent);
            } else {
                Toast.makeText(getActivity(), "传递失败：Activity 未实现接口", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // 单独提取恢复逻辑，确保可靠
    private void restoreSavedContent() {
        String savedContent = sp.getString(KEY_INPUT_CONTENT, "");
        if (!savedContent.isEmpty()) {
            etInput.setText(savedContent);
            etInput.setSelection(savedContent.length()); // 光标定位到末尾
            Log.d(TAG, "恢复内容：" + savedContent);
        }
    }

    // 可选：保留生命周期日志，方便排查
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: 触发（旋转前）");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 触发");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 触发");
    }
}