package com.example.test3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecordListAdapter extends ArrayAdapter<Record> {

    public RecordListAdapter(@NonNull Context context, @NonNull List<Record> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_record, parent, false);
        }

        Record record = getItem(position);
        TextView titleView = view.findViewById(R.id.textRecordTitle);
        TextView timeView = view.findViewById(R.id.textRecordTime);

        if (record != null) {
            titleView.setText(record.getTitle());
            timeView.setText(record.getTime());
        }

        return view;
    }
}

