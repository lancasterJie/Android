package com.zzx.homework;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    private List<TaskItem> originalList;
    private List<TaskItem> displayList;

    public TaskAdapter(@NonNull Context context, List<TaskItem> tasks) {
        super(context, 0, tasks);
        originalList = new ArrayList<>(tasks);
        displayList = tasks;
    }

    @Override
    public int getCount() {
        return displayList.size();
    }

    @Override
    public TaskItem getItem(int position) {
        return displayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_task, parent, false);
        }

        TaskItem task = getItem(position);

        TextView name = convertView.findViewById(R.id.tvTaskName);
        TextView date = convertView.findViewById(R.id.tvDueDate);

        name.setText(task.getTaskName());
        date.setText("Due: " + task.getDueDate());

        if (task.isCompleted()) {
            name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            name.setAlpha(0.5f);
        } else {
            name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            name.setAlpha(1f);
        }

        return convertView;
    }

    // 搜索过滤
    public void filter(String keyword) {
        displayList.clear();
        if (keyword.isEmpty()) {
            displayList.addAll(originalList);
        } else {
            for (TaskItem task : originalList) {
                if (task.getTaskName().toLowerCase().contains(keyword.toLowerCase())) {
                    displayList.add(task);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void addTask(TaskItem task) {
        originalList.add(task);
        displayList.add(task);
        notifyDataSetChanged();
    }
}
