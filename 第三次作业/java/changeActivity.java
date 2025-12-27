package com.example.myproject3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class changeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        showFragment();
    }

    private void showFragment()//初始化
    {
        FirstFragment firstFragment=new FirstFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.change_fragment,firstFragment)
                .commit();
    }

    public void ToSecondFragment(String data)
    {
        Bundle bundle=new Bundle();
        bundle.putString("first",data);

        SecondFragment secondFragment=new SecondFragment();
        secondFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.change_fragment,secondFragment)
                .commit();
    }

    public void ToFirstFragment(String data)
    {
        Bundle bundle=new Bundle();
        bundle.putString("second",data);

        FirstFragment firstFragment=new FirstFragment();
        firstFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.change_fragment,firstFragment)
                .commit();
    }
}
