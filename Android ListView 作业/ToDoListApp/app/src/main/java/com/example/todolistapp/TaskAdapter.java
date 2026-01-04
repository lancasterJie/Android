package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private Context context;
    private int resource;
    private ArrayList<TaskItem> tasks;

    public TaskAdapter(Context context, int resource, ArrayList<TaskItem> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.resource = resource;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            holder.taskNameTextView = convertView.findViewById(R.id.taskName);
            holder.dueDateTextView = convertView.findViewById(R.id.dueDate);
            holder.priorityView = convertView.findViewById(R.id.priorityView);
            holder.overdueIndicator = convertView.findViewById(R.id.overdueIndicator);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskItem task = getItem(position);
        if (task != null) {
            // 设置任务名称
            holder.taskNameTextView.setText(task.getTaskName());

            // 设置截止日期
            holder.dueDateTextView.setText(task.getFormattedDate());

            // 设置复选框状态
            holder.checkBox.setChecked(task.isCompleted());

            // 根据完成状态设置文本样式
            if (task.isCompleted()) {
                holder.taskNameTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                holder.taskNameTextView.setPaintFlags(holder.taskNameTextView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
                holder.dueDateTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                holder.dueDateTextView.setText("已完成");
            } else {
                holder.taskNameTextView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                holder.taskNameTextView.setPaintFlags(holder.taskNameTextView.getPaintFlags() & ~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);

                // 检查是否过期
                if (task.isOverdue()) {
                    holder.dueDateTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
                    holder.overdueIndicator.setVisibility(View.VISIBLE);
                } else {
                    holder.dueDateTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                    holder.overdueIndicator.setVisibility(View.GONE);
                }
            }

            // 设置优先级颜色
            holder.priorityView.setBackgroundColor(ContextCompat.getColor(context, task.getPriorityColor()));

            // 复选框点击监听
            holder.checkBox.setOnCheckedChangeListener(null); // 清除之前的监听器
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setCompleted(isChecked);
                    notifyDataSetChanged();

                    // 显示提示信息
                    String message = isChecked ? "任务已完成: " + task.getTaskName() : "任务未完成: " + task.getTaskName();
                    android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
                }
            });

            // 添加点击整个项的事件
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里可以添加点击事件，比如显示任务详情
                    showTaskDetails(task);
                }
            });
        }

        return convertView;
    }

    private void showTaskDetails(TaskItem task) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("任务详情")
                .setMessage("任务: " + task.getTaskName() +
                        "\n截止日期: " + task.getDueDate() +
                        "\n优先级: " + task.getPriorityText() +
                        "\n状态: " + (task.isCompleted() ? "已完成" : "未完成") +
                        "\n创建时间: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(new java.util.Date(task.getCreatedAt())))
                .setPositiveButton("确定", null)
                .show();
    }

    // ViewHolder模式提高性能
    static class ViewHolder {
        CheckBox checkBox;
        TextView taskNameTextView;
        TextView dueDateTextView;
        View priorityView;
        View overdueIndicator;
    }

    // 删除任务方法
    public void removeTask(int position) {
        if (position >= 0 && position < tasks.size()) {
            tasks.remove(position);
            notifyDataSetChanged();
        }
    }

    // 获取已完成任务数量
    public int getCompletedCount() {
        int count = 0;
        for (TaskItem task : tasks) {
            if (task.isCompleted()) count++;
        }
        return count;
    }

    // 获取未完成任务数量
    public int getPendingCount() {
        int count = 0;
        for (TaskItem task : tasks) {
            if (!task.isCompleted()) count++;
        }
        return count;
    }
}