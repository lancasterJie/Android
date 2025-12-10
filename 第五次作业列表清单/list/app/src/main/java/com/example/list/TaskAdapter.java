package com.example.list;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private Context context;
    private ArrayList<TaskItem> tasks;
    private OnStarClickListener starClickListener;

    public interface OnStarClickListener {
        void onStarClick(int position);
    }

    public TaskAdapter(@NonNull Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    public void setOnStarClickListener(OnStarClickListener listener) {
        this.starClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_task, parent, false);
        }

        TaskItem task = getItem(position);

        ImageView starImageView = convertView.findViewById(R.id.starImageView);
        TextView taskNameTextView = convertView.findViewById(R.id.taskNameTextView);
        TextView dueDateTextView = convertView.findViewById(R.id.dueDateTextView);

        if (task != null) {
            // Set task name and apply strikethrough if completed
            taskNameTextView.setText(task.getTaskName());
            if (task.isCompleted()) {
                taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                taskNameTextView.setAlpha(0.5f);
            } else {
                taskNameTextView.setPaintFlags(taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                taskNameTextView.setAlpha(1.0f);
            }

            dueDateTextView.setText(task.getDueDate());

            // Set star icon based on favorite status
            if (task.isFavorite()) {
                starImageView.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                starImageView.setImageResource(android.R.drawable.btn_star_big_off);
            }

            // Setup star click listener
            starImageView.setOnClickListener(v -> {
                if (starClickListener != null) {
                    starClickListener.onStarClick(position);
                }
            });
        }

        return convertView;
    }
}

