一.数据记录与分析：

图片：！\[应用启动时的Log输出](1.png)

1.应用启动
MainActivity 生命周期顺序	：onCreate→onStart→onResume

2.Main → SecondActivity
MainActivity 生命周期顺序：onPause→onStop
SecondActivity 生命周期顺序：onCreate→onStart→onResume

3.SecondActivity 返回
MainActivity 生命周期顺序：onStart→onResume
SecondActivity 生命周期顺序：onPause→onStop→onDestroy

4.Main →FourthActivity
MainActivity 生命周期顺序：onPause
FourthActivity 生命周期顺序：onCreate→onStart→onResume

5.FourthActivity 返回
MainActivity 生命周期顺序：onResume
FourthActivity 生命周期顺序：onPause→onStop→onDestroy

二、FourthActivity-设置对话框形式

图片：！\[对话框形式的activity](2.png)

思路：
首先创建fourth activity，然后在其布局文件中添加文本显示这是对话框形式。
在main的布局文件中设置按钮4用于跳转fourth activity，并在其代码文件中设置按钮4的监听器并显式调用

