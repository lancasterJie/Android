# Android 本地数据持久化综合实验

## 项目概述
这是一个完整的Android记事本应用，实现了三种本地数据存储方式：
1. **文件存储** - 使用FileInputStream/FileOutputStream
2. **SharedPreferences** - 保存用户设置（账户、密码、自动保存选项）
3. **SQLite数据库** - 管理多条笔记记录

## 功能实现

### 1. 文件读写模块 (MainActivity)
- ✅ 多行文本输入框（EditText）
- ✅ 保存到文件按钮 - 将内容写入`note.txt`
- ✅ 从文件加载按钮 - 从`note.txt`读取内容
- ✅ 异常处理和Toast提示
- ✅ 使用`openFileOutput()`和`openFileInput()`

**关键代码位置：**
- `MainActivity.java` - `saveToFile()` 和 `loadFromFile()` 方法

### 2. 设置模块 (SettingsActivity)
- ✅ CheckBox - 自动保存开关
- ✅ 用户名输入框
- ✅ 密码输入框
- ✅ 保存设置按钮
- ✅ 使用`getSharedPreferences("settings", MODE_PRIVATE)`
- ✅ 自动加载之前保存的配置

**关键代码位置：**
- `SettingsActivity.java` - `loadSettings()` 和 `saveSettings()` 方法
- `MainActivity.java` - `onResume()` 中更新标题栏显示用户名
- `MainActivity.java` - `onPause()` 中根据auto_save设置自动保存

### 3. 数据库模块 (SQLite)

#### DatabaseHelper.java
- ✅ 继承自SQLiteOpenHelper
- ✅ 数据库表结构：
  - `_id` - 整数主键，自增
  - `title` - 文本
  - `content` - 文本
  - `timestamp` - 时间戳
- ✅ CRUD操作：
  - `insertNote()` - 新增记录
  - `getAllNotes()` - 查询所有记录
  - `getNote()` - 查询单条记录
  - `updateNote()` - 更新记录
  - `deleteNote()` - 删除记录

#### RecordListActivity
- ✅ ListView显示所有笔记
- ✅ 显示标题和时间
- ✅ 点击列表项查看详情
- ✅ 长按列表项删除记录
- ✅ 空列表提示

#### RecordDetailActivity
- ✅ 查看笔记详细内容
- ✅ 编辑标题和内容
- ✅ 保存修改
- ✅ 删除笔记（带确认对话框）

## 界面设计

### 三个主要Activity
1. **MainActivity** - 主界面
   - 文本编辑区域
   - 文件读写按钮
   - 保存为新笔记按钮
   - 工具栏菜单（设置、笔记列表）

2. **SettingsActivity** - 设置界面
   - 自动保存开关
   - 用户名和密码输入
   - 保存设置按钮

3. **RecordListActivity** - 笔记列表
   - ListView展示所有笔记
   - 点击查看详情
   - 长按删除

4. **RecordDetailActivity** - 笔记详情
   - 编辑标题和内容
   - 保存和删除按钮

## 核心技术点

### 1. 文件存储示例
```java
// 保存
FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
fos.write(text.getBytes());
fos.close();

// 读取
FileInputStream fis = openFileInput("note.txt");
byte[] buffer = new byte[fis.available()];
fis.read(buffer);
String text = new String(buffer);
fis.close();
```

### 2. SharedPreferences示例
```java
// 写入
SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
SharedPreferences.Editor editor = sp.edit();
editor.putBoolean("auto_save", true);
editor.putString("user_name", "username");
editor.apply();

// 读取
boolean autoSave = sp.getBoolean("auto_save", false);
String userName = sp.getString("user_name", "");
```

### 3. SQLite数据库示例
```java
// 创建表
String sql = "CREATE TABLE notes (" +
    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
    "title TEXT," +
    "content TEXT," +
    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

// 插入
ContentValues values = new ContentValues();
values.put("title", title);
values.put("content", content);
long id = db.insert("notes", null, values);

// 查询
Cursor cursor = db.query("notes", null, null, null, null, null, "timestamp DESC");
```

## 项目结构
```
app/src/main/
├── java/com/example/data_homework/
│   ├── MainActivity.java              # 主界面
│   ├── SettingsActivity.java          # 设置界面
│   ├── RecordListActivity.java        # 笔记列表
│   ├── RecordDetailActivity.java      # 笔记详情
│   ├── DatabaseHelper.java            # 数据库助手
│   └── NoteAdapter.java               # ListView适配器
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_settings.xml
│   │   ├── activity_record_list.xml
│   │   ├── activity_record_detail.xml
│   │   └── list_item_note.xml
│   ├── menu/
│   │   └── main_menu.xml              # 主菜单
│   └── values/
│       ├── strings.xml
│       ├── colors.xml
│       └── themes.xml
└── AndroidManifest.xml
```

## 运行说明
1. 使用Android Studio打开项目
2. 同步Gradle
3. 运行到模拟器或真机
4. 测试各项功能：
   - 在主界面输入文本并保存到文件
   - 从文件加载文本
   - 保存笔记到数据库
   - 在设置中配置用户名和自动保存
   - 查看笔记列表
   - 编辑和删除笔记

## 实验要求完成情况

### ✅ 文件读写模块
- [x] EditText多行文本框
- [x] 保存到文件按钮
- [x] 从文件加载按钮
- [x] 使用openFileOutput/openFileInput
- [x] 异常处理和Toast提示

### ✅ SharedPreferences模块
- [x] CheckBox自动保存开关
- [x] 用户名和密码输入
- [x] 使用getSharedPreferences
- [x] 自动加载配置
- [x] 主界面显示用户名
- [x] 自动保存功能（onPause时触发）

### ✅ SQLite数据库模块
- [x] SQLiteOpenHelper实现
- [x] 数据库表设计（_id, title, content, timestamp）
- [x] 新增记录
- [x] 显示记录列表（ListView）
- [x] 查看详情
- [x] 编辑记录
- [x] 删除记录

### ✅ 界面与交互
- [x] 至少3个Activity
- [x] Toolbar菜单导航
- [x] 美观的UI设计

## 额外功能
- ✅ 支持编辑笔记
- ✅ 删除确认对话框
- ✅ 时间格式化显示
- ✅ 空列表提示
- ✅ 长按删除功能

## 技术栈
- Java
- Android SDK
- SQLite
- Material Design Components
