package com.example.listview_homework;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TaskAdapter 自定义适配器
 * 用于将 TaskItem 数据绑定到 ListView 的列表项视图上
 */
public class TaskAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TaskItem> taskList;
    private LayoutInflater inflater;
    private OnTaskStatusChangeListener statusChangeListener;

    /**
     * 任务状态变化监听器接口
     */
    public interface OnTaskStatusChangeListener {
        void onTaskStatusChanged(int position, boolean isCompleted);
    }

    /**
     * 构造函数
     * @param context 上下文
     * @param taskList 任务列表
     */
    public TaskAdapter(Context context, ArrayList<TaskItem> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * 设置任务状态变化监听器
     * @param listener 监听器
     */
    public void setOnTaskStatusChangeListener(OnTaskStatusChangeListener listener) {
        this.statusChangeListener = listener;
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
        ViewHolder holder;

        // 使用 ViewHolder 模式优化性能
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_task, parent, false);
            holder = new ViewHolder();
            holder.taskNameTextView = convertView.findViewById(R.id.textViewTaskName);
            holder.dueDateTextView = convertView.findViewById(R.id.textViewDueDate);
            holder.checkBoxCompleted = convertView.findViewById(R.id.checkBoxCompleted);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取当前位置的任务项
        TaskItem currentTask = taskList.get(position);

        // 将数据绑定到视图
        holder.taskNameTextView.setText(currentTask.getTaskName());
        holder.dueDateTextView.setText("截止日期: " + currentTask.getDueDate());
        holder.checkBoxCompleted.setChecked(currentTask.isCompleted());

        // 根据完成状态设置文本样式
        if (currentTask.isCompleted()) {
            holder.taskNameTextView.setPaintFlags(
                holder.taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskNameTextView.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            holder.taskNameTextView.setPaintFlags(
                holder.taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.taskNameTextView.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        // 设置 CheckBox 点击事件
        holder.checkBoxCompleted.setOnClickListener(v -> {
            boolean isChecked = ((CheckBox) v).isChecked();
            currentTask.setCompleted(isChecked);
            if (statusChangeListener != null) {
                statusChangeListener.onTaskStatusChanged(position, isChecked);
            }
            notifyDataSetChanged();
        });

        return convertView;
    }

    /**
     * ViewHolder 静态内部类
     * 用于缓存列表项的视图引用，提高性能
     */
    static class ViewHolder {
        TextView taskNameTextView;
        TextView dueDateTextView;
        CheckBox checkBoxCompleted;
    }
}
