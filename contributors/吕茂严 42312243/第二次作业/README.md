# Android Activity 生命周期代码实现与数据分析
## 一、关键代码实现
### 1. MainActivity 关键代码
```java
public class MainActivity extends AppCompatActivity {
    public static String TAG = "Lifecycle";
    private Button b1, b6;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b2 = findViewById(R.id.button2);
        b6 = findViewById(R.id.button6);

        b1.setOnClickListener(new MyListener1());
        b6.setOnClickListener(new MyListener1());

        Log.d(TAG, "MainActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity - onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity - onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity - onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity - onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity - onDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            if (requestCode == 1) {
                assert data != null;
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);
            }
        }
    }

    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button1){
                Toast.makeText(MainActivity.this,"peace and love",Toast.LENGTH_LONG).show();
            }
            else if(view.getId() == R.id.button2){
                Intent it = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
            }
            else if (view.getId() == R.id.button6) {
                Intent it = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(it);
            }
        }
    }
}
```

### 2. SecondActivity 关键代码
```java
public class SecondActivity extends AppCompatActivity {
    public static String TAG = "Lifecycle";
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        b1 = findViewById(R.id.back);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        Log.d(TAG, "SecondActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "SecondActivity - onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SecondActivity - onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SecondActivity - onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "SecondActivity - onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "SecondActivity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SecondActivity - onDestroy");
    }
}
```

### 3. DialogActivity 关键代码
```java
public class DialogActivity extends AppCompatActivity {
    public static String TAG = "Lifecycle";
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        
        b1 = findViewById(R.id.back);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        Log.d(TAG, "DialogActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "DialogActivity - onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "DialogActivity - onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "DialogActivity - onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "DialogActivity - onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "DialogActivity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DialogActivity - onDestroy");
    }
}
```

## 二、数据记录与分析
| 场景                   | MainActivity 生命周期顺序      | 目标 Activity 生命周期顺序    |
| ---------------------- | ------------------------------ | ----------------------------- |
| 应用启动               | onCreate → onStart → onResume  | -                             |
| Main → SecondActivity  | onPause → onStop               | onCreate → onStart → onResume |
| SecondActivity 返回    | onRestart → onStart → onResume | onPause → onStop → onDestroy  |
| Main → Dialog Activity | onPause                        | onCreate → onStart → onResume |
| Dialog Activity 返回   | onResume                       | onPause → onStop → onDestroy  |
