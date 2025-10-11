# Android Activity 生命周期观察实验

## 1. 创建 DialogActivity

在 Activity Navigator 中，已创建 `MainActivity` 和 `SecondActivity`，接下来创建 `DialogActivity`。

1. 在项目中创建一个新的活动，在新的布局文件中添加 **返回按钮** 和 **提示文字**。
2. 在 Java 文件中为按钮添加 **点击事件监听器**。
3. **关键点**：为 `DialogActivity` 设置对话框主题，在 `AndroidManifest.xml` 中注册该活动，添加如下语句：

```xml
<activity
    android:name=".DialogActivity"
    android:theme="@style/Theme.AppCompat.Dialog"/>
```

> 注意：因为 `DialogActivity` 继承自 `AppCompatActivity`，所以设置对话框主题时，**不能使用系统主题**：
>
> ```xml
> android:theme="@android:style/Theme.Dialog"
> ```
>
> 这两者不兼容。

---

## 2. 生命周期日志输出

1. 在三个活动中重写 **所有生命周期方法**。
2. 定义日志标识：

```java
private static final String TAG ="Lifecycle";
```

3. 在每个生命周期方法中添加打印语句，例如：

```java
Log.d(TAG, "MainActivity - onCreate");
```

---

## 3. 生命周期变化分析

### 生命周期变化表

| 场景                    | MainActivity 生命周期顺序 | 目标 Activity 生命周期顺序            |
| --------------------- | ------------------- | ----------------------------- |
| 应用启动                  | onCreate → onResume | -                             |
| Main → SecondActivity | onPause → onStop    | onCreate → onStart → onResume |
| SecondActivity 返回     | onStart → onResume  | onPause → onStop → onDestroy  |
| Main → DialogActivity | onPause             | onCreate → onStart → onResume |
| DialogActivity 返回     | onResume            | onPause → onStop → onDestroy  |

---

### 分析过程

1. **启动应用**

   * 应用刚启动，系统创建 `MainActivity`。
   * `onCreate()`：Activity 被创建，进行布局初始化、数据绑定等。
   * `onResume()`：Activity 进入前台，可交互状态。

2. **Main → SecondActivity**

   * 点击按钮从 `MainActivity` 跳转到 `SecondActivity`。
   * **MainActivity**：

     * `onPause()`：不再在前台，但可能部分可见。
     * `onStop()`：完全不可见。
   * **SecondActivity**：

     * `onCreate()`：Activity 创建。
     * `onStart()`：即将对用户可见。
     * `onResume()`：进入前台，可交互。

3. **SecondActivity 返回 MainActivity**

   * 用户点击 **返回按钮** 或 `finish()` 关闭 `SecondActivity`。
   * **SecondActivity**：

     * `onPause()`：准备离开前台。
     * `onStop()`：完全不可见。
     * `onDestroy()`：释放资源。
   * **MainActivity**：

     * `onStart()`：再次可见。
     * `onResume()`：回到前台，可交互。

4. **Main → DialogActivity**

   * `DialogActivity` 是对话框模式（非全屏）。
   * **MainActivity**：

     * `onPause()`：对话框出现，不再在前台，但仍可见。
   * **DialogActivity**：

     * `onCreate()` → `onStart()` → `onResume()`：创建并显示对话框，可交互。
   * **特点**：MainActivity 没有调用 `onStop()`，仍然保留在后台。

5. **DialogActivity 返回 MainActivity**

   * 用户关闭对话框 (`finish()`)。
   * **DialogActivity**：

     * `onPause()`：准备离开前台。
     * `onStop()`：不可见。
     * `onDestroy()`：销毁 Activity。
   * **MainActivity**：

     * `onResume()`：对话框关闭后恢复到前台，可交互。

---

## 4. 总结规律

1. **普通 Activity 切换**

   * 当前 Activity → `onPause()` → `onStop()`
   * 新 Activity → `onCreate()` → `onStart()` → `onResume()`
   * 返回时：

     * 新 Activity → `onPause()` → `onStop()` → `onDestroy()`
     * 原 Activity → `onStart()` → `onResume()`

2. **对话框（非全屏）模式**

   * 背景 Activity 只调用 `onPause()`，不会调用 `onStop()`。
   * 对话框 Activity 调用 `onCreate()` → `onStart()` → `onResume()`。
   * 关闭对话框时：

     * 对话框 Activity 调用 `onPause()` → `onStop()` → `onDestroy()`。
     * 背景 Activity 调用 `onResume()`。
