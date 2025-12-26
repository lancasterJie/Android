package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    private ArrayList<TaskItem> tasks;
    private MainActivity mainActivity;

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
        this.tasks = tasks;

        if (context instanceof MainActivity) {
            this.mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TaskItem task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_task, parent, false);
        }

        TextView taskNameTextView = convertView.findViewById(R.id.taskNameTextView);
        TextView dueDateTextView = convertView.findViewById(R.id.dueDateTextView);
        CheckBox completionCheckBox = convertView.findViewById(R.id.completionCheckBox);
        TextView deleteButton = convertView.findViewById(R.id.deleteButton);

        if (task != null) {
            taskNameTextView.setText(task.getTaskName());
            dueDateTextView.setText("截止日期: " + task.getDueDate());

            boolean isCompleted = task.isCompleted();
            completionCheckBox.setChecked(isCompleted);

            if (isCompleted) {
                taskNameTextView.setTextColor(0xFF888888);
                taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                taskNameTextView.setTextColor(0xFF000000);
                taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
            }

            completionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setCompleted(isChecked);
                    notifyDataSetChanged();
                    if (mainActivity != null) {
                        mainActivity.updateTaskCount();
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mainActivity != null) {
                        mainActivity.showDeleteTaskDialog(position);
                    }
                }
            });
        }

        return convertView;
    }
}
