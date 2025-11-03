package com.example.test_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private TextView textSearch;
    private TextView textReceivedData;
    private EditText editTextSearch;
    private Button buttonSearch;

    // 保存接收到的数据
    private String receivedData = "等待其他Fragment数据...";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        textSearch = view.findViewById(R.id.text_search);
        textReceivedData = view.findViewById(R.id.text_received_data);
        editTextSearch = view.findViewById(R.id.edit_text_search);
        buttonSearch = view.findViewById(R.id.button_search);

        Log.d(TAG, "onCreateView - receivedData: " + receivedData);

        updateUIWithReceivedData();

        buttonSearch.setOnClickListener(v -> {
            String query = editTextSearch.getText().toString();
            textSearch.setText("搜索: " + query);
        });

        return view;
    }

    // 场景C: 接收从其他Fragment通过Activity中转的数据
    public void updateSearchData(String data) {
        Log.d(TAG, "updateSearchData被调用，数据: " + data);

        // 保存数据
        this.receivedData = data;

        updateUIWithReceivedData();
    }
    private void updateUIWithReceivedData() {
        if (textReceivedData != null) {
            if (receivedData.contains("中转数据")) {
                textReceivedData.setText("接收到的数据: " + receivedData);
            } else if (!receivedData.equals("等待其他Fragment数据...")) {
                textReceivedData.setText("接收到的数据: " + receivedData);
            } else {
                textReceivedData.setText(receivedData);
            }
            Log.d(TAG, "UI已更新: " + receivedData);
        } else {
            Log.w(TAG, "textReceivedData为null，无法更新UI");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume - 确保UI更新");
        // 确保在恢复时也更新UI
        updateUIWithReceivedData();
    }
}