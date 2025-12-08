package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.CollectedCard;
import com.example.dialectgame.model.DialectPuzzle;
import java.util.List;

public class RewardActivity extends AppCompatActivity {
    private DialectPuzzle currentPuzzle;
    private List<DialectPuzzle> allPuzzles;
    private int currentIndex;
    private ImageView ivRewardCard;
    private TextView tvCulturalKnowledge;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        // 获取数据
        currentPuzzle = (DialectPuzzle) getIntent().getSerializableExtra("PUZZLE_DATA");
        allPuzzles = (List<DialectPuzzle>) getIntent().getSerializableExtra("ALL_PUZZLES");
        currentIndex = getIntent().getIntExtra("CURRENT_INDEX", 0);


        if (currentPuzzle == null || allPuzzles == null) {
            finish();
            return;
        }

        // 初始化数据库
        db = AppDatabase.getInstance(this);

        // 初始化UI
        initView();
        // 收集卡片
        collectCard();
    }

    private void initView() {
        ivRewardCard = findViewById(R.id.iv_reward_card);
        tvCulturalKnowledge = findViewById(R.id.tv_cultural_knowledge);
        Button btnPrev = findViewById(R.id.btn_prev);
        Button btnNext = findViewById(R.id.btn_next);
        Button btnLevelSelect = findViewById(R.id.btn_level_select);
        Button btnBack = findViewById(R.id.btn_back_to_forum);
        Button btnAlbum = findViewById(R.id.btn_go_to_album);

        // 设置卡片图片
        int cardResId = getResources().getIdentifier(
                currentPuzzle.getCardImageUrl().replace("drawable/", ""),
                "drawable",
                getPackageName()
        );
        if (cardResId != 0) {
            ivRewardCard.setImageResource(cardResId);
        } else {
            ivRewardCard.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // 设置文化知识
        tvCulturalKnowledge.setText(currentPuzzle.getCulturalKnowledge());

        // 控制上一题按钮状态
        btnPrev.setEnabled(currentIndex > 0);

        // 控制下一题按钮状态
        btnNext.setEnabled(currentIndex < allPuzzles.size() - 1);

        // 上一题
        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                startQuiz(currentIndex - 1);
            }
        });

        // 下一题
        btnNext.setOnClickListener(v -> {
            if (currentIndex < allPuzzles.size() - 1) {
                startQuiz(currentIndex + 1);
            }
        });

        // 返回关卡选择
        btnLevelSelect.setOnClickListener(v -> {
            Intent intent = new Intent(this, LevelSelectActivity.class);
            intent.putExtra("REGION", currentPuzzle.getRegion());
            intent.putExtra("PUZZLES", (java.io.Serializable) allPuzzles);
            startActivity(intent);
            finish();
        });

        // 返回论坛
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // 查看图鉴
        btnAlbum.setOnClickListener(v -> {
            Intent intent = new Intent(this, CardAlbumActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // 启动答题界面
    private void startQuiz(int index) {
        Intent intent = new Intent(this, TranslationQuizActivity.class);
        intent.putExtra("PUZZLES", (java.io.Serializable) allPuzzles);
        intent.putExtra("CURRENT_INDEX", index);
        startActivity(intent);
        finish();
    }

    // 收集卡片
    private void collectCard() {
        new Thread(() -> {
            if (!db.cardDao().isCardCollected(currentPuzzle.getId())) {
                CollectedCard card = new CollectedCard();
                card.setCardId(currentPuzzle.getId());
                card.setRegion(currentPuzzle.getRegion());
                card.setDialectType(currentPuzzle.getDialectType());
                card.setCardImageUrl(currentPuzzle.getCardImageUrl());
                card.setCulturalKnowledge(currentPuzzle.getCulturalKnowledge());
                card.setCollectTime(System.currentTimeMillis());
                db.cardDao().insertCard(card);
            }
        }).start();
    }
}