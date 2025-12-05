# Android 本地数据持久化综合实验

## 项目概述

本项目是一个基于Android平台的本地数据持久化综合实验，实现了一个完整的"记事本 + 设置 + 数据库记录"应用。通过本项目，深入学习和实践了Android中三种常用的本地数据存储方式：文件读写、SharedPreferences和SQLite数据库。

## 实验目标

掌握Android中三种常用本地数据存储方式：
1. **文件（File）读写**：使用`openFileInput`/`openFileOutput`进行文本文件的保存与加载
2. **SharedPreferences**：使用`getSharedPreferences()`保存和读取简单的键值对配置数据
3. **SQLite 数据库**：使用`SQLiteOpenHelper`管理数据库，完成基本的增删改查操作

## 功能模块

### 1. 文件读写模块（File I/O）

**功能描述**：
- 提供文本编辑功能，支持将内容保存到文件和从文件加载内容
- 主界面包含一个多行EditText和两个操作按钮

**核心功能**：
- ✅ `保存到文件`：将EditText内容写入应用私有目录的`note.txt`文件
- ✅ `从文件加载`：从`note.txt`读取内容并显示到EditText
- ✅ 异常处理：文件不存在时给出Toast提示

**实现方式**：
```java
// 保存到文件
FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
fos.write(text.getBytes());
fos.close();

// 从文件加载
FileInputStream fis = openFileInput("note.txt");
// 读取并转换为字符串
```

### 2. 设置模块（SharedPreferences）

**功能描述**：
- 提供应用设置功能，包括自动保存配置和账户信息管理
- 设置界面包含Checkbox、EditText和Button控件

**核心功能**：
- ✅ 自动保存账户密码开关（Checkbox）
- ✅ 用户昵称和密码设置（EditText）
- ✅ 登录跳转功能（Button）
- ✅ 配置数据持久化存储
- ✅ 主界面标题动态更新

**实现方式**：
```java
// 获取SharedPreferences实例
SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
// 写入数据
SharedPreferences.Editor editor = sp.edit();
editor.putBoolean("auto_save", true/false);
editor.putString("user_name", "xxx");
editor.apply();
// 读取数据
boolean autoSave = sp.getBoolean("auto_save", false);
```

### 3. 数据库模块（SQLite）

**功能描述**：
- 提供数据库记录管理功能，支持记录的新增、查看和详情展示
- 使用RecyclerView展示记录列表，支持点击查看详情

**核心功能**：
- ✅ 新增记录：将当前EditText内容保存为数据库记录
- ✅ 记录列表展示：使用RecyclerView展示所有记录的标题和时间
- ✅ 详情查看：点击列表项跳转到详情界面，显示完整内容
- ✅ 数据库自动创建和升级管理

**实现方式**：
- 数据库表结构：`records`表包含`_id`、`title`、`content`、`time`字段
- 使用`SQLiteOpenHelper`管理数据库的创建和升级
- 使用`RecyclerView`和自定义适配器展示记录列表

## 界面设计

### 1. MainActivity（主界面）
- 文本编辑区域（多行EditText）
- 操作按钮：保存到文件、从文件加载、保存为记录
- Toolbar菜单：跳转到设置和记录列表

### 2. SettingsActivity（设置界面）
- 自动保存账户密码（Checkbox）
- 用户昵称输入框（EditText）
- 密码输入框（EditText）
- 登录按钮（Button）

### 3. RecordListActivity（记录列表界面）
- RecyclerView记录列表：展示记录标题和时间
- 点击列表项跳转到详情界面

### 4. RecordDetailActivity（记录详情界面）
- 展示记录的完整标题、时间和内容

## 项目结构

```
app/src/main/
├── java/com/example/fileinputoutput/
│   ├── MainActivity.java          # 主界面，文本编辑+文件读写+保存记录
│   ├── SettingsActivity.java       # 设置界面，SharedPreferences配置
│   ├── RecordListActivity.java     # 记录列表，SQLite数据展示
│   ├── RecordDetailActivity.java   # 记录详情
│   ├── MyDbHelper.java             # SQLite数据库帮助类
│   └── Record.java                 # 记录实体类
└── res/
    ├── layout/
    │   ├── activity_main.xml       # 主界面布局
    │   ├── activity_setting.xml    # 设置界面布局
    │   ├── activity_record_list.xml # 记录列表布局
    │   ├── activity_record_detail.xml # 记录详情布局
    │   └── item_record.xml         # 列表项布局
    └── menu/
        └── main_menu.xml           # 主界面菜单
```

## 使用说明

1. **文本编辑与文件操作**：
   - 在主界面EditText中输入文本
   - 点击"保存到文件"将内容保存到note.txt
   - 点击"从文件加载"从note.txt读取内容

2. **应用设置**：
   - 通过主界面菜单进入设置界面
   - 设置用户昵称和密码
   - 开启/关闭自动保存功能
   - 点击登录按钮跳转到其他页面

3. **数据库记录管理**：
   - 在主界面输入内容后，点击"保存为记录"
   - 通过主界面菜单进入记录列表
   - 点击列表项查看记录详情

## 核心代码示例

### 1. 文件读写实现

```java
// 保存文件
private void save(String inputString) {
    String fileName = "note.txt";
    try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
        fos.write(inputString.getBytes());
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

// 加载文件
private String load(String fileName) {
    StringBuilder content = new StringBuilder();
    try (FileInputStream fis = openFileInput(fileName)) {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) != -1) {
            content.append(new String(buffer, 0, length));
        }
    } catch (FileNotFoundException e) {
        Toast.makeText(this, "文件不存在！", Toast.LENGTH_LONG).show();
        return "";
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    return content.toString();
}
```

### 2. SharedPreferences实现

```java
// 读取设置
SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
boolean autoSave = sp.getBoolean("auto_save", false);
String userName = sp.getString("user_name", "无");

// 写入设置
SharedPreferences.Editor editor = sp.edit();
editor.putBoolean("auto_save", true);
editor.putString("user_name", "张三");
editor.apply();
```

### 3. SQLite数据库实现

```java
// 数据库帮助类
public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE records (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }
}
```

## 注意事项

1. **文件存储位置**：
   - 使用`openFileOutput`保存的文件位于应用私有目录：`/data/data/com.example.fileinputoutput/files/`
   - 该目录下的文件只有应用本身可以访问，其他应用无法访问，保证了数据安全性

2. **SharedPreferences数据**：
   - 存储在`/data/data/com.example.fileinputoutput/shared_prefs/settings.xml`
   - 适合存储简单的键值对数据，不适合存储大量复杂数据

3. **SQLite数据库**：
   - 存储在`/data/data/com.example.fileinputoutput/databases/myapp.db`
   - 适合存储结构化数据，支持复杂查询

4. **权限说明**：
   - 本应用仅使用内部存储，不需要额外的存储权限
   - Android 6.0及以上版本不需要动态申请权限

## 总结

本项目成功实现了Android本地数据持久化的三种常用方式，通过实际应用场景深入理解了每种存储方式的特点和适用场景：

| 存储方式          | 适用场景         | 特点                                 |
| ----------------- | ---------------- | ------------------------------------ |
| 文件读写          | 保存大量文本数据 | 简单直接，适合存储非结构化数据       |
| SharedPreferences | 保存简单配置数据 | 键值对存储，适合存储少量配置信息     |
| SQLite数据库      | 保存结构化数据   | 支持复杂查询，适合存储大量结构化数据 |

通过本实验，掌握了Android本地数据存储的核心技术，为开发更复杂的Android应用打下了坚实的基础。

