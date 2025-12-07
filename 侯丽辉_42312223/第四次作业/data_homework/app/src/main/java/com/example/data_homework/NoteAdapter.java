package com.example.data_homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<DatabaseHelper.Note> noteList;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<DatabaseHelper.Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return noteList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_note, parent, false);
            holder = new ViewHolder();
            holder.textTitle = convertView.findViewById(R.id.text_title);
            holder.textTime = convertView.findViewById(R.id.text_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DatabaseHelper.Note note = noteList.get(position);
        holder.textTitle.setText(note.getTitle());
        holder.textTime.setText(formatDate(note.getTimestamp()));

        return convertView;
    }

    private String formatDate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return timestamp;
        }
    }

    static class ViewHolder {
        TextView textTitle;
        TextView textTime;
    }
}
