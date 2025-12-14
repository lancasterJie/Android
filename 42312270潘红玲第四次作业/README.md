# Android 本地数据持久化综合实验（Java）

## 一、项目说明

本项目是一个基于 **Android + Java** 的本地数据持久化综合实验，通过开发一个简单的记事本应用，系统性地练习并掌握 Android 中常用的三种本地数据存储方式：

- 文件存储（File）
- SharedPreferences
- SQLite 数据库

应用采用多 Activity 结构，实现了数据保存、读取、界面跳转以及生命周期控制等基本功能。

---

## 二、已完成功能概述

### 1️ 文本编辑与文件读写功能

- 在主界面提供多行文本输入框（EditText）
- 支持将文本内容保存到本地文件
- 支持从本地文件加载文本内容并显示

**功能说明：**
- 点击“保存到文件”按钮，将当前输入内容写入应用私有目录中的 `note.txt`
- 点击“从文件加载”按钮，读取文件内容并显示到输入框
- 若文件不存在，进行异常捕获并提示用户

---

### 2️ 应用设置功能（SharedPreferences）

- 提供独立的设置界面（SettingsActivity）
- 支持设置：
  - 用户名
  - 是否开启“自动保存”功能

**功能说明：**
- 使用 `SharedPreferences` 保存用户配置数据
- 打开设置界面时自动读取已保存的设置并更新 UI
- 主界面在 `onResume()` 中读取用户名并更新标题栏
- 若开启“自动保存”，在主界面 `onPause()` 时自动保存当前文本内容到文件

---

### 3️ 数据库存储与记录管理（SQLite）

- 使用 SQLite 数据库保存多条文本记录
- 每条记录包含：
  - 标题（内容前 10 个字符）
  - 完整内容
  - 保存时间

**功能说明：**
- 用户可将当前编辑内容保存为一条数据库记录
- 使用 SQLiteOpenHelper 管理数据库创建与升级
- 在记录列表界面展示所有保存记录
- 列表按时间倒序排列，显示标题和保存时间

---

### 4️ 多界面切换与交互

- 项目包含多个 Activity：
  - MainActivity：文本编辑、文件保存、功能入口
  - SettingsActivity：应用设置
  - RecordListActivity：数据库记录列表
- 使用 Intent 实现 Activity 之间的跳转
- 各界面职责清晰，逻辑独立

---

## 三、使用的工具与技术

### 🔹 开发工具
- Android Studio
- Java 编程语言
- Android SDK

### 🔹 核心技术点

| 技术 | 用途 |
|----|----|
| FileInputStream / FileOutputStream | 本地文件读写 |
| SharedPreferences | 简单配置数据存储 |
| SQLite + SQLiteOpenHelper | 结构化数据持久化 |
| Activity 生命周期 | 自动保存与界面刷新 |
| Intent | Activity 页面跳转 |
| ListView + SimpleCursorAdapter | 数据列表展示 |

---

## 四、功能实现方法说明

### 1️ 文件存储实现方式

- 使用 `openFileOutput()` 将文本写入内部存储文件
- 使用 `openFileInput()` 读取文件内容
- 通过字节流方式处理文本数据
- 使用 try-catch 进行异常处理

---

### 2️ SharedPreferences 实现方式

- 使用 `getSharedPreferences("settings", MODE_PRIVATE)` 获取配置文件
- 通过 `Editor` 写入用户配置
- 在 Activity 生命周期方法中读取配置并更新界面
- 利用 `onPause()` 实现自动保存逻辑

---

### 3️ SQLite 数据库实现方式

- 自定义 `MyDbHelper` 继承 `SQLiteOpenHelper`
- 在 `onCreate()` 中创建数据表
- 使用 `ContentValues` 封装数据
- 通过 `insert()` 方法保存记录
- 使用 `Cursor` 查询数据库数据
- 结合 `SimpleCursorAdapter` 显示到 ListView

---

## 五、项目结构说明

```text
com.example.datasave
│
├── MainActivity.java        // 主界面：编辑与文件保存
├── SettingsActivity.java    // 设置界面：SharedPreferences
├── RecordListActivity.java  // 记录列表：SQLite 查询
├── MyDbHelper.java          // 数据库管理类
│
└── res/layout
    ├── activity_main.xml
    ├── activity_settings.xml
    └── activity_record_list.xml
