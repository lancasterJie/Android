package com.example.task5;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<TaskItem> {
    private final ArrayList<TaskItem> taskList;
    private final LayoutInflater inflater;

    // ViewHolder优化
    static class ViewHolder {
        LinearLayout llTaskItem; // 根布局（点击划横线）
        TextView tvStar;
        TextView tvTaskName;
        TextView tvDueDate;
    }

    public TaskListAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, R.layout.list_item_task, tasks);
        this.taskList = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_task, parent, false);
            holder = new ViewHolder();
            holder.llTaskItem = convertView.findViewById(R.id.ll_task_item); // 根布局
            holder.tvStar = convertView.findViewById(R.id.tv_star);
            holder.tvTaskName = convertView.findViewById(R.id.tv_task_name);
            holder.tvDueDate = convertView.findViewById(R.id.tv_due_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取当前任务
        TaskItem currentTask = taskList.get(position);

        // 1. 绑定基础数据
        holder.tvTaskName.setText(currentTask.getTaskName());
        holder.tvDueDate.setText(currentTask.getDueDate());

        // 2. 星星状态：显示/隐藏
        holder.tvStar.setText(currentTask.isStarred() ? "⭐" : "☆");


        // 3. 划横线状态：根据isCompleted设置文字划线
        if (currentTask.isCompleted()) {
            holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // 4. 星星点击事件：切换星星
        holder.tvStar.setOnClickListener(v -> {
            currentTask.setStarred(!currentTask.isStarred());
            holder.tvStar.setText(currentTask.isStarred() ? "⭐" : "☆");
        });

        // 5. 根布局点击事件：切换划横线（核心新增）
        holder.llTaskItem.setOnClickListener(v -> {
            // 切换完成状态
            currentTask.setCompleted(!currentTask.isCompleted());
            // 更新文字划线
            if (currentTask.isCompleted()) {
                holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        return convertView;
    }

    // 搜索刷新列表
    public void updateTasks(ArrayList<TaskItem> newTasks) {
        taskList.clear();
        taskList.addAll(newTasks);
        notifyDataSetChanged();
    }
}