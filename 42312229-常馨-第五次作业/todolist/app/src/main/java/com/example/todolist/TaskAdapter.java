package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private Context context;
    private ArrayList<TaskItem> tasks;
    private OnTaskCompletedListener onTaskCompletedListener;

    public interface OnTaskCompletedListener {
        void onTaskCompleted(int position);
    }

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    public void setOnTaskCompletedListener(OnTaskCompletedListener listener) {
        this.onTaskCompletedListener = listener;
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
        CheckBox completedCheckBox = convertView.findViewById(R.id.completedCheckBox);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        // 设置任务数据
        if (task != null) {
            taskNameTextView.setText(task.getTaskName());
            dueDateTextView.setText("截止日期: " + task.getDueDate());

            // 根据完成状态设置文本样式
            if (task.isCompleted()) {
                taskNameTextView.setAlpha(0.5f);
                dueDateTextView.setAlpha(0.5f);
                taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
                completedCheckBox.setChecked(true);
            } else {
                taskNameTextView.setAlpha(1.0f);
                dueDateTextView.setAlpha(1.0f);
                taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
                completedCheckBox.setChecked(false);
            }

            // 设置复选框点击事件
            completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setCompleted(isChecked);
                    notifyDataSetChanged();

                    // 触发回调
                    if (onTaskCompletedListener != null) {
                        onTaskCompletedListener.onTaskCompleted(position);
                    }
                }
            });

            // 设置删除按钮点击事件
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasks.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    // 添加新任务
    public void addTask(TaskItem task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    // 获取所有任务
    public ArrayList<TaskItem> getAllTasks() {
        return tasks;
    }

    // 获取已完成任务数量
    public int getCompletedTaskCount() {
        int count = 0;
        for (TaskItem task : tasks) {
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    // 更新数据方法
    public void updateData(ArrayList<TaskItem> newTasks) {
        this.tasks.clear();
        this.tasks.addAll(newTasks);
        notifyDataSetChanged();
    }
}