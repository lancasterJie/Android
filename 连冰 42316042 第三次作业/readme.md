# Android 本地数据持久化综合实验说明

## 功能概述
- 整合 **文件存储**、**SharedPreferences**、**SQLite** 三种 Android 本地持久化核心方式，实现笔记记录与管理全流程。
- 核心界面：MainActivity（主界面）、SettingsActivity（设置界面）、RecordListActivity（记录列表）、RecordDetailActivity（记录详情）。
- 关键能力：文件读写、数据库记录存储与查询、昵称动态配置、自动保存、屏幕切换状态保持、记录列表与详情联动。

## 运行环境
- Android Studio Giraffe+
- Gradle（项目内置 Wrapper，无需额外配置）
- 最低兼容版本：与 Material3 模板默认配置一致（Android 8.0+）

## 使用说明
1. 编译运行应用，默认进入主界面（MainActivity）。
2. 主界面操作：
   - 多行 EditText 输入笔记内容，支持换行编辑。
   - 点击“保存到文件”：将当前文本写入私有目录 `note.txt`；点击“从文件加载”：读取 `note.txt` 内容回显，文件不存在时弹出 Toast 提示。
   - 点击“保存为记录”：自动截取文本前 15 字符作为标题，按 `yyyy-MM-dd HH:mm` 生成时间戳，完整内容存入 SQLite 数据库。
   - 顶部标题显示 `SharedPreferences` 配置的昵称（默认“Guest”）；右上角菜单可跳转“设置”或“记录列表”。
3. 设置界面（菜单 → 设置）：
   - 切换 SwitchCompat 控制“自动保存”功能（开启后切后台自动保存文本）。
   - EditText 输入昵称，空值自动回退为“Guest”；点击“保存设置”持久化配置并生效。
4. 记录列表（菜单 → 记录列表）：
   - 按记录创建时间倒序展示所有数据库记录，无数据时显示空视图提示。
   - 点击任意列表项，跳转至详情页查看完整标题、时间和内容。
5. 详情界面：通过滚动视图查看长文本，点击返回按钮回到记录列表。

## 关键实现要点
### 1) UI & 资源配置
| 文件名                  | 核心功能                                                                 |
|-------------------------|--------------------------------------------------------------------------|
| activity_main.xml       | 欢迎文案、多行 EditText、文件操作按钮、保存记录按钮、顶部标题栏、菜单入口 |
| activity_settings.xml   | SwitchCompat（自动保存开关）、EditText（昵称输入）、保存设置按钮           |
| activity_record_list.xml| ListView（记录列表）、空视图（无数据提示文案）                             |
| activity_record_detail.xml | ScrollView（滚动展示）、标题/时间/内容文本控件（支持长文本显示）           |
| item_record.xml         | 列表条目布局：单行省略标题、右侧时间戳（水平排列）                         |
| menu_main.xml           | 两个菜单项：“设置”（跳转 SettingsActivity）、“记录列表”（跳转 RecordListActivity） |
| strings.xml             | 统一管理应用名称、按钮文本、Toast 提示语、空视图文案等静态字符串           |

### 2) 核心功能实现
#### （1）文件存储（操作私有文件 note.txt）
- 保存逻辑：通过 `openFileOutput` 获得输出流，写入文本内容并关闭流。
  ```java
  FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
  fos.write(editText.getText().toString().getBytes());
  fos.close();
  ```
- 加载逻辑：通过 `openFileInput` 获得输入流，用 `ByteArrayOutputStream` 读取字节，转换为字符串后回显。
  ```java
  try {
      FileInputStream fis = openFileInput("note.txt");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len = fis.read(buffer)) != -1) {
          baos.write(buffer, 0, len);
      }
      editText.setText(baos.toString());
      fis.close();
      baos.close();
  } catch (FileNotFoundException e) {
      Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
  }
  ```

#### （2）SharedPreferences 配置管理
- 读取配置：获取 `settings` 实例，读取昵称和自动保存状态（带默认值）。
  ```java
  SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
  String userName = sp.getString("user_name", "Guest");
  boolean autoSave = sp.getBoolean("auto_save", false);
  ```
- 写入配置：通过 `Editor` 存入配置，调用 `apply()` 异步持久化。
  ```java
  SharedPreferences.Editor editor = sp.edit();
  editor.putBoolean("auto_save", switchAutoSave.isChecked());
  editor.putString("user_name", etUserName.getText().toString().trim());
  editor.apply();
  ```
- 自动保存联动：MainActivity 的 `onPause` 中判断开关状态，开启则执行文件保存逻辑。
  ```java
  @Override
  protected void onPause() {
      super.onPause();
      if (autoSave) {
          saveToFile(); // 复用文件保存方法
      }
  }
  ```
- 标题更新：MainActivity 的 `onResume` 中读取昵称，动态设置顶部标题。
  ```java
  @Override
  protected void onResume() {
      super.onResume();
      String userName = sp.getString("user_name", "Guest");
      setTitle(userName + "的笔记");
  }
  ```

#### （3）SQLite 数据库操作
- 数据库辅助类 `MyDbHelper`：继承 `SQLiteOpenHelper`，创建 `records` 表。
  ```java
  public class MyDbHelper extends SQLiteOpenHelper {
      private static final String DB_NAME = "note_db";
      private static final int DB_VERSION = 1;
      private static final String CREATE_TABLE = "CREATE TABLE records (" +
              "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
              "title TEXT," +
              "content TEXT," +
              "time TEXT)";

      @Override
      public void onCreate(SQLiteDatabase db) {
          db.execSQL(CREATE_TABLE);
      }
  }
  ```
- 插入记录：通过 `ContentValues` 封装数据，调用 `insert` 方法存入数据库。
  ```java
  MyDbHelper dbHelper = new MyDbHelper(this);
  SQLiteDatabase db = dbHelper.getWritableDatabase();
  ContentValues values = new ContentValues();
  String content = editText.getText().toString();
  String title = content.length() > 15 ? content.substring(0, 15) : content;
  String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
  values.put("title", title);
  values.put("content", content);
  values.put("time", time);
  db.insert("records", null, values);
  db.close();
  ```
- 查询记录：按 `_id` 倒序查询所有记录，适配 ListView 展示。
  ```java
  SQLiteDatabase db = dbHelper.getReadableDatabase();
  Cursor cursor = db.query("records", null, null, null, null, null, "_id DESC");
  List<Record> recordList = new ArrayList<>();
  while (cursor.moveToNext()) {
      String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
      String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
      String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
      recordList.add(new Record(title, content, time));
  }
  cursor.close();
  db.close();
  ```
- 详情跳转：通过 Intent 传递 `title`、`content`、`time` 数据，RecordDetailActivity 接收并展示。
  ```java
  // RecordListActivity 列表点击事件
  intent.putExtra("title", record.getTitle());
  intent.putExtra("content", record.getContent());
  intent.putExtra("time", record.getTime());
  startActivity(intent);

  // RecordDetailActivity 接收数据
  String title = getIntent().getStringExtra("title");
  String content = getIntent().getStringExtra("content");
  String time = getIntent().getStringExtra("time");
  ```
- 数据库关闭：RecordListActivity 和 RecordDetailActivity 的 `onDestroy` 中关闭数据库，避免资源泄露。
  ```java
  @Override
  protected void onDestroy() {
      super.onDestroy();
      if (dbHelper != null) {
          dbHelper.close();
      }
  }
  ```

## 运行与测试建议
1. 编译运行：使用 Android Studio 直接点击“Run”，或执行命令 `./gradlew assembleDebug` 构建后安装。
2. 核心流程测试：
   - 文件存储测试：输入文本 → 保存文件 → 清空输入 → 加载文本，验证内容一致性。
   - 配置与自动保存测试：进入设置修改昵称和自动保存开关 → 返回主界面检查标题 → 切后台再返回，验证文本是否自动保存。
   - 数据库测试：保存多条记录 → 进入记录列表查看排序（倒序） → 点击任意记录，验证详情页内容完整。
   - 初始化场景测试：清空应用数据 → 重新打开，验证昵称默认“Guest”、自动保存默认关闭、无记录时显示空视图。

要不要我帮你生成一份 **配套的核心代码片段汇总**，包含所有关键实现的完整代码（如 MyDbHelper、MainActivity 核心逻辑），方便直接复制到项目中使用？
