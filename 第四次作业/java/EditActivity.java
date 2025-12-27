package com.example.myproject4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditActivity extends AppCompatActivity {

    Button b1;

    Button b2;

    EditText edit_title;

    EditText edit_content;

    EditText edit_time;

    MyDbHelper dbHelper;

    private Record currentRecord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1=findViewById(R.id.edit_button);
        b2=findViewById(R.id.取消);
        edit_title=findViewById(R.id.edit_title);
        edit_content=findViewById(R.id.edit_content);
        edit_time=findViewById(R.id.edit_time);
        dbHelper=new MyDbHelper(this);

        Intent intent = getIntent();
        int recordId = intent.getIntExtra("record_id", -1);

        if(recordId!=-1)
        {
            loadRecordData(recordId);
            currentRecord=dbHelper.getRecordById(recordId);
        }
        else
        {
            edit_title.setText("没有信息");
            edit_content.setText("没有信息");
            edit_time.setText("没有信息");
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRecord();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadRecordData(int recordID)//显示详情信息
    {
        Record record = dbHelper.getRecordById(recordID);

        if(record!=null)
        {
            edit_title.setText(record.getTitle());
            edit_content.setText(record.getContent());
            edit_time.setText(record.getTime());
        }
        else
        {
            edit_title.setText("没有信息");
            edit_content.setText("没有信息");
            edit_time.setText("没有信息");
        }
    }

    private void editRecord()
    {
        String new_title=edit_title.getText().toString();
        String new_content=edit_content.getText().toString();

        if(!new_title.isEmpty()&&!new_content.isEmpty())
        {
            if(currentRecord!=null)
            {
                currentRecord.setTitle(new_title);
                currentRecord.setContent(new_content);

                int result = dbHelper.updateRecord(currentRecord);

                if(result>0)
                {
                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else
                {
                    Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

}
