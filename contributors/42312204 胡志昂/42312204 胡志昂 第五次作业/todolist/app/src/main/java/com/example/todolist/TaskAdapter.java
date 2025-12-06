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

    // 构造方法
    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. 获取当前任务项
        TaskItem task = getItem(position);

        // 2. 复用视图（优化性能）
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_task, parent, false);
        }

        // 3. 找到列表项中的控件
        ImageView ivStar = convertView.findViewById(R.id.iv_star);
        TextView tvTaskName = convertView.findViewById(R.id.tv_task_name);
        TextView tvDueDate = convertView.findViewById(R.id.tv_due_date);

        // 4. 绑定数据到控件
        tvTaskName.setText(task.getTaskName());
        tvDueDate.setText(task.getDueDate());
        // 重要任务显示星星，否则隐藏
        ivStar.setVisibility(task.isImportant() ? View.VISIBLE : View.GONE);

        return convertView;
    }
}