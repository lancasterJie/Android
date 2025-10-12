操作图如下：

<img title="" src="file:///C:/Users/Koreyoshi/Desktop/2025-10-12-22-16-31-image.png" alt="" width="309" data-align="left">                 <img title="" src="file:///C:/Users/Koreyoshi/Desktop/Screenshot%202025-10-12%20223606.png" alt="" width="305" data-align="left">              <img title="" src="file:///C:/Users/Koreyoshi/Desktop/Screenshot%202025-10-12%20223656.png" alt="" width="305" data-align="left">

| 场景                    | MainActivity生命周期顺序           | 目标Activity生命周期顺序            |
| --------------------- |:----------------------------:|:---------------------------:|
| 应用启动                  | onCreate--onStart--onResume  | 无                           |
| Main--SecondActvity   | onPause--onStop              | onCreate--onStart--onResume |
| SecondActivity返回      | onRestart--onStart--onResume | onPause--onStop--onDestroy  |
| Main--Dialog Activity | onPause                      | onCreate--onStart--onResume |
| Dialog Activity 返回    | onResume                     | onPause--onStop--onDestroy  |

##### 
