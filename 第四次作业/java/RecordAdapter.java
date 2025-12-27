package com.example.myproject4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class RecordAdapter extends BaseAdapter
{

    private Context context;
    private List<Record> recordList;
    private LayoutInflater inflater;

    public RecordAdapter(Context context, List<Record> recordList)
    {
        this.context = context;
        this.recordList = recordList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return recordList.size();
    }


    @Override
    public Object getItem(int position)
    {
        return recordList.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return recordList.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;


        if (convertView == null)
        {

            convertView = inflater.inflate(R.layout.item_record_list, parent, false);

            holder = new ViewHolder();

            holder.textTitle = convertView.findViewById(R.id.textTitle);
            holder.textTime = convertView.findViewById(R.id.textTime);


            convertView.setTag(holder);
        }
        else
        {

            holder = (ViewHolder) convertView.getTag();
        }


        Record record = recordList.get(position);


        holder.textTitle.setText(record.getTitle());
        holder.textTime.setText(record.getTime());

        return convertView;
    }


    static class ViewHolder {
        TextView textTitle;
        TextView textTime;
    }


    public void updateData(List<Record> newList) {
        this.recordList = newList;
        notifyDataSetChanged();
    }
}
