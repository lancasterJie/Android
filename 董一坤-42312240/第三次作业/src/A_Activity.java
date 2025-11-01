package com.dyk.assignments.assignment_4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dyk.assignments.R;
import com.dyk.assignments.assignment_2.FirstActivity;

public class A_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int HOME_FRAGMENT_REQUEST_CODE = 1;
    private EditText et_age;
    private EditText et_user_name;
    private RadioButton rb_stu;
    private static final String TAG = "DYK";
    private TextView tv_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_a);

        et_user_name = findViewById(R.id.et_user_name);
        et_age = findViewById(R.id.et_age);
        rb_stu = findViewById(R.id.rb_stu);
        tv_res = findViewById(R.id.tv_res);

        findViewById(R.id.btn_switch_to_detail_activity).setOnClickListener(this);
        findViewById(R.id.btn_switch_to_home_fragment).setOnClickListener(this);
        findViewById(R.id.btn_switch_to_fragments).setOnClickListener(this);

        if(savedInstanceState != null){
            String user_name = savedInstanceState.getString("user_name");
            String age = savedInstanceState.getString("age");
            boolean is_stu = savedInstanceState.getBoolean("is_stu");
            String desc = "你在这个应用的用户名为：" + user_name + "\n 年龄为：" + age + "是否为学生：" + (is_stu?"是":"否");

            tv_res.setVisibility(View.VISIBLE);
            tv_res.setText(desc);

            Log.d(TAG, "onCreate: 屏幕已经旋转了");
        }
    }

    @Override
    public void onClick(View v) {

        String user_name = et_user_name.getText().toString();
        String age = et_age.getText().toString();
        boolean is_stu = rb_stu.isChecked();

        Bundle info = new Bundle();
        info.putString("user_name",user_name);
        info.putString("age",age);
        info.putBoolean("is_stu",is_stu);

        int v_id = v.getId();
        if(v_id == R.id.btn_switch_to_detail_activity){
            Intent intent = new Intent(this,B_Activity.class);
            intent.putExtra("info",info);
            startActivity(intent);
        }else if(v_id == R.id.btn_switch_to_home_fragment){
            Intent intent = new Intent(this, FirstMainActivity.class);
            intent.putExtra("info",info);
            startActivityForResult(intent,HOME_FRAGMENT_REQUEST_CODE);
        }else if(v_id == R.id.btn_switch_to_fragments){
            Intent intent = new Intent(this,FirstMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HOME_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("final_result");
            tv_res.setVisibility(View.VISIBLE);
            tv_res.setText(result);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("user_name",et_user_name.getText().toString());
        outState.putString("age",et_age.getText().toString());
        outState.putBoolean("is_stu",rb_stu.isChecked());

        Log.d(TAG, "onSaveInstanceState: 保存屏幕旋转前的内容");

    }
}