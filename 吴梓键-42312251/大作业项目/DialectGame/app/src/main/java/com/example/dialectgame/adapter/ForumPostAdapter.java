// ForumPostAdapter.java
package com.example.dialectgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.R;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.model.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ViewHolder> {
    private List<ForumPost> postList;
    private OnPostClickListener listener;
    private AppDatabase db;

    public interface OnPostClickListener {
        void onPostClick(int postId);
    }

    public ForumPostAdapter(List<ForumPost> postList, OnPostClickListener listener, AppDatabase db) {
        this.postList = postList;
        this.listener = listener;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forum_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForumPost post = postList.get(position);
        User user = db.userDao().findById(post.getUserId());

        holder.tvTitle.setText(post.getTitle());
        holder.tvContent.setText(post.getContent());
        holder.tvAuthor.setText(user != null ? user.getNickname() : "匿名用户");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.tvTime.setText(sdf.format(new Date(post.getCreateTime())));

        holder.tvLikes.setText(String.valueOf(post.getLikeCount()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPostClick(post.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvAuthor, tvTime, tvLikes;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_post_title);
            tvContent = itemView.findViewById(R.id.tv_post_content);
            tvAuthor = itemView.findViewById(R.id.tv_post_author);
            tvTime = itemView.findViewById(R.id.tv_post_time);
            tvLikes = itemView.findViewById(R.id.tv_post_likes);
        }
    }
}