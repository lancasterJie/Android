package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 详情页：通过静态 start() 方法接收 title/content/time 并展示。
 */
public class RecordDetailActivity extends AppCompatActivity {

    private static final String EXTRA_TITLE = "extra_title";
    private static final String EXTRA_CONTENT = "extra_content";
    private static final String EXTRA_TIME = "extra_time";

    /**
     * 封装跳转逻辑，方便列表页复用。
     */
    public static void start(Context context, String title, String content, String time) {
        Intent intent = new Intent(context, RecordDetailActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_TIME, time);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvTime = findViewById(R.id.tv_detail_time);
        TextView tvContent = findViewById(R.id.tv_detail_content);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String content = intent.getStringExtra(EXTRA_CONTENT);
        String time = intent.getStringExtra(EXTRA_TIME);

        // 当标题为空时使用默认文案，避免空白标题
        if (title == null || title.isEmpty()) {
            title = getString(R.string.records_detail_title);
        }
        setTitle(title);
        tvTitle.setText(title);
        tvTime.setText(time == null ? "" : time);
        tvContent.setText(content == null ? "" : content);
    }
}

