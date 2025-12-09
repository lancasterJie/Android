package com.example.fifthactivity;

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
        // 获取当前任务
        TaskItem task = getItem(position);

        // 复用视图（优化性能）
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_task, parent, false);
        }

        // 绑定控件
        TextView tvTaskName = convertView.findViewById(R.id.tv_task_name);
        TextView tvDueDate = convertView.findViewById(R.id.tv_due_date);

        // 设置数据
        tvTaskName.setText(task.getTaskName());
        tvDueDate.setText(task.getDueDate());

        return convertView;
    }
}