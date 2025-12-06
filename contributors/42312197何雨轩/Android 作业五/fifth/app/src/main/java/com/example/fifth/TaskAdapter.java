package com.example.fifth;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    private Context context;
    private ArrayList<TaskItem> tasks;

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, R.layout.list_item_task, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_task, parent, false);

            holder = new ViewHolder();
            holder.ivStar = convertView.findViewById(R.id.iv_star);
            holder.tvTaskName = convertView.findViewById(R.id.tv_task_name);
            holder.tvDueDate = convertView.findViewById(R.id.tv_due_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskItem task = tasks.get(position);

        holder.tvTaskName.setText(task.getTaskName());
        holder.tvDueDate.setText(task.getDueDate());

        // 根据完成状态设置星星图标和文字样式
        if (task.isCompleted()) {
            holder.ivStar.setImageResource(android.R.drawable.btn_star_big_on);
            holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.ivStar.setImageResource(android.R.drawable.btn_star_big_off);
            holder.tvTaskName.setPaintFlags(holder.tvTaskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivStar;
        TextView tvTaskName;
        TextView tvDueDate;
    }
}
