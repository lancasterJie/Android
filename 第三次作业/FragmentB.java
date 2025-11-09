package com.example.activityradiogroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {

    private EditText etMessage;
    private Button btnSendToFragmentC;

    public interface FragmentBListener {
        void sendMessageToFragmentC(String message);
    }

    private FragmentBListener listener;

    public void setFragmentBListener(FragmentBListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        etMessage = view.findViewById(R.id.et_message);
        btnSendToFragmentC = view.findViewById(R.id.btn_send_to_fragment_c);

        btnSendToFragmentC.setOnClickListener(v -> {
            String message = etMessage.getText().toString();
            if (listener != null && !message.isEmpty()) {
                listener.sendMessageToFragmentC(message);
            }
        });

        return view;
    }
}