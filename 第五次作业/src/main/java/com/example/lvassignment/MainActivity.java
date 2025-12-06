package com.example.lvassignment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task>taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskList=new ArrayList<>();
        TaskAdapter adapter=new TaskAdapter(this,R.layout.custom_item_layout,taskList);
        ListView listView=findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        Button btnAdd=findViewById(R.id.add_bnt);
        EditText editText=findViewById(R.id.et_txt);

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editText.setHint("");
            } else {
                if (editText.getText().length() == 0) {
                    editText.setHint("请输入任务...");
                }
            }
        });

        btnAdd.setOnClickListener(v->{
            String title=editText.getText().toString();
            String time=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

            Task task=new Task(title,time);
            taskList.add(task);
            adapter.notifyDataSetChanged();
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            Task task = taskList.get(position);

            String[] menu = {"标记为重要 ⭐", "标记为完成 ✔", "删除任务 🗑"};

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("操作选项")
                    .setItems(menu, (dialog, which) -> {
                        switch (which) {
                            case 0: // 标星
                                task.setStar(!task.isStar());
                                break;

                            case 1: // 完成
                                task.setDone(!task.isDone());
                                break;

                            case 2: // 删除
                                taskList.remove(position);
                                break;
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .show();
            return true;
        });
    }
}