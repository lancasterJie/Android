# Android 本地数据持久化实验报告

## 学生信息
- **姓名**: 吴秋寒
- **学号**: 42312260
- **实验日期**: 2025年11月

---

## 一、实验目的

1. 掌握 Android 中文件（File）读写操作
2. 掌握 SharedPreferences 保存和读取键值对数据
3. 掌握 SQLite 数据库的基本 CRUD 操作
4. 理解三种数据持久化方式的适用场景

---

## 二、实验内容

### 2.1 文件读写模块

#### 功能描述
使用 FileInputStream / FileOutputStream 进行文本文件的保存与加载。

#### 核心代码

**保存到文件**
```java
private void saveToFile(String content) {
    try {
        FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
        fos.write(content.getBytes());
        fos.close();
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
```

**从文件加载**
```java
private String loadFromFile() {
    try {
        FileInputStream fis = openFileInput("note.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        fis.close();
        return sb.toString().trim();
    } catch (FileNotFoundException e) {
        Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        return "";
    } catch (IOException e) {
        e.printStackTrace();
        return "";
    }
}
```

---

### 2.2 SharedPreferences 设置模块

#### 功能描述
使用 SharedPreferences 保存账户密码和自动保存配置。

#### 核心代码

**保存配置**
```java
private void savePreferences() {
    SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    
    editor.putBoolean("auto_save", cbAutoSave.isChecked());
    editor.putString("user_name", etUserName.getText().toString());
    editor.putString("passwd", etPassword.getText().toString());
    editor.apply();
    
    Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
}
```

**加载配置**
```java
private void loadPreferences() {
    SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
    
    boolean autoSave = sp.getBoolean("auto_save", false);
    String userName = sp.getString("user_name", "");
    String password = sp.getString("passwd", "");
    
    cbAutoSave.setChecked(autoSave);
    etUserName.setText(userName);
    etPassword.setText(password);
}
```

---

### 2.3 SQLite 数据库模块

#### 功能描述
使用 SQLiteOpenHelper 管理数据库，完成记录的增删改查操作。

#### 数据库帮助类

```java
public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

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

#### 增删改查操作

**新增记录**
```java
public long insertRecord(String title, String content) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("title", title);
    values.put("content", content);
    values.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(new Date()));
    long id = db.insert("records", null, values);
    db.close();
    return id;
}
```

**查询所有记录**
```java
public List<Record> getAllRecords() {
    List<Record> records = new ArrayList<>();
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.query("records", null, null, null, null, null, "time DESC");
    
    while (cursor.moveToNext()) {
        Record record = new Record();
        record.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        record.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        record.setContent(cursor.getString(cursor.getColumnIndex("content")));
        record.setTime(cursor.getString(cursor.getColumnIndex("time")));
        records.add(record);
    }
    cursor.close();
    db.close();
    return records;
}
```

**删除记录**
```java
public int deleteRecord(int id) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    int rows = db.delete("records", "_id = ?", new String[]{String.valueOf(id)});
    db.close();
    return rows;
}
```

**更新记录**
```java
public int updateRecord(int id, String title, String content) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("title", title);
    values.put("content", content);
    values.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(new Date()));
    int rows = db.update("records", values, "_id = ?", new String[]{String.valueOf(id)});
    db.close();
    return rows;
}
```

---

## 三、界面

### 3.2 MainActivity（主界面）
- 

#### 核心组件
```xml
<!-- 主要组件 -->
- EditText (etContent): 多行文本输入
- Button (btnSave): 保存到文件
- Button (btnLoad): 从文件加载
- Button (btnSaveRecord): 保存为数据库记录
- OptionsMenu: 跳转到设置、记录列表*
```

### ![](./../../安卓第四次作业/图片/主界面.png)

### 3.3 SettingsActivity（设置界面)

#### 布局特点
- **标题区域**: ⚙️ 应用设置 + 功能说明
- **偏好设置卡片**:
  - CheckBox: 🔒 自动保存账户密码
  - 包含详细说明文字
- **账户信息卡片**:
  - 标签 + EditText组合，清晰的表单设计
  - 用户名输入框
  - 密码输入框（密码类型）
- **操作按钮区域**:
  - 保存设置按钮（蓝色）
  - 登录按钮（绿色）
- **测试账号提示**: 显示测试用户名和密码

#### 核心组件
```xml
- CheckBox (cbAutoSave): 自动保存开关
- EditText (etUserName): 用户名输入
- EditText (etPassword): 密码输入
- Button (btnSave): 保存设置
- Button (btnLogin): 登录验证
```

![](./../../安卓第四次作业/图片/设置.png)

### 3.4 RecordListActivity（记录列表）

#### 布局特点
- **顶部标题卡片**: 
  - 🗄️ 数据库记录列表
  - 固定在顶部，带阴影效果
- **RecyclerView**: 显示所有记录

- **交互**: 点击查看详情，长按删除
- ![](./../../安卓第四次作业/图片/记录列表.png)

### 3.5 自定义Drawable资源
#### button_primary.xml - 主要按钮背景（蓝色）
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#2196F3"/>
    <corners android:radius="12dp"/>
</shape>
```

#### button_secondary.xml - 次要按钮背景（绿色）
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#4CAF50"/>
    <corners android:radius="12dp"/>
</shape>
```

#### card_bg.xml - 卡片背景
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#FFFFFF"/>
    <stroke android:width="1dp" android:color="#E0E0E0"/>
    <corners android:radius="12dp"/>
</shape>
```

#### input_bg.xml - 输入框背景
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#F5F5F5"/>
    <stroke android:width="1dp" android:color="#BDBDBD"/>
    <corners android:radius="8dp"/>
</shape>
```

---

<img src="./../../安卓第四次作业/图片/保存成功.png" style="zoom:67%;" />

![](./../../安卓第四次作业/图片/纪录保存.png)

![](./../../安卓第四次作业/图片/加载成功.png)

---

## 五、实验总结

### 5.1 三种存储方式对比

| 存储方式 | 适用场景 | 优点 | 缺点 |
|----------|----------|------|------|
| File | 大文本、日志 | 灵活、无限制 | 需要自行处理格式 |
| SharedPreferences | 配置项、键值对 | 简单易用 | 只支持基本类型 |
| SQLite | 结构化数据 | 支持复杂查询 | 学习成本较高 |

