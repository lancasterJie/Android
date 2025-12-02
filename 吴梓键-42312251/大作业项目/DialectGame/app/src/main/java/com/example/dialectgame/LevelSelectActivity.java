package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dialectgame.model.DialectPuzzle;
import java.util.List;

public class LevelSelectActivity extends AppCompatActivity {

    private String region;
    private List<DialectPuzzle> puzzles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        // 获取数据
        region = getIntent().getStringExtra("REGION");
        puzzles = (List<DialectPuzzle>) getIntent().getSerializableExtra("PUZZLES");

        // 初始化UI
        TextView title = findViewById(R.id.tv_region_title);
        title.setText("选择关卡 - " + region + "话");

        GridView gridView = findViewById(R.id.grid_levels);
        gridView.setAdapter(new LevelAdapter());

        // 关卡点击事件
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            startQuiz(position);
        });

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    // 启动答题界面
    private void startQuiz(int position) {
        Intent intent = new Intent(this, TranslationQuizActivity.class);
        intent.putExtra("PUZZLES", (java.io.Serializable) puzzles);
        intent.putExtra("CURRENT_INDEX", position);
        startActivity(intent);
        finish();
    }

    // 关卡适配器
    private class LevelAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return puzzles.size();
        }

        @Override
        public Object getItem(int position) {
            return puzzles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_level, parent, false);
            }

            TextView tvLevel = convertView.findViewById(R.id.tv_level);
            tvLevel.setText("关卡 " + (position + 1));

            return convertView;
        }
    }
}