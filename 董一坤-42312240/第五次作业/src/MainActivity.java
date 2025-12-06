package com.dyk.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_NEW_TASK_CODE = 1;
    private static final int REQUEST_VEW_TASK_CODE = 2;
    private ListView to_do_list_view;
    private EditText et_input;
    private Button add_task_button;
    private MyDBHelper db;
    private Button search;
    private List<ToDoItem> to_do_list;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initView();
        setListener();

        loadData();
    }

    private void loadData() {
        this.to_do_list = db.queryAll();
        adapter = new ToDoAdapter(this,R.layout.to_to_item,this.to_do_list);
        this.to_do_list_view.setAdapter(adapter);
    }

    private void setListener() {
        to_do_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 设置条目的点击事件
                ToDoItem item = to_do_list.get(position);
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("requestCode",REQUEST_VEW_TASK_CODE);
                bundle.putString("taskContent",item.getTaskContent());
                bundle.putBoolean("isDone",item.isDone());
                bundle.putBoolean("isImportant",item.isImportance());
                bundle.putString("task_date",item.getTaskDate());
                bundle.putLong("ID",item.getId());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        add_task_button.setOnClickListener(v->{
            // 页面的跳转
            Intent intent = new Intent(this, TaskDetailActivity.class);
            intent.putExtra("requestCode", REQUEST_ADD_NEW_TASK_CODE);
            startActivity(intent);
        });

        search.setOnClickListener(v->{
            // 按条件搜索
            String key = et_input.getText().toString();
            if(key.isEmpty()){
                loadData();
                return;
            }
            this.to_do_list = db.queryByKeyword(key);
            adapter.updateData(this.to_do_list);
        });
    }

    private void initView() {
        to_do_list_view = findViewById(R.id.to_do_list);
        et_input = findViewById(R.id.input_key);
        add_task_button = findViewById(R.id.add_task_button);
        search = findViewById(R.id.search);
        db = new MyDBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}