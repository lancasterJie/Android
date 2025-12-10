package com.example.dialectgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.dialectgame.R;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.Follow;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;
import java.io.File;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<User> userList;
    private AppDatabase db;
    private int currentUserId;

    public UserListAdapter(List<User> userList, AppDatabase db) {
        this.userList = userList;
        this.db = db;
        this.currentUserId = UserManager.getInstance(null).getCurrentUser().getId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvNickname.setText(user.getNickname());
        holder.tvUsername.setText("用户名: " + user.getUsername());

        // 加载用户头像
        loadUserAvatar(user, holder.ivAvatar);

        if (user.getId() == currentUserId) {
            holder.btnFollow.setVisibility(View.GONE);
            return;
        }

        // 检查是否已关注
        boolean[] isFollowingArr = {
                db.followDao().isFollowing(currentUserId, user.getId())
        };
        updateFollowButton(holder.btnFollow, isFollowingArr[0]);

        // 关注/取消关注按钮点击事件
        holder.btnFollow.setOnClickListener(v -> {
            new Thread(() -> {
                if (isFollowingArr[0]) {
                    // 取消关注
                    Follow follow = new Follow(currentUserId, user.getId());
                    db.followDao().unfollow(follow);
                    isFollowingArr[0] = false;
                } else {
                    // 关注
                    Follow follow = new Follow(currentUserId, user.getId());
                    db.followDao().follow(follow);
                    isFollowingArr[0] = true;
                }

                // 更新UI
                holder.itemView.post(() ->
                        updateFollowButton(holder.btnFollow, isFollowingArr[0])
                );
            }).start();
        });
    }

    // 加载用户头像
    private void loadUserAvatar(User user, ImageView imageView) {
        String avatarPath = user.getAvatar();
        if (avatarPath != null && !avatarPath.isEmpty() && !"default_avatar".equals(avatarPath)) {
            Glide.with(imageView.getContext())
                    .load(new File(avatarPath))
                    .circleCrop() // 圆形裁剪
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_user);
        }
    }

    private void updateFollowButton(Button button, boolean isFollowing) {
        if (isFollowing) {
            button.setText("已关注");
            button.setBackgroundResource(R.drawable.btn_round_gray);
        } else {
            button.setText("关注");
            button.setBackgroundResource(R.drawable.btn_round_primary);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvNickname;
        TextView tvUsername;
        Button btnFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvUsername = itemView.findViewById(R.id.tv_username);
            btnFollow = itemView.findViewById(R.id.btn_follow);
        }
    }
}