package tk.growuphappily.work4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("历史记录");
    }

    private void refresh() {
        LinearLayout list = findViewById(R.id.list);
        list.removeAllViews();
        try (var db = new MyDbHelper(getApplicationContext())) {
            for (MyDbHelper.Value value : db.readAll()){
                var frag = RecordFragment.newInstance(value);
                var view = new FrameLayout(getApplicationContext());
                view.setId(View.generateViewId());
                var manager = getSupportFragmentManager();
                var transaction = manager.beginTransaction();
                transaction.add(view.getId(), frag);
                transaction.commit();
                list.addView(view);
            }
        } catch (Exception e) {
            Log.e("RecordActivity", e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}