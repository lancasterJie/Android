package com.example.sql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView 适配器，展示记录的标题与时间。
 */
public class RecordListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<Record> data = new ArrayList<>();

    public RecordListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void submit(List<Record> records) {
        data.clear();
        if (records != null) {
            data.addAll(records);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Record getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_record, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Record record = getItem(position);
        holder.title.setText(record.getTitle());
        holder.time.setText(record.getTime());
        return convertView;
    }

    private static class ViewHolder {
        final TextView title;
        final TextView time;

        ViewHolder(View view) {
            title = view.findViewById(R.id.text_title);
            time = view.findViewById(R.id.text_time);
        }
    }
}

