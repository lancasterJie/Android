package tk.growuphappily.work3;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Fragment[] cache = new Fragment[4];
    private int lastFragmentId = -1;
    private ArrayList<Class<? extends Fragment>> fragments = new ArrayList<>();

    private EditText editText;

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
        editText = findViewById(R.id.edit);

        fragments.add(HomeFragment.class);
        fragments.add(AppFragment.class);
        fragments.add(WorkFragment.class);
        fragments.add(MyFragment.class);

        RadioGroup group = findViewById(R.id.group);
        group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton button = (RadioButton) radioGroup.getChildAt(i);
                if(button.isChecked()) {
                    setFragment(i);
                }
            }
        });

        setFragment(0);

        if(savedInstanceState != null) {
            editText.setText(savedInstanceState.getString("editText"));
        }

        getSupportFragmentManager().setFragmentResultListener("REQ_KEY", this, (reqkey, bundle) -> {
            Toast.makeText(getApplicationContext(), "Fragment Result: " + bundle.getString("result"), Toast.LENGTH_LONG).show();
        });
    }

    private void setFragment(int index)
    {
        if(index == lastFragmentId)
            return;
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if(cache[index] == null)
            {
                if(index == 0) {
                    cache[index] = HomeFragment.newInstance("123", "456");
                    transaction.add(R.id.frame, cache[index]);
                } else {
                    cache[index] = fragments.get(index).newInstance();
                    transaction.add(R.id.frame, cache[index]);
                }
            }
            if(lastFragmentId != -1)
                transaction.hide(cache[lastFragmentId]);
            transaction.show(cache[index]);
            transaction.commit();
        } catch (Exception ex) {
            Log.e("MainActivity", ex.getLocalizedMessage());
        }
        lastFragmentId = index;
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editText", editText.getText().toString());
    }


}