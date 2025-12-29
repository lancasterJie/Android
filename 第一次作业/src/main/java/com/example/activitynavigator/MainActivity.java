package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b4;
    TextView tv;//用于最终显示返回的结果

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
        tv=findViewById(R.id.tv);
        b1=findViewById(R.id.button1);
        b1.setOnClickListener(new MyListener());
        b4=findViewById(R.id.button4);
        b4.setOnClickListener(new MyListener());
        b4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动带结果的跳转", Toast.LENGTH_SHORT).show();
                return true; // 阻止长按后触发点击事件
            }
        });
    }
    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(view.getId()==R.id.button1){
                Intent it=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
            } else if (view.getId()==R.id.button4) {
                Intent it=new Intent(MainActivity.this,ThirdActivity.class);
                startActivityForResult(it,101);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) { // 确认是ThirdActivity的返回
            if (resultCode == 3) {
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);
            } else if (resultCode == 4) { // 新增：处理取消
                tv.setText("操作已取消"); // 显示取消提示
            }
        }
    }
}