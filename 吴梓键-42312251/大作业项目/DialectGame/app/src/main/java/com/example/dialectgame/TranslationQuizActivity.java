package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.DialectPuzzle;
import com.example.dialectgame.model.UserPuzzleProgress;
import com.example.dialectgame.utils.UserManager;

import java.util.List;

public class TranslationQuizActivity extends AppCompatActivity {

    private List<DialectPuzzle> puzzles;
    private int currentIndex;
    private DialectPuzzle currentPuzzle;
    private EditText etAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_quiz);

        // 获取数据
        puzzles = (List<DialectPuzzle>) getIntent().getSerializableExtra("PUZZLES");
        currentIndex = getIntent().getIntExtra("CURRENT_INDEX", 0);

        if (puzzles == null || puzzles.isEmpty()) {
            finish();
            return;
        }

        currentPuzzle = puzzles.get(currentIndex);
        etAnswer = findViewById(R.id.et_answer);

        // 初始化UI
        initUI();

        // 提交按钮
        findViewById(R.id.btn_submit).setOnClickListener(v -> checkAnswer());

        // 语音按钮
        findViewById(R.id.btn_voice).setOnClickListener(v -> speakDialect());
    }

    private void initUI() {
        TextView tvDialect = findViewById(R.id.tv_dialect);
        TextView tvRegion = findViewById(R.id.tv_region);
        TextView tvLevel = findViewById(R.id.tv_level);

        tvRegion.setText(currentPuzzle.getRegion() + " - " + currentPuzzle.getDialectType());
        tvDialect.setText(currentPuzzle.getDialectText());
        tvLevel.setText("关卡 " + (currentPuzzle.getLevel()));
    }

    // 检查答案（本地判断）
    private void checkAnswer() {
        String userAnswer = etAnswer.getText().toString().trim();
        if (userAnswer.isEmpty()) {
            Toast.makeText(this, "请输入答案", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查用户答案是否正确
        boolean isCorrect = currentPuzzle.getStandardTranslations().stream()
                .anyMatch(answer -> answer.equalsIgnoreCase(userAnswer));

        if (isCorrect) {
            // 答案正确，更新翻译模块进度
            updateTranslationProgress();
            Toast.makeText(this, "翻译解密成功！", Toast.LENGTH_SHORT).show();
            finish(); // 返回模块选择页
        } else {
            Toast.makeText(this, "答案不正确，请再试一次", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTranslationProgress() {
        // 获取当前用户ID
        int userId = UserManager.getInstance(this).getCurrentUser().getId();
        // 获取当前谜题ID
        String puzzleId = currentPuzzle.getId();
        // 获取数据库实例
        AppDatabase db = AppDatabase.getInstance(this);

        // 在后台线程中更新进度（Room不允许在主线程操作数据库）
        new Thread(() -> {
            // 查询当前谜题的进度记录
            UserPuzzleProgress progress = db.progressDao().getProgress(userId, puzzleId);
            if (progress != null) {
                // 更新翻译模块为已完成
                progress.setTranslationCompleted(true);
                // 保存更新到数据库
                db.progressDao().updateProgress(progress);
            }
        }).start();
    }

    // 语音朗读方言
    private void speakDialect() {
        // 这里实现语音朗读功能
        Toast.makeText(this, "正在朗读: " + currentPuzzle.getDialectText(), Toast.LENGTH_SHORT).show();
        // 实际项目中应调用讯飞SDK进行语音合成
    }
}