package com.example.fileinputoutput;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private Context context;
    private List<Record> recordList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Record record);
    }

    public RecordAdapter(Context context, List<Record> recordList, OnItemClickListener listener) {
        this.context = context;
        this.recordList = recordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.tvTitle.setText(record.getTitle());
        holder.tvTime.setText(record.getTime());
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(record);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}