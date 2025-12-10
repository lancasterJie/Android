package com.example.dialectgame;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.adapter.UserListAdapter;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.Follow;
import com.example.dialectgame.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    public static final String TYPE = "TYPE";
    public static final String USER_ID = "USER_ID";
    public static final String TYPE_FOLLOWING = "following";
    public static final String TYPE_FOLLOWERS = "followers";

    private RecyclerView rvUserList;
    private TextView tvTitle;
    private AppDatabase db;
    private String type;
    private int userId;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        db = AppDatabase.getInstance(this);
        type = getIntent().getStringExtra(TYPE);
        userId = getIntent().getIntExtra(USER_ID, -1);

        if (userId == -1 || type == null) {
            finish();
            return;
        }

        initViews();
        loadUserList();
    }

    private void initViews() {
        rvUserList = findViewById(R.id.rv_user_list);
        tvTitle = findViewById(R.id.tv_title);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));

        // 设置标题
        if (TYPE_FOLLOWING.equals(type)) {
            tvTitle.setText("我的关注");
        } else {
            tvTitle.setText("我的粉丝");
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void loadUserList() {
        new Thread(() -> {
            List<User> userList = new ArrayList<>();

            if (TYPE_FOLLOWING.equals(type)) {
                // 获取关注列表
                List<Follow> follows = db.followDao().getFollowing(userId);
                for (Follow follow : follows) {
                    User user = db.userDao().findById(follow.getFollowedUserId());
                    if (user != null) {
                        userList.add(user);
                    }
                }
            } else {
                // 获取粉丝列表
                List<Follow> followers = db.followDao().getFollowers(userId);
                for (Follow follower : followers) {
                    User user = db.userDao().findById(follower.getUserId());
                    if (user != null) {
                        userList.add(user);
                    }
                }
            }

            runOnUiThread(() -> {
                adapter = new UserListAdapter(userList, db);
                rvUserList.setAdapter(adapter);
            });
        }).start();
    }
}