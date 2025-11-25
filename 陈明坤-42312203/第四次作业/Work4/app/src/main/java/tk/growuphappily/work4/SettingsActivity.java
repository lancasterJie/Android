package tk.growuphappily.work4;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {
    private EditText nameEdit;
    private EditText password;
    private Switch autoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameEdit = findViewById(R.id.name);
        password = findViewById(R.id.passwd);
        autoSave = findViewById(R.id.autoSave);
        var sp = getSharedPreferences("settings", MODE_PRIVATE);
        nameEdit.setText(sp.getString("user_name", "Guest"));
        autoSave.setChecked(sp.getBoolean("auto_save", false));
        password.setText(sp.getString("password", ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        var sp = getSharedPreferences("settings", MODE_PRIVATE);
        var editor = sp.edit();
        editor.putBoolean("auto_save", autoSave.isChecked());
        editor.putString("user_name", nameEdit.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();
    }
}