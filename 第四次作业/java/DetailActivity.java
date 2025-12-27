package com.example.myproject4;

import android.annotation.SuppressLint;
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

public class DetailActivity extends AppCompatActivity {

    TextView title;

    TextView content;

    TextView time;

    Button b1;

    Button b2;

    Button b3;

    MyDbHelper dbHelper;

    private Record currentRecord;

    @SuppressLint("MissingInflatedId")
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

        title=findViewById(R.id.title_detail);
        content=findViewById(R.id.content_detail);
        time=findViewById(R.id.time_detail);
        b1=findViewById(R.id.back);
        b2=findViewById(R.id.delete);
        b3=findViewById(R.id.edit);

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
            title.setText("没有信息");
            content.setText("没有信息");
            time.setText("没有信息");
        }

        b1.setOnClickListener(view -> {
            finish();
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCurrentRecord();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditPage();
            }
        });
    }

    private void loadRecordData(int recordID)//显示详情信息
    {
        Record record = dbHelper.getRecordById(recordID);

        if(record!=null)
        {
            title.setText(record.getTitle());
            content.setText(record.getContent());
            time.setText(record.getTime());
        }
        else
        {
            title.setText("没有信息");
            content.setText("没有信息");
            time.setText("没有信息");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    private void deleteCurrentRecord()//删除记录
    {
        if(currentRecord!=null)
        {
            int result= dbHelper.deleteRecord(currentRecord.getId());

            if(result>0)
            {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);

                finish();
            }
            else
            {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToEditPage()
    {
        if (currentRecord != null)
        {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("record_id", currentRecord.getId());

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "无法编辑，记录不存在", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentRecord.getId() != -1) {
            loadRecordData(currentRecord.getId());
        }
    }
}
