# Android 本地数据持久化综合实验

这是一个完整的Android应用项目，实现了三种本地数据存储方式：文件读写、SharedPreferences和SQLite数据库。

## 项目结构

```
app/
├── src/
│   └── main/
│       ├── java/com/example/notepadapp/
│       │   ├── MainActivity.java          # 主界面：文件读写功能
│       │   ├── SettingsActivity.java      # 设置界面：SharedPreferences
│       │   ├── RecordListActivity.java    # 记录列表界面
│       │   ├── RecordDetailActivity.java  # 记录详情界面
│       │   ├── MyDbHelper.java            # SQLite数据库帮助类
│       │   └── Record.java                # 记录数据模型
│       ├── res/
│       │   ├── layout/                    # 布局文件
│       │   ├── menu/                      # 菜单文件
│       │   └── values/                    # 资源文件
│       └── AndroidManifest.xml
└── build.gradle
```

## 功能模块

### 1. 文件读写模块（MainActivity）
- ✅ 多行文本输入框（EditText）
- ✅ "保存到文件"按钮：将内容保存到 `note.txt`
- ✅ "从文件加载"按钮：从 `note.txt` 读取内容
- ✅ 异常处理和Toast提示

### 2. SharedPreferences设置模块（SettingsActivity）
- ✅ 自动保存账户密码的CheckBox
- ✅ 用户昵称和密码输入框
- ✅ Login按钮
- ✅ 自动加载和保存配置
- ✅ 主界面标题栏显示用户名
- ✅ 自动保存功能（当auto_save开启时）

### 3. SQLite数据库模块
- ✅ `MyDbHelper`：数据库帮助类，继承自 `SQLiteOpenHelper`
- ✅ `records` 表：包含 `_id`, `title`, `content`, `time` 字段
- ✅ 新增记录功能
- ✅ 记录列表显示（ListView）
- ✅ 记录详情查看
- ✅ 删除记录功能（长按删除）
- ✅ 更新记录功能（在MyDbHelper中已实现）

## 使用方法

1. **文件读写**：
   - 在主界面输入文本
   - 点击"保存到文件"保存内容
   - 点击"从文件加载"加载之前保存的内容

2. **设置**：
   - 点击菜单中的"设置"
   - 配置自动保存、用户昵称和密码
   - 设置会自动保存

3. **数据库记录**：
   - 在主界面输入文本后，点击"保存为记录"
   - 点击菜单中的"记录列表"查看所有记录
   - 点击记录查看详情
   - 长按记录可删除

## 技术要点

- **文件操作**：使用 `openFileInput()` 和 `openFileOutput()`
- **SharedPreferences**：使用 `getSharedPreferences()` 进行配置管理
- **SQLite**：使用 `SQLiteOpenHelper` 管理数据库，实现CRUD操作
- **Activity生命周期**：在 `onResume()` 和 `onPause()` 中处理数据加载和保存

## 编译和运行

1. 使用 Android Studio 打开项目
2. 同步 Gradle 依赖
3. 连接 Android 设备或启动模拟器
4. 运行应用

## 注意事项

- 最低支持 Android API 24 (Android 7.0)
- 目标 SDK 版本：34
- 使用 Java 语言开发
- 使用 Material Design 组件

