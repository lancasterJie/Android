package com.dyk.homework;

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

import java.util.List;

public class ToDoAdapter extends ArrayAdapter {
    private int resourceID;
    private List<ToDoItem> resourceData;

    public ToDoAdapter(@NonNull Context context, int resource, @NonNull List<ToDoItem> objects) {
        super(context, resource, objects);
        resourceID = resource;
        resourceData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ToDoItem item = (ToDoItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        ImageView start_image = view.findViewById(R.id.start_image);
        TextView task_content = view.findViewById(R.id.task_content);
        TextView task_date = view.findViewById(R.id.task_date);

        if(item.isImportance()){
            start_image.setImageResource(ToDoItem.STAR_IMAGE_ID);
        }else{
            start_image.setImageResource(ToDoItem.EMPTY_IMAGE_ID);
        }

        if(item.isDone()){
            // 设置删除线
            task_content.setPaintFlags(task_content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        task_content.setText(item.getTaskContent());
        task_date.setText(item.getTaskDate());

        return view;
    }

    public void updateData(List<ToDoItem> newData) {
        this.resourceData.clear();
        if (newData != null) {
            this.resourceData.addAll(newData);
        }
        notifyDataSetChanged(); // 👈 关键：通知 ListView 刷新
    }
}
