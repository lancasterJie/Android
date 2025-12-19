package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private List<TaskItem> taskList;
    private List<TaskItem> filteredList;

    public TaskAdapter(Context context) {
        this.context = context;
        this.taskList = new ArrayList<>();
        this.filteredList = new ArrayList<>();

        taskList.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        taskList.add(new TaskItem("Buy groceries", "2024-05-01"));
        taskList.add(new TaskItem("Walk the dog", "2024-04-20"));
        taskList.add(new TaskItem("Call John", "2024-04-23"));
        filteredList.addAll(taskList);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemtask, parent, false);
            holder = new ViewHolder();
            holder.ivFavorite = convertView.findViewById(R.id.ivFavorite);
            holder.tvTaskName = convertView.findViewById(R.id.tvTaskName);
            holder.tvDueDate = convertView.findViewById(R.id.tvDueDate);
            holder.cbCompleted = convertView.findViewById(R.id.cbCompleted);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskItem task = filteredList.get(position);

        holder.tvTaskName.setText(task.getTaskName());
        holder.tvDueDate.setText(task.getDueDate());

        if (task.isCompleted()) {
            holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTaskName.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTaskName.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        updateFavoriteIcon(holder.ivFavorite, task.isFavorite());

        holder.cbCompleted.setChecked(task.isCompleted());

        holder.ivFavorite.setOnClickListener(v -> {
            task.toggleFavorite();
            updateFavoriteIcon(holder.ivFavorite, task.isFavorite());
            updateOriginalItem(task);
            notifyDataSetChanged();
        });

        holder.cbCompleted.setOnClickListener(v -> {
            task.setCompleted(holder.cbCompleted.isChecked());
            if (task.isCompleted()) {
                holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvTaskName.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            } else {
                holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
                holder.tvTaskName.setTextColor(context.getResources().getColor(android.R.color.black));
            }
            updateOriginalItem(task);
            notifyDataSetChanged();
        });

        return convertView;
    }

    private void updateFavoriteIcon(ImageView imageView, boolean isFavorite) {
        imageView.setImageResource(
                isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star_border
        );
    }

    private void updateOriginalItem(TaskItem updatedTask) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == updatedTask.getId()) {
                taskList.set(i, updatedTask);
                break;
            }
        }
    }

    public void addTask(TaskItem task) {
        taskList.add(0, task);
        filter("");
        notifyDataSetChanged();
    }

    public void filter(String keyword) {
        filteredList.clear();
        if (keyword.isEmpty()) {
            filteredList.addAll(taskList);
        } else {
            for (TaskItem task : taskList) {
                if (task.containsKeyword(keyword)) {
                    filteredList.add(task);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView ivFavorite;
        TextView tvTaskName;
        TextView tvDueDate;
        CheckBox cbCompleted;
    }
}