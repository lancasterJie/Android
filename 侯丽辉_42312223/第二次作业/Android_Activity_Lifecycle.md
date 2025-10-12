# Android Activity 生命周期观察实验报告

**实验名称**：Activity Lifecycle 观察  
**实验日期**：2025年10月12日  
**姓名**：侯丽辉  
**学号**：42312223 

## 一、实验目的

1. 掌握 **Android Activity 生命周期**的基本概念和各个生命周期方法的调用时机  
2. 通过 **Log 输出**观察 Activity 在不同场景下的生命周期变化规律  
3. 理解 **Activity 跳转和返回**时的生命周期调用顺序  
4. 分析 **普通 Activity** 与 **Dialog Activity** 在生命周期上的差异  



## 二、实验内容

完成一个名为 **"LifecycleExperiment"** 的 Android 应用，包含三个 Activity：  
- **MainActivity**：应用主界面，包含跳转按钮  
- **SecondActivity**：普通 Activity，用于对比生命周期  
- **DialogActivity**：对话框样式 Activity，观察特殊生命周期行为  

## 三、实验步骤

### （一）项目结构搭建

1. **创建项目基础结构**  
   - 创建 MainActivity、SecondActivity、DialogActivity  
   - 配置 AndroidManifest.xml  

2. **配置 Dialog Activity 主题**  
   在 `themes.xml` 中添加对话框主题：  

   ```xml
   <style name="DialogTheme" parent="Theme.AppCompat.Light.Dialog">
       <item name="android:windowBackground">@android:color/transparent</item>
       <item name="android:windowNoTitle">true</item>
   </style>
   ```

### （二）MainActivity 实现

创建 MainActivity 布局  
在 activity_main.xml 中添加两个跳转按钮：  

- “跳转到 SecondActivity”  
- “跳转到 Dialog Activity”  

![alt text](photo\image.png)

实现 MainActivity 生命周期方法  
重写所有生命周期方法并添加 Log 输出：  

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(TAG, "MainActivity - onCreate");
    
    // 初始化按钮和点击事件
    initViews();
}

@Override
protected void onStart() {
    super.onStart();
    Log.d(TAG, "MainActivity - onStart");
}
// 其他生命周期方法
```
![alt text](photo\image-1.png) 

### （三）SecondActivity 实现

创建 SecondActivity 布局  
设计简单的界面显示当前为 SecondActivity  

![alt text](photo\image-4.png)  

实现 SecondActivity 生命周期方法  
同样重写所有生命周期方法：  

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    Log.d(TAG, "SecondActivity - onCreate");
}

@Override
protected void onStart() {
    super.onStart();
    Log.d(TAG, "SecondActivity - onStart");
}
```
 

### （四）DialogActivity 实现

创建 DialogActivity 布局  
使用对话框样式，设置固定尺寸：  

```xml
android:layout_width="300dp"
android:layout_height="200dp"
```
![alt text](photo\image-3.png)

实现 DialogActivity 生命周期方法  
重写所有生命周期方法：  

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog);
    Log.d(TAG, "DialogActivity - onCreate");
}
```
![alt text](photo\image-2.png)

## 五、实验结果与数据分析

### 实验场景记录

| 场景 | MainActivity 生命周期顺序 | 目标 Activity 生命周期顺序 |
|------|----------------------------|-----------------------------|
| 应用启动 | onCreate → onStart → onResume | 无 |
| Main → SecondActivity | onPause → onStop | onCreate → onStart → onResume |
| SecondActivity 返回 | onRestart → onStart → onResume | onPause → onStop → onDestroy |
| Main → Dialog Activity | onPause | onCreate → onStart → onResume |
| Dialog Activity 返回 | onResume | onPause → onStop → onDestroy |

### 生命周期观察截图
![alt text](photo\9506b01dd8f665b856370557b15ea2f8.png)

### 关键发现

**普通 Activity 跳转：**  
- 原 Activity 先执行 onPause，然后新 Activity 执行完整创建流程  
- 最后原 Activity 执行 onStop  
- 返回时原 Activity 需要重新 onRestart  

**Dialog Activity 特殊性：**  
- 跳转时 MainActivity 只执行 onPause，不执行 onStop  
- 返回时 MainActivity 直接执行 onResume  
- 体现了对话框不会完全覆盖底层 Activity 的特性  

## 六、核心代码展示

### MainActivity 关键代码

```java
// 跳转到 SecondActivity
btnToSecond.setOnClickListener {
    val intent = Intent(this, SecondActivity::class.java)
    startActivity(intent)
}

// 跳转到 DialogActivity  
btnToDialog.setOnClickListener {
    val intent = Intent(this, DialogActivity::class.java)
    startActivity(intent)
}
```

### 完整的生命周期方法实现

```java
@Override
protected void onPause() {
    super.onPause();
    Log.d(TAG, "MainActivity - onPause");
}

@Override
protected void onStop() {
    super.onStop();
    Log.d(TAG, "MainActivity - onStop");
}

@Override
protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "MainActivity - onDestroy");
}

@Override
protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "MainActivity - onRestart");
}
```

## 七、遇到的问题及解决方案

**问题1：布局文件编译错误**  
- 现象：XML 文件中的 URL 格式错误导致预览失败  
- 解决方案：修正命名空间 URL，确保使用正确的格式：  
  ```xml
  xmlns:android="http://schemas.android.com/apk/res/android"
  ```

**问题2：Dialog Activity 主题配置**  
- 现象：DialogActivity 没有显示为对话框样式  
- 解决方案：在 AndroidManifest.xml 中正确配置主题：  
  ```xml
  <activity
      android:name=".DialogActivity"
      android:theme="@style/DialogTheme"
      android:exported="false" />
  ```

**问题3：API 版本兼容性**  
- 现象：构建时出现依赖版本冲突  
- 解决方案：调整 compileSdk 和 targetSdk 版本至 34，确保兼容性  

## 八、实验总结

通过本次 Activity 生命周期观察实验，我深入理解了：  

1. **生命周期方法的调用顺序**：掌握了 onCreate、onStart、onResume、onPause、onStop、onDestroy、onRestart 的准确调用时机和顺序  
2. **不同跳转场景的差异**：  
   - 普通 Activity 跳转会导致原 Activity 完全停止  
   - Dialog Activity 跳转时原 Activity 仅暂停而不停止  
   - 这种差异影响了用户体验和资源管理策略  

本次实验为后续学习 Fragment 生命周期、Service 生命周期等 Android 组件生命周期管理奠定了坚实基础。
