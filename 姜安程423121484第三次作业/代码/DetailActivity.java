import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework3.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDetail = findViewById(R.id.tvDetail);

        // 从Intent中获取Bundle数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("USER_NAME", "未知");
            int userAge = bundle.getInt("USER_AGE", 0);
            boolean isStudent = bundle.getBoolean("IS_STUDENT", false);

            String detailText = String.format(
                    "用户信息:\n\n姓名: %s\n年龄: %d\n学生: %s",
                    userName, userAge, isStudent ? "是" : "否"
            );

            tvDetail.setText(detailText);
        }
    }
}
