package com.example.dialectgame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.adapter.CardAlbumAdapter;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.CollectedCard;
import java.util.List;

public class CardAlbumActivity extends AppCompatActivity {
    private RecyclerView rvCardAlbum;
    private TextView tvEmptyAlbum;
    private AppDatabase db;
    private CardAlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_album);

        // 初始化数据库
        db = AppDatabase.getInstance(this);

        // 初始化UI
        rvCardAlbum = findViewById(R.id.rv_card_album);
        tvEmptyAlbum = findViewById(R.id.tv_empty_album);

        // 设置RecyclerView为网格布局（2列）
        rvCardAlbum.setLayoutManager(new GridLayoutManager(this, 2));

        // 加载收集的卡片
        loadCollectedCards();
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    // 加载收集的卡片
    private void loadCollectedCards() {
        List<CollectedCard> cardList = db.cardDao().getAllCards();
        if (cardList.isEmpty()) {
            // 空状态
            tvEmptyAlbum.setVisibility(View.VISIBLE);
            rvCardAlbum.setVisibility(View.GONE);
        } else {
            // 显示卡片
            tvEmptyAlbum.setVisibility(View.GONE);
            rvCardAlbum.setVisibility(View.VISIBLE);
            adapter = new CardAlbumAdapter(cardList);
            rvCardAlbum.setAdapter(adapter);
        }
    }

    // 返回时刷新卡片列表
    @Override
    protected void onResume() {
        super.onResume();
        loadCollectedCards();
    }
}