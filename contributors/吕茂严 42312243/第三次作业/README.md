# 1. RadioGroup 控制 Fragment 切换
## 要求
- 创建包含 4 个 RadioButton 的 RadioGroup
- 每个 RadioButton 对应一个不同的 Fragment
- 点击 RadioButton 时切换到对应的 Fragment
- 每个 Fragment 要有独特的 UI 和功能区分
- res/drawable/radio_button_selector.xml 定义背景选择器，定义 radiobutton 是否 check 的状态
- 在 style 中定义 radiobutton 统一样式

## MainActivity 代码
```java
private List<Fragment> fragmentList;
private FragmentManager fragmentManager;
private OneFragment oneFragment;
private TwoFragment twoFragment;
private ThreeFragment threeFragment;
private FourFragment fourFragment;
RadioGroup radioGroup;
Bundle msg = new Bundle();
Bundle msgForTwo = new Bundle();
private static final String TAG = "MainActivity";
private EditText editText;
private TextView tv;

@SuppressLint("MissingInflatedId")
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    radioGroup = findViewById(R.id.radioGroup);
    editText = findViewById(R.id.editTextText);
    tv = findViewById(R.id.tv);

    oneFragment = new OneFragment();
    twoFragment = new TwoFragment();
    threeFragment = new ThreeFragment();
    fourFragment = new FourFragment();

    fragmentList = new ArrayList<>();
    fragmentList.add(oneFragment);
    fragmentList.add(twoFragment);
    fragmentList.add(threeFragment);
    fragmentList.add(fourFragment);

    fragmentManager = getSupportFragmentManager();

    // 初始化默认显示第一个 Fragment
    if (savedInstanceState == null) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, oneFragment);
        fragmentTransaction.commit();
    } else {
        // 屏幕旋转后恢复数据
        String savedText = savedInstanceState.getString("editText");
        tv.setText(savedText);
        int checkedRadioButtonId = savedInstanceState.getInt("checkedRadioButtonId", -1);
        if (checkedRadioButtonId != -1) {
            radioGroup.check(checkedRadioButtonId);
        }
    }

    // RadioGroup 选择事件监听
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d(TAG, "onCheckedChanged: checkedId = " + checkedId);

            if (checkedId == R.id.radioButtonOne) {
                msg.putString("msg", "传入fragmentOne");
                oneFragment.setArguments(msg);

                oneFragment.setResultListener((isSuccess, resultMsg) -> {
                    if (isSuccess) {
                        ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                        msgForTwo.putString("fragment", "将传入fragmentOne数据传入fragmentTwo");
                    } else {
                        Toast.makeText(MainActivity.this, "失败：" + resultMsg, Toast.LENGTH_SHORT).show();
                    }
                });
                hideOtherFragment(oneFragment);
            } else if (checkedId == R.id.radioButtonTwo) {
                // 优先使用 msgForTwo 中的数据（来自 FragmentOne 的传递）
                if (msgForTwo.getString("fragment") != null) {
                    Log.d("fragment", "非空的");
                    twoFragment.setArguments(msgForTwo);
                } else {
                    Log.d("fragment", "空的");
                    msg.putString("msg", "传入fragmentTwo");
                    twoFragment.setArguments(msg);
                }

                hideOtherFragment(twoFragment);
                twoFragment.setResultListener((isSuccess, resultMsg) -> {
                    if (isSuccess && resultMsg != null) {
                        ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                    } else {
                        ((TextView) findViewById(R.id.tv)).setText("未传入");
                    }
                });
            } else if (checkedId == R.id.radioButtonThree) {
                msg.putString("msg", "传入fragmentThree");
                threeFragment.setArguments(msg);
                hideOtherFragment(threeFragment);
                threeFragment.setResultListener((isSuccess, resultMsg) -> {
                    if (isSuccess) {
                        ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                    } else {
                        Toast.makeText(MainActivity.this, "失败：" + resultMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (checkedId == R.id.radioButtonFour) {
                msg.putString("msg", "传入fragmentFour");
                fourFragment.setArguments(msg);
                hideOtherFragment(fourFragment);
                fourFragment.setResultListener((isSuccess, resultMsg) -> {
                    if (isSuccess) {
                        ((TextView) findViewById(R.id.tv)).setText("已" + resultMsg);
                    } else {
                        Toast.makeText(MainActivity.this, "失败：" + resultMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    });

    // 按钮点击事件监听（用于 Activity 跳转传递数据）
    findViewById(R.id.button_sedmsg).setOnClickListener(new MyListener1());
}

// 隐藏其他 Fragment，显示目标 Fragment
public void hideOtherFragment(Fragment fragmentToShow) {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    if (!fragmentToShow.isAdded()) {
        fragmentTransaction.add(R.id.fragmentContainer, fragmentToShow);
    }

    for (Fragment fragment : fragmentList) {
        if (fragment != fragmentToShow) {
            fragmentTransaction.hide(fragment);
        } else {
            fragmentTransaction.show(fragmentToShow);
            Log.d(TAG, "hideOtherFragment: fragmentToShow = " + fragmentToShow);
        }
    }

    fragmentTransaction.commit();
}

// 屏幕旋转状态保存
@Override
protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("editText", editText.getText().toString());
    outState.putInt("checkedRadioButtonId", radioGroup.getCheckedRadioButtonId());
    Log.d(TAG, "onSaveInstanceState");
}

// Activity 跳转传递数据的点击监听
class MyListener1 implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_sedmsg) {
            Intent it = new Intent(MainActivity.this, DetailActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("name", "张三");
            bundle.putInt("age", 20);
            bundle.putBoolean("isStudent", true);

            it.putExtras(bundle);
            startActivity(it);
        }
    }
}
```

## 布局文件（activity_main.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button 1" />

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button 2" />

    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button browser" />

    <Button
        android:id="@+id/button4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button implicit" />

    <TextView
        android:id="@+id/tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:text="MainActivity"
        android:textSize="30dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/button5"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button result" />

    <Button
        android:id="@+id/button6"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button result" />

    <Button
        android:id="@+id/button_sedmsg"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="SendMsg" />

    <EditText
        android:id="@+id/editTextText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="text"
        android:text="Name" />

    <RadioGroup
        android:id="@+id/radioGroup"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <RadioButton
            android:id="@+id/radioButtonOne"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="One"
            android:layout_weight="0.25"
            style="@style/RadioButtonStyle" />

        <RadioButton
            android:id="@+id/radioButtonTwo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Two"
            android:layout_weight="0.25"
            style="@style/RadioButtonStyle" />

        <RadioButton
            android:id="@+id/radioButtonThree"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Three"
            android:layout_weight="0.25"
            style="@style/RadioButtonStyle" />

        <RadioButton
            android:id="@+id/radioButtonFour"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Four"
            android:layout_weight="0.25"
            style="@style/RadioButtonStyle" />
    </RadioGroup>

    <FrameLayout
        android:id="@+id/fragmentContainer"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

</LinearLayout>
```

## Fragment 布局文件
### FragmentOne（fragment_one.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#000000"
    tools:context=".OneFragment">

    <Button
        android:id="@+id/btn_submit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:textColor="@color/white"
        android:text="hello_blank_One_Fragment" />

</FrameLayout>
```

### FragmentTwo（fragment_two.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#00FF00"
    android:orientation="vertical"
    tools:context=".TwoFragment">

    <Button
        android:id="@+id/btn_submit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/black"
        android:layout_gravity="bottom"
        android:text="hello_blank_Two_Fragment" />

    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="未传入"
        android:textColor="@color/black" />

</FrameLayout>
```

### FragmentThree（fragment_three.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFFFFF"
    tools:context=".ThreeFragment">

    <Button
        android:id="@+id/btn_submit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:textColor="@color/black"
        android:text="hello_blank_Three_Fragment" />

</FrameLayout>
```

### FragmentFour（fragment_four.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FF0000"
    tools:context=".FourFragment">

    <Button
        android:id="@+id/btn_submit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        android:textColor="@color/black"
        android:text="hello_blank_Four_Fragment" />

</FrameLayout>
```

## Fragment 通用 Java 代码（以 OneFragment 为例）
```java
public class OneFragment extends Fragment {
    private String message;
    private OnFragmentResultListener mResultListener;

    // 回调接口，用于向 Activity 返回信息
    public interface OnFragmentResultListener {
        void onResult(Boolean isSuccess, String message);
    }

    // 设置回调监听
    public void setResultListener(OnFragmentResultListener listener) {
        this.mResultListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        view.findViewById(R.id.btn_submit).setOnClickListener(v -> handleBusiness());
        return view;
    }

    // 业务逻辑处理，向 Activity 返回结果
    private void handleBusiness() {
        boolean isSuccess = true;
        Bundle bundle = getArguments();
        if (bundle != null) {
            message = bundle.getString("msg");
        }
        if (mResultListener != null) {
            mResultListener.onResult(isSuccess, message);
        }
    }

    // 解除回调引用，避免内存泄漏
    @Override
    public void onDetach() {
        super.onDetach();
        mResultListener = null;
    }
}
```

> 说明：TwoFragment、ThreeFragment、FourFragment 代码与 OneFragment 一致，仅需修改 `inflater.inflate()` 中的布局文件（如 `R.layout.fragment_two`）。

## 样式与选择器配置
### RadioButton 样式（res/values/styles.xml）
```xml
<style name="RadioButtonStyle" parent="Widget.AppCompat.CompoundButton.RadioButton">
    <item name="android:textSize">16sp</item>
    <item name="android:gravity">center</item>
    <item name="android:button">@drawable/radio_button_selector</item>
</style>
```

### RadioButton 背景选择器（res/drawable/radio_button_selector.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 选中状态 -->
    <item android:state_checked="true">
        <shape android:shape="oval">
            <solid android:color="#FF0000" />
            <size android:width="20dp" android:height="20dp" />
        </shape>
    </item>
    <!-- 未选中状态 -->
    <item android:state_checked="false">
        <shape android:shape="oval">
            <stroke android:width="2dp" android:color="#CCCCCC" />
            <size android:width="20dp" android:height="20dp" />
        </shape>
    </item>
</selector>
```

# 2. Bundle 数据传输
## 场景 A: Activity → Activity 数据传输
### 功能描述
从 MainActivity 传递用户数据到 DetailActivity，包含：用户名、年龄、是否学生等信息。

### MainActivity 相关代码（已整合到上方 MainActivity 完整代码中）
核心逻辑：通过 Intent 携带 Bundle 传递数据，触发方式为 `button_sedmsg` 按钮点击。

### DetailActivity Java 代码
```java
package com.example.myapplication1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_msg = findViewById(R.id.tv_msg);

        // 接收 MainActivity 传递的数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("name", "默认名称");
            int age = bundle.getInt("age", 0);
            boolean isStudent = bundle.getBoolean("isStudent", false);

            // 展示数据
            if (isStudent) {
                tv_msg.setText("姓名：" + name + "，年龄：" + age + "，学生");
            } else {
                tv_msg.setText("姓名：" + name + "，年龄：" + age + "，非学生");
            }
        }
    }
}
```

### DetailActivity 布局（activity_detail.xml）
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <TextView
        android:id="@+id/tv_msg"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="20sp" />

</LinearLayout>
```

## 场景 B: Activity ↔ Fragment 数据传输
### 功能描述
MainActivity 向 Fragment 传递初始数据，Fragment 向 Activity 返回处理结果。

### 实现说明
- 数据传递：MainActivity 通过 `setArguments(bundle)` 向 Fragment 传递数据
- 结果返回：Fragment 通过自定义回调接口 `OnFragmentResultListener` 向 Activity 返回结果
- 核心代码已整合到上方 MainActivity 和 Fragment 完整代码中

## 场景 C: Fragment → Fragment 数据传输（通过 Activity 中转）
### 功能描述
通过 MainActivity 作为中转，实现 FragmentOne 向 FragmentTwo 传递数据。

### 实现逻辑
1. FragmentOne 处理完成后，通过回调向 MainActivity 返回结果
2. MainActivity 接收结果后，存入 `msgForTwo`  Bundle
3. 切换到 FragmentTwo 时，将 `msgForTwo` 作为参数传入
4. FragmentTwo 读取参数并展示在 TextView 上

### TwoFragment 关键修改（添加数据读取逻辑）
```java
public class TwoFragment extends Fragment {
    private String message;
    private String message_2;
    private OnFragmentResultListener mResultListener;
    private TextView tv;

    public interface OnFragmentResultListener {
        void onResult(Boolean isSuccess, String message);
    }

    public void setResultListener(OnFragmentResultListener listener) {
        this.mResultListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        tv = view.findViewById(R.id.tv);
        view.findViewById(R.id.btn_submit).setOnClickListener(v -> handleBusiness());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 读取传递的数据并展示
        readArgumentsAndShow();
    }

    // 读取参数并显示到 TextView
    private void readArgumentsAndShow() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            message = bundle.getString("msg");
            message_2 = bundle.getString("fragment");
            if (!TextUtils.isEmpty(message_2)) {
                tv.setText(message_2);
            } else if (!TextUtils.isEmpty(message)) {
                tv.setText(message);
            }
        }
    }

    private void handleBusiness() {
        boolean isSuccess = true;
        Bundle bundle = getArguments();
        if (bundle != null) {
            message = bundle.getString("msg");
            message_2 = bundle.getString("fragment");
        }
        // 优先返回 fragment 字段的数据（来自 FragmentOne）
        String resultMsg = TextUtils.isEmpty(message_2) ? message : message_2;
        if (mResultListener != null) {
            mResultListener.onResult(isSuccess, resultMsg);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mResultListener = null;
    }
}
```

# 3. 屏幕旋转与状态保存（onSaveInstanceState）
## 核心知识点
- onSaveInstanceState 调用时机：在 `onPause()` 之后、`onStop()` 之前
- 作用：保存临时状态数据，避免屏幕旋转、内存不足等场景下数据丢失

## 实现功能
在 onSaveInstanceState 中保存 EditText 内容和当前选中的 RadioButton，屏幕旋转后恢复数据并展示在 TextView 中。

## 关键代码（已整合到 MainActivity 完整代码中）
- 状态保存：`onSaveInstanceState` 方法中保存 EditText 文本和 RadioButton 选中状态
- 状态恢复：`onCreate` 方法中读取保存的数据，恢复 TextView 内容和 RadioButton 选中状态
