package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private Context context;
    private ArrayList<TaskItem> tasks;

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        TaskItem task = tasks.get(position);

        TextView taskNameTextView = convertView.findViewById(R.id.taskNameTextView);
        TextView dueDateTextView = convertView.findViewById(R.id.dueDateTextView);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        taskNameTextView.setText(task.getTaskName());
        dueDateTextView.setText("截止日期: " + task.getDueDate());

        // 使用 tag 存储当前位置，避免 View 复用导致的问题
        deleteButton.setTag(position);
        
        // 设置删除按钮的点击事件
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从 tag 中获取位置
                int pos = (Integer) v.getTag();
                // 从列表中移除该项
                if (pos >= 0 && pos < tasks.size()) {
                    tasks.remove(pos);
                    // 通知适配器数据已更改
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }
}

