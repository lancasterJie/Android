package com.example.listwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private int resourceId;
    private OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onTaskStatusChanged(int position, boolean isCompleted);
        void onTaskDeleted(int position);
    }

    public TaskAdapter(Context context, int resource, List<TaskItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    public void setOnTaskActionListener(OnTaskActionListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TaskItem task = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.taskName = view.findViewById(R.id.task_name);
            viewHolder.dueDate = view.findViewById(R.id.due_date);
            viewHolder.checkBoxComplete = view.findViewById(R.id.checkBox_complete);
            viewHolder.btnDelete = view.findViewById(R.id.btn_delete);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // 设置任务名称和日期
        viewHolder.taskName.setText(task.getTaskName());
        viewHolder.dueDate.setText(task.getDueDate());
        viewHolder.checkBoxComplete.setChecked(task.isCompleted());

        // 根据完成状态设置文本颜色
        if (task.isCompleted()) {
            viewHolder.taskName.setTextColor(0xFF888888); // 灰色
        } else {
            viewHolder.taskName.setTextColor(0xFF000000); // 黑色
        }

        // 复选框点击事件
        viewHolder.checkBoxComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.toggleCompleted();
                if (listener != null) {
                    listener.onTaskStatusChanged(position, task.isCompleted());
                }
                notifyDataSetChanged();
            }
        });

        // 删除按钮点击事件
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTaskDeleted(position);
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        TextView taskName;
        TextView dueDate;
        CheckBox checkBoxComplete;
        Button btnDelete;
    }
}