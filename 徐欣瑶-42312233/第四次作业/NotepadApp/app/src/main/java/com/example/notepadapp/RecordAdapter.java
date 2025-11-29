package com.example.notepadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {
    private Context context;
    private List<Record> recordList;

    public RecordAdapter(Context context, List<Record> recordList) {
        super(context, 0, recordList);
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        }

        Record record = recordList.get(position);

        TextView textViewTitle = convertView.findViewById(R.id.textViewItemTitle);
        TextView textViewTime = convertView.findViewById(R.id.textViewItemTime);

        textViewTitle.setText(record.getTitle());
        textViewTime.setText(record.getTime());

        return convertView;
    }
}