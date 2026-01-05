Android ListView 作业：待办事项清单 (To-Do List App)
项目概述

创建一个简单的 Android 待办事项清单应用，使用 ListView 展示任务列表。用户可以看到任务列表（包括任务名称和截止日期），并支持基本的交互功能。

功能要求

1. 主界面布局 (MainActivity)
使用 ListView 作为任务列表容器
每个列表项显示：
任务名称（如："Complete Android Homework"）
截止日期（如："2025-12-20"）
列表项之间有清晰分隔线

2. 数据模型
创建 TaskItem 类，包含：
taskName: String
dueDate: String

3. 列表适配器
自定义 ArrayAdapter 或 BaseAdapter
自定义列表项布局（至少两个 TextView）
正确绑定数据到视图

4. 示例数据

java
ArrayList<TaskItem> tasks = new ArrayList<>();
tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
tasks.add(new TaskItem("Call John", "2024-04-23"));

完整实现
1. 数据模型：TaskItem.java

java
package com.example.todolist;

public class TaskItem {
private String taskName;
private String dueDate;

public TaskItem(String taskName, String dueDate) {
this.taskName = taskName;
this.dueDate = dueDate;
}

public String getTaskName() { return taskName; }
public String getDueDate() { return dueDate; }
}

2. 列表项布局：res/layout/list_item_task.xml

xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical"
android:padding="16dp"
android:background="?android:attr/selectableItemBackground">

<TextView
android:id="@+id/tvTaskName"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:textSize="18sp"
android:textStyle="bold"
android:textColor="@android:color/black" />

<TextView
android:id="@+id/tvDueDate"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:textSize="14sp"
android:textColor="@android:color/darker_gray"
android:layout_marginTop="4dp" />

</LinearLayout>

3. 自定义适配器：TaskAdapter.java

java
package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
super(context, 0, tasks);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
if (convertView == null) {
convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
}

TaskItem task = getItem(position);
TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);
TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);

tvTaskName.setText(task.getTaskName());
tvDueDate.setText(task.getDueDate());

return convertView;
}
}

4. 主界面布局：res/layout/activity_main.xml

xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical"
android:padding="16dp"
android:background="#f5f5f5">

<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="horizontal"
android:gravity="center_vertical">

<EditText
android:id="@+id/etKeyword"
android:layout_width="0dp"
android:layout_height="wrap_content"
android:layout_weight="1"
android:hint="请输入关键词"
android:padding="12dp"
android:background="@drawable/edittext_rounded"
android:inputType="text" />

<Button
android:id="@+id/btnAddTask"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Add Task"
android:layout_marginStart="8dp"
android:padding="12dp"
android:textSize="16sp" />

</LinearLayout>

<ListView
android:id="@+id/lvTasks"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:layout_marginTop="16dp"
android:divider="@android:color/darker_gray"
android:dividerHeight="1dp" />

</LinearLayout>
可选：创建圆角输入框背景 res/drawable/edittext_rounded.xml：
xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
android:shape="rectangle">
<solid android:color="#ffffff" />
<corners android:radius="8dp" />
<stroke android:width="1dp" android:color="#cccccc" />
</shape>

5. 主活动：MainActivity.java

java
package com.example.todolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

private ListView lvTasks;
private EditText etKeyword;
private Button btnAddTask;
private ArrayList<TaskItem> tasks;
private TaskAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);

lvTasks = findViewById(R.id.lvTasks);
etKeyword = findViewById(R.id.etKeyword);
btnAddTask = findViewById(R.id.btnAddTask);

tasks = new ArrayList<>();
tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
tasks.add(new TaskItem("Call John", "2024-04-23"));

adapter = new TaskAdapter(this, tasks);
lvTasks.setAdapter(adapter);

btnAddTask.setOnClickListener(v -> {
String taskName = etKeyword.getText().toString().trim();
if (!taskName.isEmpty()) {
tasks.add(new TaskItem(taskName, "2025-01-01"));
adapter.notifyDataSetChanged();
etKeyword.setText("");
}
});
}
}
