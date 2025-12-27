package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b1;

    Button b2;

    TextView text;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//重写的 onActivityResult(int requestcode, int resultcode, Intent data)方法
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101)
        {
            if(resultCode==ThirdActivity.RESULT_OK)//点击“返回结果”按钮
            {
                String result=data.getStringExtra("result");
                text.setText(result);
            }
            else if (resultCode==ThirdActivity.RESULT_CANCELED)//点击“返回取消”按钮
            {
                Toast.makeText(MainActivity.this,"用户取消操作",Toast.LENGTH_LONG).show();
                text.setText("用户取消操作");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        class MyListener implements View.OnClickListener,View.OnLongClickListener
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.SecondActivity)//使用 显式Intent （ Intent(Context packageContext, Class<?> cls)）启动 SecondActivity
                {
                    Intent itSecondActivity = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(itSecondActivity);
                }
                else if (view.getId()==R.id.启动带结果的跳转)//“启动带结果的跳转”按钮的点击事件，使用 startActivityForResult()方法设置请求码
                {
                    Intent itresult = new Intent(MainActivity.this,ThirdActivity.class);
                    startActivityForResult(itresult,101);
                }
            }

            @Override
            public boolean onLongClick(View view)//在 MainActivity 中，为“启动带结果的跳转”按钮添加一个长按监听器,长按按钮时，弹出一个 Toast 提示用户"长按启动了带返回结果的跳转!"
            {
                if(view.getId()==R.id.启动带结果的跳转)
                {
                    Toast.makeText(MainActivity.this,"长按启动了带返回结果的跳转！",Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }

        }

        b1 = findViewById(R.id.SecondActivity);
        b1.setOnClickListener(new MyListener());
        b2 = findViewById(R.id.启动带结果的跳转);
        b2.setOnClickListener(new MyListener());
        b2.setOnLongClickListener(new MyListener());//为“启动带结果的跳转”按钮设置长按监听器
        text=findViewById(R.id.返回结果);
    }
}
