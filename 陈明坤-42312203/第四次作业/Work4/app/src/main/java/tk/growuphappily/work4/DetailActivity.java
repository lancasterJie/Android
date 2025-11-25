package tk.growuphappily.work4;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    private MyDbHelper.Value value;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        value = MyDbHelper.Value.fromBundle(getIntent().getBundleExtra("Data"));
        content = findViewById(R.id.editTextDetail);
        content.setText(value.content);
        getSupportActionBar().setTitle("编辑" + value.title);

        findViewById(R.id.save_detail).setOnClickListener((view) -> {
            var db = new MyDbHelper(getApplicationContext());
            db.edit(value.id, content.getText().toString());
            db.close();
            finish();
        });
    }
}