package com.example.myproject3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button b1;

    Button b2;

    EditText edit;

    Button b3;

    Button b4;

    EditText number1;

    EditText number2;

    TextView result;

    public static final String TAG="WeiLongbao";


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key",edit.getText().toString());
        Log.d(TAG,"onSaveInstanceState");
    }

    @SuppressLint("MissingInflatedId")
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

        class MyListener implements View.OnClickListener
        {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.fragment)
                {
                    Intent fragment=new Intent(MainActivity.this,FragmentActivity.class);
                    startActivity(fragment);
                }
                else if (view.getId()==R.id.detail)
                {
                    Intent detail=new Intent(MainActivity.this,DetailActivity.class);
                    Bundle bundle=new Bundle();

                    bundle.putString("student_name","韦龙宝");
                    bundle.putInt("student_age",3);
                    bundle.putString("is_student","Yes");

                    detail.putExtras(bundle);

                    startActivity(detail);
                }
                else if (view.getId()==R.id.to_fragment)
                {
                    sendDataToFragment();
                }
                else if (view.getId()==R.id.change_activity)
                {
                    Intent change=new Intent(MainActivity.this,changeActivity.class);
                    startActivity(change);
                }
            }
        }

        b1=findViewById(R.id.fragment);
        b1.setOnClickListener(new MyListener());
        b2=findViewById(R.id.detail);
        b2.setOnClickListener(new MyListener());
        edit=findViewById(R.id.edit);
        b3=findViewById(R.id.to_fragment);
        b3.setOnClickListener(new MyListener());
        b4=findViewById(R.id.change_activity);
        b4.setOnClickListener(new MyListener());
        number1=findViewById(R.id.number1);
        number2=findViewById(R.id.number2);
        result=findViewById(R.id.result_view);

        Log.d(TAG,"onCreat");

        if(savedInstanceState!=null)
        {
            String savedText=savedInstanceState.getString("key");
            edit.setText(savedText);
            Log.d(TAG,"恢复文本");
        }
    }

    private void sendDataToFragment()
    {
        String num1=number1.getText().toString();
        String num2=number2.getText().toString();

        if(num1.isEmpty()||num2.isEmpty())
        {
            Toast.makeText(this, "请输入两个数字", Toast.LENGTH_SHORT).show();
            return;
        }

        int data1=Integer.parseInt(num1);
        int data2=Integer.parseInt(num2);

        loadFragmentWithData(data1, data2);
        Toast.makeText(this, "数据已发送到Fragment", Toast.LENGTH_SHORT).show();
    }

    private void loadFragmentWithData(int d1,int d2)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("number1", d1);
        bundle.putInt("number2", d2);

        changeFragment1 fragment = new changeFragment1();
        fragment.setArguments(bundle);//进行数据传递

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_result, fragment)
                .commit();
    }

    public void onCalculationResult(int data) {
        String resultText = "计算结果: " + data;
        result.setText(resultText);
        Toast.makeText(this, "收到Fragment返回的计算结果: " + result, Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }
}
