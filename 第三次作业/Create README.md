---

# Android Fragment + Activity 通信与状态保存实验

本项目实现了 Android 中多个核心组件的综合使用，包括 Fragment 切换、Bundle 数据传输以及屏幕旋转状态保存。通过 RadioGroup 控制多个 Fragment 的展示，并实现不同组件之间的数据往返传递，同时完成旋转过程中的 UI 状态保持。

---

## 📌 目录

* [项目简介](#项目简介)
* [1. RadioGroup 控制 Fragment 切换](#1-radiogroup-控制-fragment-切换)
* [2. Bundle 数据传输](#2-bundle-数据传输)

  * [场景 A：Activity → Activity](#场景-a-activity--activity)
  * [场景 B：Activity ↔ Fragment](#场景-b-activity--fragment)
  * [场景 C：Fragment → Fragment](#场景-c-fragment--fragment)
* [3. 屏幕旋转与状态保存](#3-屏幕旋转与状态保存)
* [项目目录结构](#项目目录结构)
* [实验总结](#实验总结)

---

## 项目简介

本实验包含以下三大模块：

1. **通过 RadioGroup 切换四个 Fragment（Home、Profile、Settings、About）**
2. **实现 Activity ↔ Activity、Activity ↔ Fragment、Fragment ↔ Fragment 的 Bundle 数据传输**
3. **通过 onSaveInstanceState 完成屏幕旋转后的 UI 状态保存**

整个项目以 MainActivity 为核心，完成 Fragment 管理及数据分发，并确保旋转后 Fragment 的数据能够顺利恢复。

---

## 1. RadioGroup 控制 Fragment 切换

### ✔️ 功能说明

* MainActivity 中使用一个 RadioGroup 控制四个 Fragment 的切换。
* 每个 Fragment 都具有不同的 UI 控件，用于展示不同页面。
* Fragment 只创建一次，切换时复用，提高性能。

### ✔️ 示例代码（MainActivity）

```java
radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment selected = null;
            String tag = null;
            if (checkedId == R.id.rbHome) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                if (selected == null) selected = new HomeFragment();
                tag= TAG_HOME;
            } else if (checkedId == R.id.rbProfile) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_PROFILE);
                if (selected == null) selected = new ProfileFragment();
                tag = TAG_PROFILE;
            } else if (checkedId == R.id.rbSettings) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS);
                if (selected == null) selected = new SettingsFragment();
                tag = TAG_SETTINGS;
            } else if (checkedId == R.id.rbAbout) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_ABOUT);
                if (selected == null) selected = new AboutFragment();
                tag= TAG_ABOUT;
            }
            if (selected != null) {
                replaceFragment(selected, tag);
            }
        });
```

### ✔️ 使用 selector 与 style 统一样式

`res/drawable/radio_button_selector.xml` 控制 RadioButton 按选中状态切换背景。

---

## 2. Bundle 数据传输

本项目实现了三种常见的数据传输方式。

---

### 📌 场景 A：Activity → Activity

从 `HomeFragment` 跳转到 `DetailActivity`，传递：

* 用户名（String）
* 年龄（String）
* 是否学生（boolean）

```java
Intent intent = new Intent(getActivity(), DetailActivity.class);
intent.putExtra("name", name);
intent.putExtra("age", age);
intent.putExtra("isStudent", isStudent);
startActivity(intent);
```

---

### 📌 场景 B：Activity ↔ Fragment

#### Activity → Fragment

当 Activity 加载 Fragment 时传递初始化数据：

```java
Bundle bundle = new Bundle();
bundle.putString("message", "Hello, ProfileFragment!");
fragment.setArguments(bundle);
```

#### Fragment → Activity（FragmentResult）

```java
Bundle bundle = new Bundle();
bundle.putString("reply", "已收到消息");
getParentFragmentManager().setFragmentResult("profilekey", bundle);
```

MainActivity 监听：

```java
getSupportFragmentManager().setFragmentResultListener("profilekey", this,
        (key, result) -> {
            Toast.makeText(this,
                "来自ProfileFragment的消息: " + result.getString("reply"),
                Toast.LENGTH_SHORT).show();
        });
```

---

### 📌 场景 C：Fragment → Fragment（Activity 中转）

通过 `setFragmentResult()` 完成跨 Fragment 数据传递，符合解耦设计：

```
Fragment A → MainActivity → Fragment B
```

---

## 3. 屏幕旋转与状态保存

项目要求在旋转后恢复 EditText 与 Switch 状态。

### ✔️ 保存数据（HomeFragment）

```java
@Override
public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("name", etUsername.getText().toString());
    outState.putString("age", etAge.getText().toString());
    outState.putBoolean("isStudent", switchIsStudent.isChecked());
    Log.d(TAG, "onSaveInstanceState called - 保存EditText文本");
}
```

### ✔️ 恢复数据（HomeFragment）

```java
if (savedInstanceState != null) {
    etUsername.setText(savedInstanceState.getString("name", ""));
    etAge.setText(savedInstanceState.getString("age", ""));
    switchIsStudent.setChecked(savedInstanceState.getBoolean("isStudent", false));
}
```

### ✔️ 学到的内容

* Fragment 恢复 UI 状态必须在 `onCreateView()` 中执行。
* Understanding 生命周期顺序：
  `onPause → onStop → onSaveInstanceState → onDestroy → onCreate`

---

## 项目目录结构

```
app/
├── java/
│   └── com.example.myapplication/
│       ├── MainActivity.java
│       ├── DetailActivity.java
│       └── fragments/
│           ├── HomeFragment.java
│           ├── ProfileFragment.java
│           ├── SettingsFragment.java
│           └── AboutFragment.java
│
└── res/
    ├── layout/
    ├── drawable/
    │   └── radio_button_selector.xml
    └── values/
        └── styles.xml
```

---

## 实验总结

通过本次实验，我系统掌握了 Android 中与 Fragment 相关的核心技术，包括：

### ✔ Fragment 与 Activity 之间的交互与生命周期

理解了 replace/add 的差异与 UI 切换机制。

### ✔ 三类 Bundle 数据传输方式

包含 Activity 到 Activity、Activity 与 Fragment 双向通信、Fragment 到 Fragment 跨页面通信。

### ✔ onSaveInstanceState 与旋转状态恢复

成功让 Fragment 在横竖屏切换时保持输入状态。

### ✔ UI 组件样式管理

掌握了 selector 与 style 的使用，提高界面一致性设计能力。

本项目有效帮助我理解 Android 应用中的组件组织方式以及生命周期管理，对后续开发复杂页面打下良好基础。

---

