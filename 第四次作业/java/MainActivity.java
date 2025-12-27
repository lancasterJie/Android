package com.example.myproject4;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    Button b1;

    Button b2;

    EditText ed;

    TextView view;

    Toolbar toolbar;

    EditText my_content;

    Button b3;

    MyDbHelper myDbHelper;

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

        myDbHelper=new MyDbHelper(this);

        class MyListener1 implements View.OnClickListener {
            @Override
            public void onClick(View view)
            {
                if(view.getId()==R.id.保存到文件)
                {
                    save(ed.getText().toString());
                }
                else if (view.getId()==R.id.从文件加载)
                {
                    load();
                }
                else if(view.getId()==R.id.保存为记录)
                {
                    String content=my_content.getText().toString();

                    String title = content.length() > 10 ?
                            content.substring(0, 10) + "..." :
                            content;

                    if(!title.isEmpty()&&!content.isEmpty())
                    {
                        long id=myDbHelper.addrecord(title,content);
                        Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"保存失败",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        b1=findViewById(R.id.保存到文件);
        b1.setOnClickListener(new MyListener1());
        b2=findViewById(R.id.从文件加载);
        b2.setOnClickListener(new MyListener1());
        ed=findViewById(R.id.edit);
        view=findViewById(R.id.view);
        toolbar=findViewById(R.id.toolbar);
        my_content=findViewById(R.id.content);
        b3=findViewById(R.id.保存为记录);
        b3.setOnClickListener(new MyListener1());
        
        setSupportActionBar(toolbar);

    }
    public void save(String inputString)
    {
        FileOutputStream fos=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;

        try
        {
            fos=openFileOutput("note.txt",MODE_PRIVATE);
            osw=new OutputStreamWriter(fos);
            bw=new BufferedWriter(osw);
            osw.write(inputString);
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        try {
            bw.close();
            osw.close();
            fos.close();
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void load()
    {
        FileInputStream fis = null;
        BufferedReader reader = null;

        try
        {
            fis=openFileInput("note.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            if(stringBuilder.length() > 0)
            {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            view.setText(stringBuilder.toString());

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
            fis.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        update();
    }

    public void update()
    {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        String user=sp.getString("user_name","");

        if(user.isEmpty())
        {
            getSupportActionBar().setTitle("欢迎使用");
        }
        else if (!user.isEmpty())
        {
            getSupportActionBar().setTitle(user+"欢迎使用");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.setting)
        {
            Intent setting=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(setting);
            return true;
        }
        else if (id==R.id.record)
        {
            Intent record=new Intent(MainActivity.this,RecordListActivity.class);
            startActivity(record);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        my_content.setText("");
    }
}
