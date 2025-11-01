package com.dyk.assignments.assignment_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dyk.assignments.R;

import org.w3c.dom.Text;

public class B_Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_b);

        TextView tv_info = findViewById(R.id.tv_info);
        findViewById(R.id.back).setOnClickListener(this);

        set_text(tv_info);
    }

    private void set_text(TextView tv_info) {
        Bundle info = getIntent().getBundleExtra("info");
        String show_text = null;
        if(info != null){
            String user_name = info.getString("user_name");
            String age = info.getString("age");
            boolean is_stu = info.getBoolean("is_stu");

            show_text = "根据你填写的信息可知：\n" + "你在本应用上的用户名为：" + user_name + "\n 你的年龄为：" + age + "\n 你是否为一名学生：" + (is_stu?"是":"否");
        }else {
            show_text = "什么数据也没有传递过来";
        }

        tv_info.setText(show_text);
    }

    @Override
    public void onClick(View v) {
        int v_id = v.getId();
        if(v_id == R.id.back){
            finish();
        }
    }
}