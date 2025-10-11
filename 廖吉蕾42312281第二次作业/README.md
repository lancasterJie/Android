Activity的生命周期：
1.Running，
2.Paused,可被看见
3.Stop，不可被看见
4.Killed，
生命周期回调：
1.假设当前 Activity A 正在运行（即处于 onResume 状态），用户点击按钮启动 Activity B。调用顺序如下：A.onPause()，Activity A 仍然部分可见。此时B.onCreate()，B.onStart()，B.onResume()，此时，Activity B 已经创建完成并进入前台，A.onStop()，由于 B 已经完全覆盖了 A，系统调用此方法，A 变得完全不可见。此时可以释放一些资源。最终状态：Activity A: 处于 Stopped 状态（在后台）。Activity B: 处于 Resumed 状态（在前台，用户可交互）。
2.用户在当前 Activity B 界面，按下返回键。调用顺序如下：B.onPause()，A.onRestart()，只有当 Activity 是从 Stopped 状态恢复时，才会调用此方法。如果是首次创建，则不会调用。A.onStart()，A.onResume()，此时，Activity A 再次进入前台。B.onStop()，B.onDestroy()，由于用户主动返回，系统认为不再需要 Activity B，因此会将其销毁。最终状态：Activity B: 被销毁，Activity A: 处于 Resumed 状态（在前台，用户可交互）。
3.如果 Activity B 是透明或对话框的（DialogActivity），它不会完全覆盖 Activity A。调用顺序如下：启动 B 时：A.onPause()，B.onCreate()，B.onStart()，B.onResume()，从 B 返回时：B.onPause()，A.onResume()，B.onStop()，B.onDestroy()
