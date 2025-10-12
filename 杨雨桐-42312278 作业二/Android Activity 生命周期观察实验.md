# Android Activity 生命周期观察实验

### 任务

1. **创建主 Activity**
   
   - 创建 MainActivity，重写所有生命周期方法

   - 在每个生命周期方法中添加 Log 输出，格式：`Log.d("Lifecycle", "MainActivity - onCreate")`
   
     ```'''
     //activity在用户可见时调用
     @Override
     protected void onStart() {
         super.onStart();
         Log.d("Lifecycle","SecondActivity - onStart");
     }
     
     //和用户交互时调用
     @Override
     protected void onResume() {
         super.onResume();
         Log.d("LifeCycle","SecondActivity - onResume");
     }
     
     //其他activity获得焦点时调用
     @Override
     protected void onPause() {
         super.onPause();
         Log.d("LifeCycle","SecondActivity - onPause");
     }
     
     //activity不再可见时调用
     @Override
     protected void onStop() {
         super.onStop();
         Log.d("LifeCycle","SecondActivity - onStop");
     }
     
     //停止的activity重新启动时调用
     @Override
     protected void onRestart() {
         super.onRestart();
         Log.d("LifeCycle","SecondActivity - onRestart");
     }
     
     //被销毁前调用
     @Override
     protected void onDestroy() {
         super.onDestroy();
         Log.d("LifeCycle","SecondActivity - onDestroy");
     }
     ```
   
     
   
2. **创建普通 SecondActivity**
   - 创建第二个普通 Activity
   - 同样重写所有生命周期方法并添加 Log 输出

3. **创建 Dialog Activity**
   - 创建第三个 Activity，设置为主题为对话框样式
   - 重写所有生命周期方法并添加 Log 输出

### 实验步骤

#### 第一部分：基础生命周期观察

1. **启动应用观察**
   - 启动应用，观察 MainActivity 的生命周期调用顺序
   - 记录 Logcat 中的输出

   ![](.\1.png)
   
2. **普通 Activity 跳转**
   
   - 从 MainActivity 跳转到 SecondActivity
   
   ![](.\2.png)

   ![](E:\ch_cn\android\作业2\3.png)
   
   ![](.\4.png)
   
   
   
   在完成该任务时，发现resume运行时间不是在点击跳转按钮时，不符合预期。查询结果：
   
   <img src=".\resume不运行.png" style="zoom:67%;" />
   
3. **Dialog Activity 跳转**
   - 从 MainActivity 跳转到 Dialog Activity
   - 观察生命周期变化
   - 从 Dialog Activity 返回
   - 观察生命周期变化
   - ![](.\5.png)

#### 第二部分：数据记录与分析



记录以下场景的 Log 输出：

| 场景                   | MainActivity 生命周期顺序    | 目标Activity 生命周期顺序    |
| ---------------------- | ---------------------------- | ---------------------------- |
| 应用启动               | onCreate->onstart->onResume  |                              |
| Main → SecondActivity  | onCreate->onPuase->onstop    | onCreate->onStart->onREsume  |
| SecondActivity 返回    | onRestart->onStart->onResume | onPause->onStop-> onDestroy  |
| Main → Dialog Activity | onPause->                    | onCreate->onStart->onResume  |
| Dialog Activity 返回   | onResume                     | onPause->onResume->onDestroy |





