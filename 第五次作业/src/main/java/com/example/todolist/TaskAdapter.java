package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private List<Task> taskList;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }

        TextView taskTitle = convertView.findViewById(R.id.taskTitle);
        TextView taskDate = convertView.findViewById(R.id.taskDate);
        TextView overdueMarker = convertView.findViewById(R.id.overdueMarker);

        Task task = taskList.get(position);
        taskTitle.setText(task.getTitle());
        taskDate.setText(task.getDate());

        // 如果任务过期，显示红色三角形
        if (task.isOverdue()) {
            overdueMarker.setVisibility(View.VISIBLE);
        } else {
            overdueMarker.setVisibility(View.GONE);
        }

        return convertView;
    }
}