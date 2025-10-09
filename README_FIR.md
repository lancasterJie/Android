显示跳转：使用显式 Intent new Intent(MainActivity.this, SecondActivity.class) 启动 SecondActivity.使用 finish() 方法结束当前 Activity，返回到 MainActivity.
隐式跳转：在 SecondActivity 中定义自定义 Action：ACTION_VIEW_THIRD_ACTIVITY，创建隐式 Intent，设置 Action 和 Category，在 AndroidManifest.xml 中为 ThirdActivity 配置对应的 Intent Filter，Action: 自定义的字符串，标识要执行的操作，Category: Intent.CATEGORY_DEFAULT，表示这是一个默认的 Activity 类别，Intent Filter: 在 Manifest 中声明，告诉系统该 Activity 可以响应哪些隐式 Intent。
带返回值的跳转：intent: 启动目标 Activity 的 Intent，requestCode: 请求标识码，用于区分不同的启动请求，resultCode: 结果代码，RESULT_OK: 操作成功，RESULT_CANCELED: 操作取消，自定义结果码（≥0），data: 包含返回数据的 Intent。
