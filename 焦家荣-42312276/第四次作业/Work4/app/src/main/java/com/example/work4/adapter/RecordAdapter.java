package com.example.work4.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.work4.R;
import com.example.work4.db.MyDbHelper;  // 确保这行存在

public class RecordAdapter extends CursorAdapter {
    public RecordAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textTitle = view.findViewById(R.id.textItemTitle);
        TextView textTime = view.findViewById(R.id.textItemTime);

        // 使用完整包名确保能找到
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE));
        String time = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME));

        textTitle.setText(title);
        textTime.setText(time);
    }
}