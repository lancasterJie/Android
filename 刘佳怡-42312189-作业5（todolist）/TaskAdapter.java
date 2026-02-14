// src/main/java/com/example/todolistapp/TaskAdapter.java
package com.example.work5listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.work5listview.R;
import com.example.work5listview.TaskItem;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TaskItem> taskItems;

    public TaskAdapter(Context context, ArrayList<TaskItem> taskItems) {
        this.context = context;
        this.taskItems = taskItems;
    }

    @Override
    public int getCount() {
        return taskItems.size();
    }

    @Override
    public Object getItem(int position) {
        return taskItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_task, parent, false);
        }

        TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);
        TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
        TextView tvStar = convertView.findViewById(R.id.tvStar);

        TaskItem item = taskItems.get(position);
        tvTaskName.setText(item.getTaskName());
        tvDueDate.setText(item.getDueDate());

        // 显示星标（如果为优先级）
        if (item.isPriority()) {
            tvStar.setVisibility(View.VISIBLE);
        } else {
            tvStar.setVisibility(View.GONE);
        }

        return convertView;
    }
}