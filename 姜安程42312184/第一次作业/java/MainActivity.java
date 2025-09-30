package com.example.ActivityNavigator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
    Button b1,b2,b3;
    TextView tv;

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
        b1= findViewById(R.id.button1);
        b2= findViewById(R.id.button2);
        b3= findViewById(R.id.button3);
        tv= findViewById(R.id.tv);

        MyListener1 nl=new MyListener1();
        b1.setOnClickListener(nl);
        b2.setOnClickListener(nl);
        b3.setOnClickListener(nl);
    }
    class MyListener1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.button1){
                Intent it=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
//                startActivity(new Intent("com.scott.intent.action.TARGET"));
//                startActivity(new Intent(MainActivity.this,MainActivity2.class));
//                Toast.makeText(MainActivity.this,"Hello", Toast.LENGTH_LONG).show();
            }
            else if(view.getId()==R.id.button2){
                Intent itimp=new Intent("com.package.helloword.thirdactivity");
                startActivity(itimp);
            }
            else if(view.getId()==R.id.button3){
                Intent itresult=new Intent(MainActivity.this,ThirdActivity.class);
                startActivityForResult(itresult,1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==3){
            if(requestCode==1){
                String resultstring = data.getStringExtra("result");
                tv.setText(resultstring);
            }
        }
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Toast.makeText(MainActivity.this,"peace and love",Toast.LENGTH_LONG).show();

        }
    }
}