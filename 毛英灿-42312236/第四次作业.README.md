# Android 本地数据持久化综合实验（Java）

## 一、实验目标

通过本实验，学生需要掌握 Android 中三种常用本地数据存储方式：

1. **文件（File）读写：** 使用 `FileInputStream` / `FileOutputStream` 或 `openFileInput` / `openFileOutput` 进行文本文件的保存与加载。
2. **SharedPreferences：** 使用 `getSharedPreferences()` 保存和读取简单的键值对配置数据（实现账户，密码的自动保存和读取）。
3. **SQLite 数据库：** 使用 `SQLiteOpenHelper` 管理数据库，完成基本的增删改查（CRUD）操作。
**基本形式如图所示：**
![alt text](image-9.png)
---

## 二、实验内容与功能要求

设计并实现一个简单的“**记事本 + 设置 + 数据库记录**”应用
<img src="image.png" alt="alt text" style="width:50%; display:block; margin-left:auto; margin-right:auto">
包含如下模块：

### 1. 文件读写模块（File：Load / Save）

- 在主界面提供一个多行文本框（`EditText`）和两个按钮：
  - `保存到文件` 按钮
  - `从文件加载` 按钮
- 功能要求：
  1. 用户在 `EditText` 中输入文本，点击 `保存到文件`，将内容写入到应用私有目录中的一个文本文件，例如：`note.txt`。
     ![alt text](image-1.png)
  2. 点击 `从文件加载` 时，从 `note.txt` 中读取文本并显示到 `EditText` 中。
 ![alt text](image-2.png)
  3. 若文件不存在，需进行异常处理并给出 `Toast` 提示。

### 2. 设置模块（SharedPreferences：getSharedPreferences）

- 在设置界面（可以是一个新的 `Activity`）中提供以下控件示例：
  - 一个 `Checkbox`：是否开启“自动保存账户密码”
  - 2个 `EditText`：用户昵称和密码（例如显示在主界面标题）
  - 一个 `Button`：login跳转到其他页面
- 功能要求：
  1. 使用 `getSharedPreferences("settings", MODE_PRIVATE)` 获取 `SharedPreferences` 实例。
     ![alt text](image-3.png)
  2. 打开设置界面时自动加载之前保存的配置并更新 UI。
  3. 在主界面中读取 `SharedPreferences`，例如在 `onResume()` 中读取 `user_name` 并更新标题栏。
  4. 若 `auto_save` 为开启状态，当用户返回主界面或退出应用时自动调用“保存到文件”功能。
  ![alt text](image-4.png)
  ![alt text](image-5.png)

### 3. 数据库模块（SQLite）

- 新建一个“记录列表”界面，用来展示用户保存的多条记录，例如“备忘录记录”、“操作日志”或简单“任务列表”。
- 基本要求：
  1. 设计一个数据库表，例如：`records`，包含字段：
     - `_id`（整数主键，自增）
     - `title`（文本）
     - `content`（文本）
     - `time`（文本或整数时间戳）
  2. 编写一个继承自 `SQLiteOpenHelper` 的帮助类
  3. 提供以下基本功能：
     - **新增记录**：在主界面或单独界面中，用户可以将当前 `EditText` 内容保存为一条记录（title 可为前几字，content 为全文，time 为当前时间）。
     - **显示记录列表**：在“记录列表”界面使用 `ListView` / `RecyclerView` 展示所有记录的 `title` 和 `time`。
     - **查看详情**：点击列表项时，跳转到详情界面，显示完整 `content`。
     - 额外加分：支持删除记录、编辑记录。

#### 相应代码如下，存在MyDbHelper.java中：
```java
public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_RECORDS = "records";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TIME = "time";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_RECORDS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," +
                KEY_CONTENT + " TEXT," +
                KEY_TIME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(db);
    }

    // 添加记录
    public void addRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, record.getTitle());
        values.put(KEY_CONTENT, record.getContent());
        values.put(KEY_TIME, record.getTime());

        db.insert(TABLE_RECORDS, null, values);
        db.close();
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> recordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECORDS + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setId(cursor.getInt(0));
                record.setTitle(cursor.getString(1));
                record.setContent(cursor.getString(2));
                record.setTime(cursor.getString(3));
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordList;
    }

    // 根据ID获取记录
    public Record getRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS,
                new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_TIME},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Record record = new Record(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        record.setId(cursor.getInt(0));

        cursor.close();
        db.close();
        return record;
    }

    // 删除记录
    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
```
---

## 三、界面与交互设计建议

- 至少包含 3 个 Activity（或使用 Fragment）：
  1. **MainActivity**：文本编辑 + 文件读写 + “保存为记录”按钮。
  2. **SettingsActivity**：应用设置界面（使用 SharedPreferences）。
  3. **RecordListActivity**：显示 SQLite 中的记录列表，可点击进入详情。
- 在主界面中通过菜单（`OptionsMenu` 或 `Toolbar`）跳转到“设置”和“记录列表”。
![alt text](image-6.png)
---

## 四、实现提示（关键代码片段示例）

### 1. SharedPreferences 读写示例

```java
    private void loadUserInfo() {
        String userName = sharedPreferences.getString("user_name", "Guest");
        tvWelcome.setText("欢迎 " + userName);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();

        // 如果开启了自动保存，则自动保存到文件
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        if (autoSave && !etNote.getText().toString().isEmpty()) {
            saveToFile();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
      }
```
![alt text](image-7.png)
![alt text](image-8.png)
