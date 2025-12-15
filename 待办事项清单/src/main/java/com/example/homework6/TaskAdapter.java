package com.example.homework6;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private final Context context;
    private final List<TaskItem> tasks;

    public TaskAdapter(Context context, List<TaskItem> tasks) {
        super(context, R.layout.task_list_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list_item, parent, false);

        TextView taskNameTextView = rowView.findViewById(R.id.taskName);
        TextView dueDateTextView = rowView.findViewById(R.id.dueDate);

        TaskItem taskItem = tasks.get(position);
        taskNameTextView.setText(taskItem.getTaskName());
        dueDateTextView.setText(taskItem.getDueDate());

        // 根据任务的完成状态设置文本格式
        if (taskItem.isCompleted()) {
            taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // 划掉
        } else {
            taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)); // 取消划掉
        }

        // 添加点击事件，点击后切换任务完成状态
        rowView.setOnClickListener(v -> {
            taskItem.setCompleted(!taskItem.isCompleted());
            notifyDataSetChanged(); // 刷新列表
        });

        return rowView;
    }
}