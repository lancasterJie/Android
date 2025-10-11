package tk.growuphappily.work2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LifeCycle", TAG + " - onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.second).setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), SecondActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.dialog).setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), DialogActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle", TAG + " - onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle", TAG + " - onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle", TAG + " - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", TAG + " - onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", TAG + " - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", TAG + " - onPause");
    }
}