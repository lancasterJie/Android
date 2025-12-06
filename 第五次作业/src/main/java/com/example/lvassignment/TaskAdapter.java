package com.example.lvassignment;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private int resourceID;
    public TaskAdapter(Context ctx, int resourceID, List<Task> tasks){
        super(ctx,resourceID,tasks);
        this.resourceID=resourceID;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Task task=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView star=view.findViewById(R.id.img_star);
        TextView title=view.findViewById(R.id.txt_title);
        TextView time=view.findViewById(R.id.txt_time);

        title.setText(task.getTitle());
        time.setText(task.getTime());

        if(task.isStar())star.setVisibility(View.VISIBLE);
        else star.setVisibility(View.INVISIBLE);

        if(task.isDone()){
            title.setPaintFlags(title.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        return view;
    }
}
