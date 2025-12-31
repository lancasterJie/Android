package com.example.to_dolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    public TaskAdapter(Context context, int resource, ArrayList<TaskItem> tasks){
        super(context, resource, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TaskItem task = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
        }

        TextView tvTaskName = convertView.findViewById(R.id.tv_task_name);
        TextView tvDueDate = convertView.findViewById(R.id.tv_due_date);

        if (task != null) {
            tvTaskName.setText(task.getTaskName());
            tvDueDate.setText(task.getDueDate());
        }

        return convertView;
    }

}
