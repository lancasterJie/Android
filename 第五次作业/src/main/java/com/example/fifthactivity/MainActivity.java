package com.example.fifthactivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 初始化示例数据
        ArrayList<TaskItem> tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-15"));
        tasks.add(new TaskItem("play badminton", "2025-12-09"));
        tasks.add(new TaskItem("listen to listening test", "2025-12-10"));
        tasks.add(new TaskItem("Call mom", "2025-12-09"));

        // 2. 获取ListView控件
        ListView listView = findViewById(R.id.lv_tasks);

        // 3. 创建适配器并绑定
        TaskAdapter adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);
    }
}