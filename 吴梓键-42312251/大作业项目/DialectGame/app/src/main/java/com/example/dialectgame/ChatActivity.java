package com.example.dialectgame;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.adapter.MessageAdapter;
import com.example.dialectgame.model.Message;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.RecognizerListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText inputEditText;
    private ImageButton sendBtn, voiceBtn, backBtn;
    private LinearLayout inputContainer;
    private List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int pendingMsgPos = -1;

    // 讯飞语音识别相关
    private SpeechRecognizer mIat;
    private boolean isListening = false; // 录音状态标记
    private static final int PERMISSION_RECORD_AUDIO = 100;
    private StringBuilder voiceResultBuilder = new StringBuilder(); // 用于累积识别结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化UI组件
        initView();
        // 初始化讯飞语音识别
        initSpeechRecognizer();
        // 键盘监听（输入框随键盘上升）
        setupKeyboardListener();
    }

    /**
     * 初始化UI组件和事件绑定
     */
    private void initView() {
        recyclerView = findViewById(R.id.chat_recycler_view);
        inputEditText = findViewById(R.id.chat_input_edittext);
        sendBtn = findViewById(R.id.chat_send_button);
        voiceBtn = findViewById(R.id.chat_voice_button);
        backBtn = findViewById(R.id.back_button);
        inputContainer = findViewById(R.id.input_container);

        // 初始化聊天列表
        adapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 发送按钮点击事件
        sendBtn.setOnClickListener(v -> sendMessage());
        // 返回按钮点击事件
        backBtn.setOnClickListener(v -> finish());
        // 语音按钮触摸事件（核心：长按录音，松手识别）
        voiceBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 手指按下：开始录音
                    startVoiceRecognition();
                    return true;
                case MotionEvent.ACTION_UP:
                    // 手指松开：停止录音
                    stopVoiceRecognition();
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    // 事件取消（如手指移出按钮区域）：停止录音
                    stopVoiceRecognition();
                    return true;
            }
            return false;
        });
    }

    /**
     * 初始化讯飞语音识别（普通话）
     */
    private void initSpeechRecognizer() {
        mIat = SpeechRecognizer.createRecognizer(this, null);

        // 配置语音识别参数（普通话）
        mIat.setParameter("domain", "iat"); // 交互听写
        mIat.setParameter("language", "zh_cn"); // 中文
        mIat.setParameter("accent", "cn_cantonese"); // 普通话
        mIat.setParameter("result_type", "json"); // 结果格式JSON
        mIat.setParameter("speech_timeout", "5000"); // 录音超时5秒
        mIat.setParameter("vad_bos", "3000"); // 开始说话检测（3秒无声音则停止）
        mIat.setParameter("vad_eos", "1000"); // 结束说话检测（1秒无声音则停止）
    }

    /**
     * 开始语音识别（长按触发）
     */
    private void startVoiceRecognition() {
        // 检查录音权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
            return;
        }

        // 避免重复录音
        if (!isListening) {
            // 重置结果缓存
            voiceResultBuilder.setLength(0);

            // 开始录音并设置识别监听器（核心修正：通过startListening传入监听器）
            mIat.startListening(new RecognizerListener() {
                @Override
                public void onVolumeChanged(int volume, byte[] data) {
                    // 音量变化回调（可用于显示音量动画）
                }

                @Override
                public void onBeginOfSpeech() {
                    // 开始说话回调
                }

                @Override
                public void onEndOfSpeech() {
                    // 结束说话回调
                }

                @Override
                public void onResult(RecognizerResult results, boolean isLast) {
                    // 累积所有识别结果（包括中间结果）
                    String recognizedText = parseIatResult(results.getResultString());
                    voiceResultBuilder.append(recognizedText);

                    // 最后一次结果时，显示到输入框
                    if (isLast) {
                        runOnUiThread(() -> {
                            String finalText = voiceResultBuilder.toString().trim();
                            inputEditText.setText(finalText);
                            inputEditText.setSelection(finalText.length());
                        });
                    }
                }

                @Override
                public void onError(SpeechError error) {
                    // 识别失败提示
                    runOnUiThread(() -> {
                        inputEditText.setHint("语音识别失败：" + error.getPlainDescription(true));
                        // 恢复按钮状态
                        voiceBtn.setImageResource(android.R.drawable.ic_btn_speak_now);
                        isListening = false;
                    });
                }

                @Override
                public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                    // 事件回调（一般无需处理）
                }
            });

            voiceBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            isListening = true;
        }
    }

    /**
     * 停止语音识别（松手/取消触发）
     */
    private void stopVoiceRecognition() {
        if (isListening) {
            // 停止录音（会触发onResult的isLast=true）
            mIat.stopListening();
            // 恢复按钮图标和状态
            voiceBtn.setImageResource(android.R.drawable.ic_btn_speak_now);
            isListening = false;
        }
    }

    /**
     * 解析讯飞语音识别的JSON结果
     */
    private String parseIatResult(String json) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray wsArray = new JSONObject(json).getJSONArray("ws");
            for (int i = 0; i < wsArray.length(); i++) {
                JSONArray cwArray = wsArray.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < cwArray.length(); j++) {
                    sb.append(cwArray.getJSONObject(j).getString("w"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 发送消息（点击发送按钮触发）
     */
    private void sendMessage() {
        String content = inputEditText.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入消息内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 添加用户消息到列表
        addMessage(content, true);
        inputEditText.setText(""); // 清空输入框
        sendBtn.setEnabled(false); // 禁用发送按钮，避免重复发送

        // 调用讯飞星火AI接口获取响应
        SparkApiHelper.sendRequest(content, new SparkApiHelper.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    if (pendingMsgPos != -1) {
                        // 标记AI消息加载完成
                        messages.get(pendingMsgPos).markComplete();
                        adapter.notifyItemChanged(pendingMsgPos);
                    }
                    sendBtn.setEnabled(true);
                    pendingMsgPos = -1;
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    if (pendingMsgPos != -1) {
                        messages.get(pendingMsgPos).markComplete();
                        messages.get(pendingMsgPos).appendContent("\n\n请求失败：" + error);
                        adapter.notifyItemChanged(pendingMsgPos);
                    }
                    sendBtn.setEnabled(true);
                    pendingMsgPos = -1;
                });
            }

            @Override
            public void onPartialResult(String partialText) {
                runOnUiThread(() -> {
                    if (pendingMsgPos == -1) {
                        // 添加AI加载中占位消息
                        pendingMsgPos = addPlaceholderMessage();
                    }
                    // 追加流式返回的内容
                    Message aiMsg = messages.get(pendingMsgPos);
                    aiMsg.appendContent(partialText);
                    adapter.notifyItemChanged(pendingMsgPos);
                    recyclerView.smoothScrollToPosition(pendingMsgPos);
                });
            }
        });
    }

    /**
     * 添加普通消息到聊天列表
     * @param content 消息内容
     * @param isUser 是否是用户消息（true=用户，false=AI）
     */
    private void addMessage(String content, boolean isUser) {
        String time = DateFormat.format("HH:mm", new Date()).toString();
        messages.add(new Message(content, isUser, time));
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.smoothScrollToPosition(messages.size() - 1); // 滚动到最新消息
    }

    /**
     * 添加AI加载中占位消息
     * @return 占位消息在列表中的位置
     */
    private int addPlaceholderMessage() {
        String time = DateFormat.format("HH:mm", new Date()).toString();
        Message placeholder = new Message("", false, time); // 空内容标记为加载中
        messages.add(placeholder);
        int pos = messages.size() - 1;
        adapter.notifyItemInserted(pos);
        recyclerView.smoothScrollToPosition(pos);
        return pos;
    }

    /**
     * 键盘监听：输入框随键盘升降调整位置
     */
    private void setupKeyboardListener() {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = rootView.getRootView().getHeight();
                int keyboardHeight = screenHeight - rect.bottom;
                boolean isKeyboardShow = keyboardHeight > screenHeight / 4;

                // 调整输入区域位置
                if (isKeyboardShow) {
                    inputContainer.setTranslationY(-keyboardHeight);
                } else {
                    inputContainer.setTranslationY(0);
                }
            }
        });
    }

    /**
     * 权限请求回调（录音权限）
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限授予后，若正在长按则自动开始录音
                if (isListening) {
                    startVoiceRecognition();
                }
            } else {
                Toast.makeText(this, "需要录音权限才能使用语音功能", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 页面销毁时释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除所有未执行的回调
        handler.removeCallbacksAndMessages(null);
        // 释放讯飞语音资源
        if (mIat != null) {
            mIat.destroy();
        }
    }
}