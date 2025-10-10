package tk.growuphappily.work1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
        // 显式调用
        findViewById(R.id.explicit).setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), SecondActivity.class);
            startActivity(intent);
        });
        // 带返回值
        findViewById(R.id.forResult).setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), ThirdActivity.class);
            startActivityForResult(intent, ThirdActivity.REQ_CODE);
        });
        // 长按监听器
        findViewById(R.id.forResult).setOnLongClickListener((view) -> {
            Toast.makeText(getApplicationContext(), "长按了带返回结果的跳转", Toast.LENGTH_LONG).show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ThirdActivity.REQ_CODE)
        {
            if(resultCode == ThirdActivity.RESULT_SUCCEED)
            {
                TextView textView = findViewById(R.id.text);
                textView.setText(data.getStringExtra("Ret"));
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "用户取消操作", Toast.LENGTH_LONG).show();
            }
        }
    }
}