package com.example.expriment04;

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

public class TaskListAdapter extends ArrayAdapter<TaskListAdapter.TaskData> {
    public static class TaskData {
        private String title;
        private String time;
        TaskData(String title, String time) {
            this.title = title;
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
        public static TaskData newDefault() {
            return new TaskData("", "");
        }
    }

    public TaskListAdapter(@NonNull Context context, ArrayList<TaskData> list) {
        super(context, R.layout.task_item, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TaskData data = getItem(position);
        if (data == null) {
            data = TaskData.newDefault();
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        ImageView star = convertView.findViewById(R.id.star);
        TextView title = convertView.findViewById(R.id.title),
                time = convertView.findViewById(R.id.time);
        title.setText(data.title);
        time.setText(data.time);
        convertView.setOnClickListener((v) -> {
            int visibility = star.getVisibility();
            if (visibility == View.VISIBLE) {
                star.setVisibility(View.INVISIBLE);
            }
            else {
                star.setVisibility(View.VISIBLE);
            }
        });
        convertView.setOnLongClickListener((v) -> {
            int paint = title.getPaintFlags();
            if ((paint & Paint.STRIKE_THRU_TEXT_FLAG) != 0) {
                title.setPaintFlags(paint & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
            else {
                title.setPaintFlags(paint | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            return true;
        });
        return convertView;
    }
}
