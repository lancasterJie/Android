package com.example.txte5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 将 TaskItem 映射到自定义列表项视图的适配器。
 */
public class TaskAdapter extends ArrayAdapter<TaskItem> {

    public TaskAdapter(@NonNull Context context, @NonNull List<TaskItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskItem item = getItem(position);
        if (item != null) {
            holder.taskName.setText(item.getTaskName());
            holder.dueDate.setText(item.getDueDate());
        }

        return convertView;
    }

    private static class ViewHolder {
        final TextView taskName;
        final TextView dueDate;

        ViewHolder(View root) {
            taskName = root.findViewById(R.id.tvTaskName);
            dueDate = root.findViewById(R.id.tvDueDate);
        }
    }
}

