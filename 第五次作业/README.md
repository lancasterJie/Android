# Android ListView Todo App

## MainActivity（数据填充与绑定）
文件：`app/src/main/java/com/example/to_dolistapp/MainActivity.java`

```java
public class MainActivity extends AppCompatActivity {

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

		ArrayList<TaskItem> tasks = new ArrayList<>();
		tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
		tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
		tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
		tasks.add(new TaskItem("Call John", "2024-04-23"));

		ListView listView = findViewById(R.id.lv_tasks);
		TaskAdapter adapter = new TaskAdapter(this, 0, tasks);
		listView.setAdapter(adapter);
	}
}
```

说明：初始化示例任务列表，实例化 `TaskAdapter`，并绑定到 `ListView` 展示。

## TaskItem（数据模型）
文件：`app/src/main/java/com/example/to_dolistapp/TaskItem.java`

```java
public class TaskItem {
	private String taskName;
	private String dueDate;

	public TaskItem(String taskName, String dueDate){
		this.taskName = taskName;
		this.dueDate = dueDate;
	}

	public String getTaskName(){
		return taskName;
	}

	public String getDueDate(){
		return dueDate;
	}
}
```

说明：封装任务名称与截止日期两个字段的简单 POJO。

## TaskAdapter（自定义 ArrayAdapter）
文件：`app/src/main/java/com/example/to_dolistapp/TaskAdapter.java`

```java
public class TaskAdapter extends ArrayAdapter<TaskItem> {
	public TaskAdapter(Context context, int resource, ArrayList<TaskItem> tasks){
		super(context, resource, tasks);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		TaskItem task = getItem(position);

		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
		}

		TextView tvTaskName = convertView.findViewById(R.id.tv_task_name);
		TextView tvDueDate = convertView.findViewById(R.id.tv_due_date);

		if (task != null) {
			tvTaskName.setText(task.getTaskName());
			tvDueDate.setText(task.getDueDate());
		}

		return convertView;
	}
}
```

说明：从数据集中取出 `TaskItem`，填充到自定义行布局的两个 TextView 中。

## 布局文件

### activity_main.xml（主界面容器）
文件：`app/src/main/res/layout/activity_main.xml`

```xml
<LinearLayout ...>
	<LinearLayout ...>
		<EditText
			android:id="@+id/et_search"
			... />
		<Button
			android:id="@+id/btn_add_task"
			... />
	</LinearLayout>

	<ListView
		android:id="@+id/lv_tasks"
		... />
</LinearLayout>
```

说明：上方为搜索/新增占位区，下方为填满屏幕的 `ListView`。

### list_item_task.xml（单行布局）
文件：`app/src/main/res/layout/list_item_task.xml`

```xml
<LinearLayout ...>
	<TextView
		android:id="@+id/tv_task_name"
		... />
	<TextView
		android:id="@+id/tv_due_date"
		... />
</LinearLayout>
```

说明：垂直两行，第一行任务名（加粗），第二行截止日期。
