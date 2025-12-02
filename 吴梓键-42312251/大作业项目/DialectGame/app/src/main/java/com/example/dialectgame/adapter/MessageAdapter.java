package com.example.dialectgame.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dialectgame.R;
import com.example.dialectgame.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView content, timestamp;

        public ViewHolder(View v) {
            super(v);
            content = v.findViewById(R.id.message_content);
            timestamp = v.findViewById(R.id.timestamp);
        }
    }

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isUser() ? 0 : 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = viewType == 0 ? R.layout.user_message : R.layout.bot_message;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.timestamp.setText(msg.getTimestamp());

        if (!msg.isComplete() && !msg.isUser()) {
            SpannableString spannable = new SpannableString(msg.getContent() + "|");
            spannable.setSpan(new ForegroundColorSpan(Color.TRANSPARENT),
                    spannable.length() - 1, spannable.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.content.setText(spannable);
        } else {
            holder.content.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}