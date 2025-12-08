package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItem task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
        }

        // 获取视图控件
        ImageView ivStar = convertView.findViewById(R.id.iv_star);
        TextView tvTaskName = convertView.findViewById(R.id.tv_task_name);
        TextView tvDueDate = convertView.findViewById(R.id.tv_due_date);

        // 设置数据
        if (task != null) {
            // 控制五角星显示/隐藏
            ivStar.setVisibility(task.isImportant() ? View.VISIBLE : View.GONE);
            tvTaskName.setText(task.getTaskName());
            tvDueDate.setText(task.getDueDate());
        }

        return convertView;
    }
}