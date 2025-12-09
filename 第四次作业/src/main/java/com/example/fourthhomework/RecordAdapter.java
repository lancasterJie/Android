package com.example.fourthhomework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {
    private List<Record> recordList;
    private OnItemClickListener onItemClickListener; // 点击事件回调
    private OnItemLongClickListener onItemLongClickListener; // 长按事件回调

    // 构造方法：传入数据列表
    public RecordAdapter(List<Record> recordList) {
        this.recordList = recordList;
    }

    // 创建 ViewHolder（加载列表项布局）
    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(itemView);
    }

    // 绑定数据到 ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.tvTitle.setText(record.getTitle());
        holder.tvTime.setText(record.getTime());

        // 点击事件
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(record);
            }
        });

        // 长按事件
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(record);
                return true; // 消费事件，避免触发点击事件
            }
            return false;
        });
    }

    // 获取列表项数量
    @Override
    public int getItemCount() {
        return recordList.size();
    }

    // 更新列表数据（新增/删除后刷新）
    public void updateData(List<Record> newRecordList) {
        this.recordList = newRecordList;
        notifyDataSetChanged(); // 通知 RecyclerView 刷新
    }

    // ViewHolder：持有列表项控件引用
    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvTime = itemView.findViewById(R.id.tv_item_time);
        }
    }

    // 点击事件接口（给 Activity 回调）
    public interface OnItemClickListener {
        void onItemClick(Record record);
    }

    // 长按事件接口（给 Activity 回调）
    public interface OnItemLongClickListener {
        void onItemLongClick(Record record);
    }

    // 设置点击监听器
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // 设置长按监听器
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }
}