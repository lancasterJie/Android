

                        

| 场景                    | MainActivity生命周期顺序           | 目标Activity生命周期顺序            |
| --------------------- |:----------------------------:|:---------------------------:|
| 应用启动                  | onCreate--onStart--onResume  | 无                           |
| Main--SecondActvity   | onPause--onStop              | onCreate--onStart--onResume |
| SecondActivity返回      | onRestart--onStart--onResume | onPause--onStop--onDestroy  |
| Main--Dialog Activity | onPause                      | onCreate--onStart--onResume |
| Dialog Activity 返回    | onResume                     | onPause--onStop--onDestroy  |

##### 
