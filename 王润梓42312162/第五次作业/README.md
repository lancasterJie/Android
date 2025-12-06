# Android ListView待办事项清单应用实验报告

## 一、实验概述

### 1.1 实验目标
本实验旨在实现一个基于ListView的Android待办事项清单应用，通过实践掌握以下核心技能：
- ListView控件的基本使用和配置
- 自定义数据模型的创建
- 自定义适配器（Adapter）的实现
- Android界面与数据的绑定机制

### 1.2 应用功能
应用具备以下核心功能：
1. 展示任务列表，包含任务名称和截止日期
2. 支持基本的用户交互（点击和长按操作）
3. 任务状态的可视化表示

## 二、技术实现分析

### 2.1 架构设计

#### 2.1.1 三层架构
1. **数据层（Model）**：`TaskListAdapter.TaskData`类
2. **适配器层（Adapter）**：`TaskListAdapter`类
3. **视图层（View）**：`ThirdActivity`和布局文件

#### 2.1.2 类关系图
```
ThirdActivity
    ├── 持有MyDBHelper实例（数据库操作）
    ├── 持有TaskListAdapter实例（数据适配）
    ├── 管理ListView控件
    └── 处理用户输入和按钮点击
        │
        └── TaskListAdapter
            ├── 继承自ArrayAdapter<TaskData>
            ├── 负责数据与视图的绑定
            └── 处理列表项的交互事件
                │
                └── TaskData（内部类）
                    ├── title：任务标题
                    └── time：截止时间
```

### 2.2 核心组件实现

#### 2.2.1 数据模型（TaskData）
```java
public static class TaskData {
    private String title;  // 任务标题
    private String time;   // 截止时间
    
    // 构造函数、getter/setter方法
    public static TaskData newDefault() {
        return new TaskData("", "");
    }
}
```
**特点分析**：
- 封装了任务的基本属性
- 提供了默认实例创建方法
- 符合JavaBean规范，便于数据传递

#### 2.2.2 适配器实现（TaskListAdapter）
```java
public class TaskListAdapter extends ArrayAdapter<TaskListAdapter.TaskData> {
    public TaskListAdapter(@NonNull Context context, ArrayList<TaskData> list) {
        super(context, R.layout.task_item, list);
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // View复用机制
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                .inflate(R.layout.task_item, parent, false);
        }
        
        // 数据绑定
        TaskData data = getItem(position);
        TextView title = convertView.findViewById(R.id.title);
        TextView time = convertView.findViewById(R.id.time);
        title.setText(data.title);
        time.setText(data.time);
        
        return convertView;
    }
}
```

**关键技术点**：
1. **View复用机制**：通过判断`convertView`是否为null来复用已创建的视图，提高ListView性能
2. **数据绑定**：将TaskData对象的数据绑定到对应的视图控件
3. **布局引用**：使用`R.layout.task_item`引用自定义列表项布局

#### 2.2.3 交互功能实现

##### 点击交互（星标切换）
```java
convertView.setOnClickListener((v) -> {
    int visibility = star.getVisibility();
    if (visibility == View.VISIBLE) {
        star.setVisibility(View.INVISIBLE);
    } else {
        star.setVisibility(View.VISIBLE);
    }
});
```
**功能说明**：点击任务项切换星标图标显示/隐藏，用于标记重要任务

##### 长按交互（删除线切换）
```java
convertView.setOnLongClickListener((v) -> {
    int paint = title.getPaintFlags();
    if ((paint & Paint.STRIKE_THRU_TEXT_FLAG) != 0) {
        title.setPaintFlags(paint & (~Paint.STRIKE_THRU_TEXT_FLAG));
    } else {
        title.setPaintFlags(paint | Paint.STRIKE_THRU_TEXT_FLAG);
    }
    return true;
});
```
**功能说明**：长按任务项切换删除线效果，用于标记任务完成状态

### 2.3 主活动实现（ThirdActivity）

#### 2.3.1 初始化流程
```java
void initComponents() {
    // 1. 创建数据库助手
    MyDBHelper helper = new MyDBHelper(this);
    
    // 2. 获取任务列表数据
    ArrayList<TaskListAdapter.TaskData> taskDataList = helper.getTaskList();
    
    // 3. 创建适配器并绑定数据
    adapter = new TaskListAdapter(this, taskDataList);
    
    // 4. 设置ListView适配器
    taskList.setAdapter(adapter);
    
    // 5. 初始化默认数据（如果数据库为空）
    if (taskDataList.isEmpty()) {
        // 添加示例数据并保存到数据库
        taskDataList.add(new TaskListAdapter.TaskData("Complete Android Homework", "2025-12-20"));
        // ... 添加其他示例数据
        taskDataList.forEach(helper::addTaskData);
    }
    
    // 6. 通知适配器数据更新
    adapter.notifyDataSetChanged();
}
```

#### 2.3.2 数据持久化
- 使用`MyDBHelper`进行SQLite数据库操作
- 应用启动时从数据库加载数据
- 新增任务时同时更新内存列表和数据库

## 三、界面设计分析

### 3.1 列表项布局（task_item.xml）
根据代码推断，布局应包含以下元素：
- 至少两个TextView：用于显示任务标题和截止时间
- 一个ImageView：用于显示星标图标（R.id.star）
- 适当的布局容器（如LinearLayout或ConstraintLayout）

### 3.2 主界面布局（activity_third.xml）
主要包含：
- ListView控件（R.id.task_list）
- 输入区域：两个EditText（任务和截止时间）
- 操作按钮：保存和返回按钮

## 四、实验亮点

### 4.1 技术实现亮点
1. **完整的MVC模式**：清晰的数据-视图-控制器分离
2. **性能优化**：实现了ListView的View复用机制
3. **交互丰富**：支持点击和长按两种交互方式
4. **数据持久化**：集成了SQLite数据库存储

### 4.2 用户体验亮点
1. **直观的状态标记**：通过星标和删除线清晰表示任务状态
2. **示例数据引导**：首次使用时有默认数据引导
3. **实时反馈**：操作后立即更新界面显示

## 五、存在的问题与改进建议

### 5.1 存在的问题
1. **数据库设计问题**：
   - `MyDBHelper`中表名不一致（创建的是tasks，删除的是records）
   - 数据字段设计不合理，title字段冗余

2. **功能完整性**：
   - 缺少任务删除功能
   - 缺少任务编辑功能
   - 任务状态变更未保存到数据库

3. **用户体验**：
   - 缺少数据验证（如日期格式验证）
   - 缺少空状态提示

### 5.2 改进建议

#### 5.2.1 数据库优化
```java
// 修正onUpgrade方法
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS tasks"); // 修正表名
    onCreate(db);
}

// 优化数据模型
public class Task {
    private int id;
    private String content;    // 任务内容
    private String dueDate;    // 截止日期
    private boolean isStarred; // 是否星标
    private boolean isCompleted; // 是否完成
    // ... getter/setter方法
}
```

## 六、实验总结

### 6.1 目标达成情况
- ✓ 成功实现ListView展示任务列表
- ✓ 完成自定义适配器的开发
- ✓ 实现基本的数据模型和绑定
- ✓ 提供基本的用户交互功能
- ✓ 集成数据库持久化存储

### 6.2 技术掌握情况
通过本实验，掌握了以下Android开发技能：
1. **ListView控件的全面使用**
2. **自定义适配器的实现方法**
3. **数据模型的设计原则**
4. **视图与数据的绑定技术**
5. **基本的用户交互处理**
6. **SQLite数据库集成**
