package com.example.homework5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取当前任务项
        TaskItem task = getItem(position);

        // 检查视图是否被重用，否则填充视图
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
        }

        // 获取视图引用
        TextView taskNameTextView = convertView.findViewById(R.id.taskNameTextView);
        TextView dueDateTextView = convertView.findViewById(R.id.dueDateTextView);

        // 设置数据
        if (task != null) {
            taskNameTextView.setText(task.getTaskName());
            dueDateTextView.setText("截止日期: " + task.getDueDate());
        }

        return convertView;
    }
}