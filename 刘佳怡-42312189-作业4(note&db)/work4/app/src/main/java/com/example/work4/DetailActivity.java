package com.example.work4;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Record record;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        record = Record.fromBundle(getIntent().getBundleExtra("record"));
        editText = findViewById(R.id.detailText);
        editText.setText(record.content);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(record.title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MyDbHelper dbHelper = new MyDbHelper(this);
        if (item.getItemId() == R.id.action_save) {
            String newContent = editText.getText().toString();
            dbHelper.update(record.id, newContent);
            Toast.makeText(this, "记录已更新", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            dbHelper.delete(record.id);
            Toast.makeText(this, "记录已删除", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}