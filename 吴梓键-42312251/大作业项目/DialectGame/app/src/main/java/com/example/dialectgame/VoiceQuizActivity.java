package com.example.dialectgame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.dialectgame.model.DialectPuzzle;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.RecognizerListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VoiceQuizActivity extends AppCompatActivity {
    private DialectPuzzle currentPuzzle;
    private List<DialectPuzzle> allPuzzles;
    private TextView tvVoicePrompt, tvRecordStatus, tvOriginalText, tvRecognizedText, tvSimilarity;
    private ImageButton btnVoiceRecord;

    // 讯飞语音识别（方言适配）
    private SpeechRecognizer mIat;
    private boolean isRecording = false; // 录音状态标记
    private static final int PERMISSION_RECORD_AUDIO = 101;
    private StringBuilder voiceResultBuilder = new StringBuilder(); // 累积识别结果

    // 颜色常量
    private static final int COLOR_CORRECT = 0xFF00C853; // 绿色
    private static final int COLOR_INCORRECT = 0xFFD32F2F; // 红色
    private static final double PASS_THRESHOLD = 70.0; // 通关阈值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_quiz);

        // 获取谜题数据
        currentPuzzle = (DialectPuzzle) getIntent().getSerializableExtra("PUZZLE_DATA");
        allPuzzles = (List<DialectPuzzle>) getIntent().getSerializableExtra("ALL_PUZZLES");
        if (currentPuzzle == null || allPuzzles == null) {
            finish();
            return;
        }

        // 初始化UI
        initView();
        // 初始化讯飞语音识别（方言配置）
        initDialectSpeechRecognizer();
    }

    private void initView() {
        tvVoicePrompt = findViewById(R.id.tv_voice_prompt);
        tvRecordStatus = findViewById(R.id.tv_record_status);
        btnVoiceRecord = findViewById(R.id.btn_voice_record);
        tvOriginalText = findViewById(R.id.tv_original_text);
        tvRecognizedText = findViewById(R.id.tv_recognized_text);
        tvSimilarity = findViewById(R.id.tv_similarity);

        // 设置提示文本（方言类型+标准答案）
        String promptText = String.format(
                "请用【%s】说出答案：%s",
                currentPuzzle.getDialectType(),
                currentPuzzle.getDialectText()
        );
        tvVoicePrompt.setText(promptText);

        // 显示原始谜题文本
        tvOriginalText.setText("原始内容：" + currentPuzzle.getDialectText());

        // 语音按钮触摸事件
        btnVoiceRecord.setOnTouchListener((v, event) -> {
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
                    // 事件取消：停止录音
                    stopVoiceRecognition();
                    return true;
            }
            return false;
        });
    }

    private void initDialectSpeechRecognizer() {
        mIat = SpeechRecognizer.createRecognizer(this, null);

        // 配置方言识别参数
        mIat.setParameter("domain", "iat");
        mIat.setParameter("language", "zh_cn");
        String accent = currentPuzzle.getXunfeiAccent();
        if (accent == null || accent.isEmpty()) {
            accent = "mandarin"; // 默认为普通话
        }
        mIat.setParameter("accent", "cn_cantonese"); // 使用谜题指定的方言
        mIat.setParameter("result_type", "json");
        mIat.setParameter("speech_timeout", "5000");
        mIat.setParameter("vad_bos", "3000");
        mIat.setParameter("vad_eos", "1000");
    }

    private void startVoiceRecognition() {
        // 检查录音权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
            return;
        }

        // 避免重复录音
        if (!isRecording) {
            // 重置结果缓存
            voiceResultBuilder.setLength(0);
            tvRecordStatus.setText("正在录制...请说出答案");
            tvRecognizedText.setText("识别结果：");
            tvSimilarity.setText("相似度：0%");

            // 开始录音并设置识别监听器
            mIat.startListening(new RecognizerListener() {
                @Override
                public void onVolumeChanged(int volume, byte[] data) {}

                @Override
                public void onBeginOfSpeech() {}

                @Override
                public void onEndOfSpeech() {}

                @Override
                public void onResult(RecognizerResult results, boolean isLast) {
                    String recognizedText = parseIatResult(results.getResultString());
                    voiceResultBuilder.append(recognizedText);

                    if (isLast) {
                        runOnUiThread(() -> {
                            String finalText = voiceResultBuilder.toString().trim();
                            if (!finalText.isEmpty()) {
                                // 显示识别结果并对比
                                showComparisonResult(finalText);
                            } else {
                                tvRecordStatus.setText("未识别到语音，请重试");
                            }
                        });
                    }
                }

                @Override
                public void onError(SpeechError error) {
                    runOnUiThread(() -> {
                        tvRecordStatus.setText("识别失败：" + error.getPlainDescription(true));
                        btnVoiceRecord.setImageResource(android.R.drawable.ic_btn_speak_now);
                        isRecording = false;
                    });
                }

                @Override
                public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {}
            });

            btnVoiceRecord.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            isRecording = true;
        }
    }

    private void stopVoiceRecognition() {
        if (isRecording) {
            mIat.stopListening();
            btnVoiceRecord.setImageResource(android.R.drawable.ic_btn_speak_now);
            tvRecordStatus.setText("点击并长按按钮开始录制方言");
            isRecording = false;
        }
    }

    private String parseIatResult(String json) {
        StringBuilder result = new StringBuilder();
        try {
            JSONArray wsArray = new JSONObject(json).getJSONArray("ws");
            for (int i = 0; i < wsArray.length(); i++) {
                JSONArray cwArray = wsArray.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < cwArray.length(); j++) {
                    result.append(cwArray.getJSONObject(j).getString("w"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 显示对比结果并计算相似度
     */
    private void showComparisonResult(String recognizedText) {
        String originalText = currentPuzzle.getDialectText();

        // 创建带颜色的识别结果
        SpannableString spannable = new SpannableString(recognizedText);

        // 计算相似度（使用编辑距离算法）
        double similarity = calculateSimilarity(originalText, recognizedText);

        // 标记不同颜色
        int minLength = Math.min(originalText.length(), recognizedText.length());
        for (int i = 0; i < minLength; i++) {
            if (originalText.charAt(i) == recognizedText.charAt(i)) {
                spannable.setSpan(new ForegroundColorSpan(COLOR_CORRECT),
                        i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(COLOR_INCORRECT),
                        i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        // 处理长度不同的部分（全部标红）
        if (recognizedText.length() > originalText.length()) {
            spannable.setSpan(new ForegroundColorSpan(COLOR_INCORRECT),
                    originalText.length(), recognizedText.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // 显示结果
        tvRecognizedText.setText("识别结果：");
        tvRecognizedText.append(spannable);
        tvSimilarity.setText(String.format("相似度：%.1f%%", similarity));

        // 判断是否通关
        if (similarity >= PASS_THRESHOLD) {
            Toast.makeText(this, "恭喜！准确度达标，通关成功～", Toast.LENGTH_SHORT).show();

            // 新增跳转至奖励页面的逻辑
            Intent intent = new Intent(this, RewardActivity.class);
            intent.putExtra("PUZZLE_DATA", currentPuzzle);
            intent.putExtra("ALL_PUZZLES", (java.io.Serializable) allPuzzles);
            intent.putExtra("CURRENT_INDEX", getIntent().getIntExtra("CURRENT_INDEX", 0));
            startActivity(intent);
            finish(); // 关闭当前语音答题页面
        } else {
            tvRecordStatus.setText("准确度未达标，请重新尝试（需达到70%以上）");
        }
    }

    /**
     * 使用编辑距离计算文本相似度
     */
    private double calculateSimilarity(String text1, String text2) {
        if (text1.isEmpty() && text2.isEmpty()) return 100.0;
        if (text1.isEmpty() || text2.isEmpty()) return 0.0;

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        for (int i = 0; i <= text1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= text2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }

        int maxLength = Math.max(text1.length(), text2.length());
        return (1 - (double) dp[text1.length()][text2.length()] / maxLength) * 100;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isRecording) {
                    startVoiceRecognition();
                }
            } else {
                Toast.makeText(this, "需要录音权限才能进行语音答题", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIat != null) {
            mIat.destroy();
        }
    }
}