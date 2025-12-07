# Android本地数据持久化综合实验报告

---
姓名：张明玉
学号：42312196
实验名称：Android本地数据持久化综合实验报告
---

## 一、实验概述

### 1.1 实验目标
本实验通过实现一个Android应用，综合运用了三种本地数据持久化技术：
- 文件读写（File I/O）
- SharedPreferences配置存储
- SQLite数据库操作

### 1.2 应用功能
应用包含三个主要模块：
1. **主界面**：文件读写功能，文本编辑与保存
2. **设置界面**：用户账户信息存储与自动保存功能
3. **任务列表界面**：SQLite数据库的增查操作和任务管理

## 二、各模块实现分析

### 2.1 文件读写模块（MainActivity）

#### 2.1.1 核心功能
- **保存文件**：将EditText中的内容保存到`note.txt`文件
- **加载文件**：从`note.txt`读取内容并显示在EditText中

#### 2.1.2 关键代码
```java
// 保存文件
FileOutputStream fileOutputStream = openFileOutput("note.txt", MODE_PRIVATE);
fileOutputStream.write(fileContent.getText().toString().getBytes());
fileOutputStream.close();

// 加载文件（适配不同Android版本）
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    byte[] bytes = fileInputStream.readAllBytes();
    fileContent.setText(new String(bytes));
}
```

#### 2.1.3 存在的问题
1. **异常处理不完善**：仅使用try-catch捕获IOException，缺少文件不存在的提示
2. **版本兼容性**：低版本Android没有`readAllBytes()`方法，可能导致崩溃

### 2.2 SharedPreferences模块（SecondActivity）

#### 2.2.1 核心功能
- 保存账户和密码信息
- 通过CheckBox控制是否保存
- 在主界面恢复保存的数据

#### 2.2.2 关键代码
```java
// 保存配置
SharedPreferences.Editor editor = sharedPreferences.edit();
if (isSave.isChecked()) {
    editor.putString("account", account.getText().toString());
    editor.putString("password", password.getText().toString());
    editor.putBoolean("is_save", true);
    editor.apply();
}

// 读取配置（MainActivity的onResume中）
boolean isSave = sharedPreferences.getBoolean("is_save", false);
if (isSave) {
    title.setText(sharedPreferences.getString("account", title.getText().toString()));
}
```

#### 2.2.3 存在的问题
1. **密码明文存储**：密码以明文形式存储在SharedPreferences中，存在安全隐患
2. **UI一致性**：只恢复了账户名，未恢复密码显示

### 2.3 SQLite数据库模块（ThirdActivity）

#### 2.3.1 数据库设计
表名：`tasks`
字段：
- `_id`：INTEGER PRIMARY KEY AUTOINCREMENT
- `title`：TEXT
- `content`：TEXT
- `time`：TEXT

#### 2.3.2 核心功能
1. **初始化数据**：如果数据库为空，添加4条默认任务
2. **添加任务**：通过界面输入新增任务到数据库
3. **显示任务**：使用ListView和自定义Adapter展示任务列表

#### 2.3.3 关键代码
```java
// 数据库创建
public void onCreate(SQLiteDatabase db) {
    String sql = "CREATE TABLE tasks (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT," +
            "content TEXT," +
            "time TEXT)";
    db.execSQL(sql);
}

// 获取任务列表
public ArrayList<TaskListAdapter.TaskData> getTaskList() {
    ArrayList<TaskListAdapter.TaskData> taskDataList = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();
    Cursor tasks = db.query("tasks", new String[]{"content", "time"}, 
                          null, null, null, null, null);
    // ... 处理Cursor数据
    return taskDataList;
}
```

#### 2.3.4 存在的问题
1. **数据冗余**：title字段实际上是content的第一个字符，设计不合理
2. **字段获取不完整**：`getTaskList()`只获取了content和time，缺少id和title
3. **逻辑错误**：在`addTaskData()`方法中，标题生成逻辑有误，可能导致乱码

## 三、界面交互设计

### 3.1 布局结构
应用采用三Activity架构：
1. **MainActivity**：主界面，包含文件操作和导航
2. **SecondActivity**：设置界面
3. **ThirdActivity**：任务管理界面

### 3.2 交互特性
1. **任务列表交互**：
   - 点击切换星标状态
   - 长按切换删除线效果
2. **数据持久化**：各模块数据独立存储，互不干扰

## 四、实验存在的问题与改进建议

### 4.1 代码问题
1. **MyDBHelper.java**：
   - `onUpgrade()`方法中删除了`records`表，但应用中使用的是`tasks`表
   - 标题生成逻辑：`title = new String(content.getBytes(),0,1);` 可能导致乱码

2. **MainActivity.java**：
   - 缺少文件不存在的异常处理
   - 低版本Android兼容性问题

3. **SecondActivity.java**：
   - 密码明文存储，应使用加密存储

### 4.2 功能缺陷
1. **数据库功能不完整**：缺少删除、修改功能
2. **错误处理不足**：多处缺少合适的错误提示
3. **数据验证缺失**：输入数据没有进行有效性验证

## 五、实验总结

本次实验成功实现了Android本地数据持久化的三种主要方式，涵盖了：
1. **文件存储**：用于存储文本内容
2. **SharedPreferences**：用于存储用户配置
3. **SQLite数据库**：用于结构化数据存储

虽然实现中存在一些问题和不足，但整体框架完整，各功能模块基本可用。通过本次实验，加深了对Android数据持久化机制的理解，掌握了相关API的使用方法。
