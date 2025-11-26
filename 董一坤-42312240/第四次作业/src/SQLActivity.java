package com.dyk.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dyk.homework.entity.MyEntity;
import com.dyk.homework.utils.DBHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_content;
    private EditText et_title;
    private EditText et_id;
    private TextView tv_show;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlactivity);


        initView();
        setListener();
        loadData();
    }

    private void loadData() {
        List<MyEntity> list = db.queryAll();

        if(list == null){
            tv_show.setText("数据库内容为空！");
            return;
        }
        StringBuilder show = new StringBuilder();
        for(MyEntity obj : list){
            show.append(obj.toString()).append("------------------------------\n");
        }
        tv_show.setText(show.toString());
    }

    private void setListener() {
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_update_by_id).setOnClickListener(this);
        findViewById(R.id.btn_delete_by_id).setOnClickListener(this);
        findViewById(R.id.btn_view_by_id).setOnClickListener(this);
        findViewById(R.id.btn_delete_all).setOnClickListener(this);
    }

    private void initView() {
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        et_id = findViewById(R.id.et_id);
        tv_show = findViewById(R.id.tv_show);
        db = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        int v_id = v.getId();

        String ID = et_id.getText().toString();
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String currentTime = now.format(formatter);

        if(v_id == R.id.btn_add){

            if(content.isEmpty()){
                Toast.makeText(this, "文章内容不得为空!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(title.isEmpty()){
                title = content.substring(0,Math.min(content.length(),20));
            }
            MyEntity obj = new MyEntity(title,content,currentTime);

            long insertID = db.insert(obj);

            Toast.makeText(this, "插入成功"+insertID, Toast.LENGTH_SHORT).show();
            clearInputFields();
            loadData();

        }else if(v_id == R.id.btn_delete_by_id){
            if(ID.isEmpty()){
                Toast.makeText(this, "请输入要查询的ID", Toast.LENGTH_SHORT).show();
                return;
            }
            long id = Long.parseLong(ID);
            long deleteCount = db.delete(id);

            Toast.makeText(this, "成功删除，影响行数："+deleteCount, Toast.LENGTH_SHORT).show();
            clearInputFields();
            loadData();
        }else if(v_id == R.id.btn_view_by_id){
            if(ID.isEmpty()){
                Toast.makeText(this, "请输入要查询的ID", Toast.LENGTH_SHORT).show();
                return;
            }
            long id = Long.parseLong(ID);
            MyEntity obj = db.queryById(id);

            if(obj == null){
                Toast.makeText(this, "该id对应的实体不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            et_title.setText(obj.getTitle());
            et_content.setText(obj.getContent());

        }else if(v_id == R.id.btn_update_by_id){
            if(ID.isEmpty()){
                Toast.makeText(this, "请输入要查询的ID", Toast.LENGTH_SHORT).show();
                return;
            }
            long id = Long.parseLong(ID);
            int upgradeCount = db.upgrade(new MyEntity(id, title, content, currentTime));

            Toast.makeText(this, "成功更新的行数"+upgradeCount, Toast.LENGTH_SHORT).show();
            clearInputFields();
            loadData();
        }else if(v_id == R.id.btn_delete_all){
            long deleteCount = db.deleteAll();
            Toast.makeText(this, "成功删除所有条目，影响行数为："+deleteCount, Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    public void clearInputFields(){
        et_id.setText("");
        et_title.setText("");
        et_content.setText("");
    }
}