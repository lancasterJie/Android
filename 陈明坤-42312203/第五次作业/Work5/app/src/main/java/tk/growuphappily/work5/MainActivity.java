package tk.growuphappily.work5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<TaskItem> items = new ArrayList<>();
    public TaskItemArrayAdapter adapter;

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

        adapter = new TaskItemArrayAdapter(this, items);
        ListView list = findViewById(R.id.list);
        items.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        items.add(new TaskItem("Buy groceries", "2024-05-01"));
        items.add(new TaskItem("Walk the dog", "2024-04-20"));
        items.add(new TaskItem("Call John", "2024-04-23"));
        list.setAdapter(adapter);

        var button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener((v) -> {
            var model = new ModalBottomSheet();
            model.show(getSupportFragmentManager(), "ModelBottomSheet");
        });
    }
}