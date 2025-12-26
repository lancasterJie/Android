package com.example.test;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.health.connect.datatypes.ActivityIntensityRecord;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
    TextView tv;
    public static final String TAG = "liMain" ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101)
        {
            if(resultCode== MainActivity.RESULT_CANCELED){
                Toast.makeText(this, "用户取消了操作", Toast.LENGTH_SHORT).show();
            }else if(resultCode == 3)
            {
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);
            }
        }
    }//有返回值的跳转
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        class AirplaneModeReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "关闭飞行模式", Toast.LENGTH_LONG).show();
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        AirplaneModeReceiver airplaneModeReceiver = new AirplaneModeReceiver();

        registerReceiver(airplaneModeReceiver,intentFilter);
        class MyListener1 implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button1) {
                    Intent it = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(it);
                }
                else if(view.getId() == R.id.button4){
                    Intent itresult = new Intent(MainActivity.this, ThirdActivity.class);
                    startActivityForResult(itresult,101);
                }
                else if(view.getId() == R.id.button5){
                    Intent it = new Intent(MainActivity.this,DialogActivity.class);
                    startActivity(it);
                }
                else if(view.getId() == R.id.frag_button){
                    Intent it = new Intent(MainActivity.this,FragmentActivity.class);
                    startActivity(it);
                }else if(view.getId() == R.id.bundle_button){
                    Intent it = new Intent(MainActivity.this,BundleActivity.class);
                    startActivity(it);
                }else if(view.getId() == R.id.rotate_button){
                    Intent it = new Intent(MainActivity.this,RotateActivity.class);
                    startActivity(it);
                }else if(view.getId() == R.id.file){
                    Intent it = new Intent(MainActivity.this,FileActivity.class);
                    startActivity(it);
                }else if(view.getId() == R.id.button_s){
                    Intent it = new Intent(MainActivity.this,SharedActivity.class);
                    startActivity(it);
                }
            }
        }
        MyListener1 ml = new MyListener1();
        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(ml);
        b10 = findViewById(R.id.button_s);
        b10.setOnClickListener(ml);
        b2 = findViewById(R.id.button4);
        b2.setOnClickListener(ml);
        b2.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View view) {
                // 显示Toast提示
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转!", Toast.LENGTH_SHORT).show();

                // 启动ThirdActivity并期待返回结果
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, 101);

                return true; // 返回true表示已处理长按事件
            }
        });
        tv = findViewById(R.id.backtext);
        b3 = findViewById(R.id.button5);
        b3.setOnClickListener(ml);

        LinearLayout l = findViewById(R.id.main);
        b4 = new Button(this);
        b4.setText("Java Button");
        l.addView(b4);
        b5 = findViewById(R.id.frag_button);
        b5.setOnClickListener(ml);
        b6 = findViewById(R.id.bundle_button);
        b6.setOnClickListener(ml);
        b7 = findViewById(R.id.rotate_button);
        b7.setOnClickListener(ml);
        b8 = findViewById(R.id.file);
        b8.setOnClickListener(ml);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        b9 = (Button)findViewById(R.id.broadcast);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.test.localbroadcast");
                localBroadcastManager.sendBroadcast(intent);
                intent.setComponent(new ComponentName("com.example.test",
                        "com.example.test.MyReceiver"));
                sendBroadcast(intent,null);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}
