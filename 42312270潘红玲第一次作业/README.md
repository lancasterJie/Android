# 显式跳转、隐式跳转、带返回结果的跳转

## 1. 显式跳转

首先在 `MainActivity` 中创建按钮 `JumpButton1`，为其设置点击事件监听器，并通过如下语句创建显示 Intent：

```java
intent.setClass(MainActivity.this, SecondActivity.class);
```

同时在 `SecondActivity` 中创建按钮 `BackButton1`，为其设置点击事件监听按钮，调用 `finish()` 函数，点击按钮结束 `SecondActivity`，返回到 `MainActivity`。

---

## 2. 隐式跳转

在 `SecondActivity` 中创建 `ImplicitButton`，为其设置点击事件监听器，创建隐式 Intent，通过如下语句设置 action 和 category：

```java
intent.setAction("com.example.action.VIEW_THIRD_ACTIVITY");
intent.addCategory(Intent.CATEGORY_DEFAULT);
```

在 `AndroidManifest.xml` 中为 `ThirdActivity` 添加 `<intent-filter>`：

```xml
<intent-filter>
    <action android:name="com.example.action.VIEW_THIRD_ACTIVITY" />
    <category android:name="android.intent.category.DEFAULT" />
</intent-filter>
```

---

## 3. 带返回结果的跳转

首先在 `MainActivity` 中定义请求码：

```java
private static final int REQUEST_CODE_THIRD = 101;
```

在 `MainActivity` 中创建按钮 `JumpButton2` 和一个 `TextView` 命名为 `tvResult` 用来显示返回的结果；  
在 `ThirdActivity` 中创建返回结果按钮 `ReturnResultButton` 和 `EditText`，命名为 `etInput` 用来保存用户输入的信息。

为 `JumpButton2` 设置一个点击事件监听器，点击按钮通过下列语句启动 `ThirdActivity`：

```java
startActivityForResult(intent, REQUEST_CODE_THIRD);
```

在 `ThirdActivity` 中，为 `ReturnResultButton` 设置点击事件监听器，在 `onClick` 函数中创建 `inputText` 用来接收用户输入的信息，并创建数据 `Intent`，通过 `putExtra` 函数保存信息，通过 `setResult` 函数设置返回结果，然后调用 `finish()` 结束该活动。

接着系统会调用 `MainActivity` 中的 `onActivityResult(...)` 方法，在此方法中通过对比请求码和结果码确定得到正确对应的返回数据，同时提取返回数据并更新 `tvResult`。

---

## 4. 加分项

在 `MainActivity` 中，为 `JumpButton2` 添加一个新的长按点击事件监听器，调用 `setOnLongClickListener` 方法，在方法中传入一个匿名内部类，重写 `onLongClick` 方法，使其在内部调用下列语句：

```java
Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转", Toast.LENGTH_SHORT).show();
```

在 `ThirdActivity` 中添加一个 `ReturnCancel` 按钮，为按钮添加点击事件监听器，点击事件发生时调用方法：

```java
setResult(Activity.RESULT_CANCELED);
```

同时在 `MainActivity` 中，改写 `onActivityResult` 方法，增加一个选择逻辑：  
当结果码为 `RESULT_OK` 时，正常返回；  
当结果码为 `RESULT_CANCELED` 时，更新 `tvResult` 为 **“用户取消了操作”**。
