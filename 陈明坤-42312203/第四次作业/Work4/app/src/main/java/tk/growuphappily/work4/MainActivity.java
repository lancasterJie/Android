package tk.growuphappily.work4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);

        findViewById(R.id.save).setOnClickListener((view) -> save());

        findViewById(R.id.load).setOnClickListener((view) -> load());

        load();

        findViewById(R.id.floatingActionButton).setOnClickListener((view) -> {
            var intent = new Intent(view.getContext(), SettingsActivity.class);
            startActivity(intent);
        });
        var sp = getSharedPreferences("settings", MODE_PRIVATE);

        getSupportActionBar().setTitle(sp.getString("user_name", "Guest") + "的记事本");
    }

    private void load() {
        try (var file = openFileInput("note.txt")) {
            var txt = new String(file.readAllBytes(), Charset.defaultCharset());
            editText.setText(txt);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "记事本文件不存在", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("MainActivity", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void save() {
        try (var file = openFileOutput("note.txt", MODE_PRIVATE)) {
            var writer = new OutputStreamWriter(file);
            var txt = editText.getText().toString();
            writer.write(txt);
            writer.close();
        } catch (IOException e) {
            Log.e("MainActivity", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.record) {
            var intent = new Intent(getApplicationContext(), RecordActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.save) {
            var txt = editText.getText().toString();
            var db = new MyDbHelper(getApplicationContext());
            db.insert(new MyDbHelper.Value(txt));
            db.close();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        var sp = getSharedPreferences("settings", MODE_PRIVATE);
        if(sp.getBoolean("auto_save", false)) {
            save();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        var sp = getSharedPreferences("settings", MODE_PRIVATE);
        if(sp.getBoolean("auto_save", false)) {
            save();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}