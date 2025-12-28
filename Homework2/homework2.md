## 检视生命周期如下

**1.当应用启动时**

```
2025-12-28 23:39:39.603  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onCreate
2025-12-28 23:39:39.822  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onStart
2025-12-28 23:39:39.831  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onResume
```

**2.跳转到secondactivity时**

```
2025-12-28 23:41:05.393  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onPause
2025-12-28 23:41:05.437  4792-4792  Lifecycle               com.zzx.homework2                    D  SecondActivity - onCreate
2025-12-28 23:41:05.453  4792-4792  Lifecycle               com.zzx.homework2                    D  SecondActivity - onStart
2025-12-28 23:41:05.456  4792-4792  Lifecycle               com.zzx.homework2                    D  SecondActivity - onResume
2025-12-28 23:41:06.783  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onStop
```

从second返回到main时

```
2025-12-28 23:41:41.731  4792-4792  Lifecycle               com.zzx.homework2                    D  SecondActivity - onPause
2025-12-28 23:41:41.737  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onRestart
2025-12-28 23:41:41.739  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onStart
2025-12-28 23:41:41.740  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onResume
2025-12-28 23:41:42.325  4792-4792  Lifecycle               com.zzx.homework2                    D  SecondActivity - onStop
2025-12-28 23:41:42.326  4792-4792  Lifecycle               com.zzx.homework2                    D  SecondActivity - onDestroy
```

**3.到dialog页面时**

```
2025-12-28 23:42:11.858  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onPause
2025-12-28 23:42:11.879  4792-4792  Lifecycle               com.zzx.homework2                    D  DialogActivity - onCreate
2025-12-28 23:42:11.885  4792-4792  Lifecycle               com.zzx.homework2                    D  DialogActivity - onStart
2025-12-28 23:42:11.886  4792-4792  Lifecycle               com.zzx.homework2                    D  DialogActivity - onResume
2025-12-28 23:42:12.443  4792-4792  Lifecycle               com.zzx.homework2                    D  MainActivity - onStop
```

退出时

```

```




