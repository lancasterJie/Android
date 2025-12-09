package com.example.fourthhomework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordDetailActivity extends AppCompatActivity {
    private EditText etTitle;
    private TextView tvTime;
    private EditText etContent;
    private Button btnSave;
    private Button btnBack;

    private RecordDao recordDao;
    private int recordId; // 当前记录的 ID
    private Record currentRecord; // 当前记录对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 初始化控件
        initViews();

        // 初始化数据库操作类
        recordDao = new RecordDao(this);

        // 获取从列表界面传递的记录 ID
        recordId = getIntent().getIntExtra("RECORD_ID", -1);
        if (recordId == -1) {
            Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 加载记录详情
        loadRecordDetail();

        // 设置按钮点击事件
        setClickListeners();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        etTitle = findViewById(R.id.et_detail_title);
        tvTime = findViewById(R.id.tv_detail_time);
        etContent = findViewById(R.id.et_detail_content);
        btnSave = findViewById(R.id.btn_detail_save);
        btnBack = findViewById(R.id.btn_detail_back);
    }

    /**
     * 加载记录详情到 UI
     */
    private void loadRecordDetail() {
        currentRecord = recordDao.getRecordById(recordId);
        if (currentRecord == null) {
            Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 填充数据到 UI
        etTitle.setText(currentRecord.getTitle());
        tvTime.setText("更新时间：" + currentRecord.getTime());
        etContent.setText(currentRecord.getContent());
    }

    /**
     * 设置按钮点击事件
     */
    private void setClickListeners() {
        // 保存修改
        btnSave.setOnClickListener(v -> {
            String newTitle = etTitle.getText().toString().trim();
            String newContent = etContent.getText().toString().trim();

            // 非空校验
            if (newTitle.isEmpty() || newContent.isEmpty()) {
                Toast.makeText(this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 更新记录对象（时间改为当前时间）
            String currentTime = getCurrentTime();
            currentRecord.setTitle(newTitle);
            currentRecord.setContent(newContent);
            currentRecord.setTime(currentTime);

            // 调用 Dao 保存到数据库
            boolean isUpdated = recordDao.updateRecord(currentRecord);
            if (isUpdated) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                tvTime.setText("更新时间：" + currentTime); // 刷新时间显示
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });

        // 返回列表界面
        btnBack.setOnClickListener(v -> finish());
    }

    /**
     * 获取当前时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }
}