package com.example.notepad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    MyDBHelper dpHelper;
    EditText edID, edTitle, edContent;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    TextView resultView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        setupBottomNavigation();

        dpHelper = new MyDBHelper(this);
        edID = findViewById(R.id.et_ID);  // 添加ID输入框引用
        edTitle = findViewById(R.id.et_title);
        edContent = findViewById(R.id.et_content);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            String title = edTitle.getText().toString();
            String content = edContent.getText().toString();
            if(!title.isEmpty() && !content.isEmpty()){
                try {
                    long id = dpHelper.addRecord(title, content);
                    if(id != -1) {
                        Toast.makeText(this, "Add Record With ID: " + id, Toast.LENGTH_LONG).show();
                        clearInputs();
                    } else {
                        Toast.makeText(this, "Fail To Add Record", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            }
        });

        btnView = findViewById(R.id.btn_view);
        btnView.setOnClickListener(v -> {
            String idStr = edID.getText().toString();
            if(!idStr.isEmpty()){
                try{
                    int id = Integer.parseInt(idStr);
                    viewSingleRecord(id);
                } catch (NumberFormatException e){
                    Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_LONG).show();
                }
            } else {
                viewAllRecords();
            }
        });

        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(v -> {
            updateRecord();
        });

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            deleteRecord();
        });

        resultView = findViewById(R.id.tv_result);

        // 添加退出登录按钮功能
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            finish(); // 或者跳转到登录页面
        });
    }

    private void viewSingleRecord(int id) {
        Cursor cursor = dpHelper.getRecordById(id);
        if (cursor.getCount() == 0) {
            resultView.setText("No record found with ID: " + id);
            cursor.close();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Records Details:\n\n");

        if (cursor.moveToFirst()) {
            int recordId = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String time =  cursor.getString(3);
            stringBuilder.append("ID: ").append(recordId)
                    .append("\nTitle: ").append(title)
                    .append("\nContent: ").append(content)
                    .append("\nTime:").append(time);
        }
        cursor.close();
        resultView.setText(stringBuilder.toString());
    }

    private void viewAllRecords() {
        Cursor cursor = dpHelper.getAllRecord();
        if (cursor.getCount() == 0) {
            resultView.setText("No records found");
            cursor.close();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Records List:\n\n");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String time =  cursor.getString(3);
            stringBuilder.append("ID: ").append(id)
                    .append("\nTitle: ").append(title)
                    .append("\nContent: ").append(content)
                    .append("\nTime: ").append(time)
                    .append("\n");
        }
        cursor.close();
        resultView.setText(stringBuilder.toString());
    }
    private void updateRecord() {
        String idStr = edID.getText().toString();
        String title = edTitle.getText().toString();
        String content = edContent.getText().toString();

        if (!idStr.isEmpty() && !title.isEmpty() && !content.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                int result = dpHelper.updateStudent(id, title, content);
                if (result > 0) {
                    Toast.makeText(this, "Record updated successfully", Toast.LENGTH_LONG).show();
                    clearInputs();
                    viewAllRecords(); // 刷新显示
                } else {
                    Toast.makeText(this, "Update failed - Record not found", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid content", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteRecord() {
        String idStr = edID.getText().toString();
        if (!idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                int result = dpHelper.deleteRecord(id);
                if (result > 0) {
                    Toast.makeText(this, "Record deleted successfully", Toast.LENGTH_LONG).show();
                    clearInputs();
                    viewAllRecords(); // 刷新显示
                } else {
                    Toast.makeText(this, "Delete failed - Record not found", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter Record ID", Toast.LENGTH_LONG).show();
        }
    }

    private void clearInputs() {
        edID.setText("");
        edTitle.setText("");
        edContent.setText("");
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_records);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                Intent intent = new Intent(RecordListActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            } else if (itemId == R.id.menu_records) {
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
