package tk.growuphappily.work1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {
    public static final int RESULT_SUCCEED = 102;
    public static final int REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.ret).setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.putExtra("Ret", "1234");
            setResult(RESULT_SUCCEED, intent);
            finish();
        });

        findViewById(R.id.cancel).setOnClickListener((view) -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}