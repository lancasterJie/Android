package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.DialectPuzzle;
import com.example.dialectgame.model.UserPuzzleProgress;
import com.example.dialectgame.utils.UserManager;

import java.io.Serializable;
import java.util.List;

public class PuzzleModuleSelectActivity extends AppCompatActivity {
    private DialectPuzzle currentPuzzle;
    private List<DialectPuzzle> allPuzzles;
    private int currentIndex;
    private AppDatabase db;
    private UserPuzzleProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_module_select);

        // 获取数据
        currentPuzzle = (DialectPuzzle) getIntent().getSerializableExtra("PUZZLE_DATA");
        allPuzzles = (List<DialectPuzzle>) getIntent().getSerializableExtra("ALL_PUZZLES");
        currentIndex = getIntent().getIntExtra("CURRENT_INDEX", 0);

        if (currentPuzzle == null || allPuzzles == null) {
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);
        initProgress();
        initView();
    }

    // 初始化进度记录
    private void initProgress() {
        int userId = UserManager.getInstance(this).getCurrentUser().getId();
        new Thread(() -> {
            progress = db.progressDao().getProgress(userId, currentPuzzle.getId());
            if (progress == null) {
                progress = new UserPuzzleProgress(userId, currentPuzzle.getId());
                db.progressDao().insertProgress(progress);
            }
            runOnUiThread(this::updateModuleStatus);
        }).start();
    }

    private void initView() {
        // 标题设置
        TextView tvTitle = findViewById(R.id.tv_module_title);
        tvTitle.setText(String.format("%s - 关卡 %d", currentPuzzle.getRegion(), currentPuzzle.getLevel()));

        TextView tvPuzzleText = findViewById(R.id.tv_puzzle_text);
        tvPuzzleText.setText(currentPuzzle.getDialectText());

        // 翻译模块按钮
        Button btnTranslation = findViewById(R.id.btn_translation_module);
        btnTranslation.setOnClickListener(v -> {
            Intent intent = new Intent(this, TranslationQuizActivity.class);
            intent.putExtra("PUZZLE_DATA", currentPuzzle);
            intent.putExtra("ALL_PUZZLES", (Serializable) allPuzzles);
            intent.putExtra("CURRENT_INDEX", currentIndex);
            startActivity(intent);
        });

        // 语音模块按钮
        Button btnVoice = findViewById(R.id.btn_voice_module);
        btnVoice.setOnClickListener(v -> {
            Intent intent = new Intent(this, VoiceQuizActivity.class);
            intent.putExtra("PUZZLE_DATA", currentPuzzle);
            intent.putExtra("ALL_PUZZLES", (Serializable) allPuzzles);
            intent.putExtra("CURRENT_INDEX", currentIndex);
            startActivity(intent);
        });

        // 奖励按钮（默认隐藏，两个模块都完成才显示）
        findViewById(R.id.btn_go_reward).setOnClickListener(v -> {
            Intent intent = new Intent(this, RewardActivity.class);
            intent.putExtra("PUZZLE_DATA", currentPuzzle);
            intent.putExtra("ALL_PUZZLES", (Serializable) allPuzzles);
            intent.putExtra("CURRENT_INDEX", currentIndex);
            startActivity(intent);
            finish();
        });

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    // 更新模块状态显示
    private void updateModuleStatus() {
        TextView tvTransStatus = findViewById(R.id.tv_translation_status);
        TextView tvVoiceStatus = findViewById(R.id.tv_voice_status);
        Button btnReward = findViewById(R.id.btn_go_reward);

        // 翻译模块状态
        if (progress.isTranslationCompleted()) {
            tvTransStatus.setText("（已完成）");
            tvTransStatus.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvTransStatus.setText("（未完成）");
            tvTransStatus.setTextColor(getResources().getColor(R.color.red));
        }

        // 语音模块状态
        if (progress.isVoiceCompleted()) {
            tvVoiceStatus.setText("（已完成）");
            tvVoiceStatus.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvVoiceStatus.setText("（未完成）");
            tvVoiceStatus.setTextColor(getResources().getColor(R.color.red));
        }

        // 两个模块都完成才显示奖励按钮
        btnReward.setVisibility(progress.isTranslationCompleted() && progress.isVoiceCompleted()
                ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新进度状态
        initProgress();
    }
}