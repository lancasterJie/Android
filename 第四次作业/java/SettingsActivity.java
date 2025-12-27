package com.example.myproject4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    Button b1;

    EditText user_name;

    EditText passwd;

    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1=findViewById(R.id.login);
        user_name=findViewById(R.id.user_name);
        passwd=findViewById(R.id.passwd);
        check=findViewById(R.id.check);

        class MyListener1 implements View.OnClickListener {
            @Override
            public void onClick(View view)
            {
               if(view.getId()==R.id.login)
               {
                   Intent login=new Intent(SettingsActivity.this,MainActivity.class);
                   startActivity(login);
               }
            }
        }

        b1.setOnClickListener(new MyListener1());

        Setting_load();
    }

    public void save()
    {
        Boolean auto_save=check.isChecked();
        String name=user_name.getText().toString();
        String pass=passwd.getText().toString();

        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", auto_save);
        editor.putString("user_name", name);
        editor.putString("passwd", pass);
        editor.apply();
    }

    public void Setting_load()
    {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        Boolean checked=sp.getBoolean("auto_save",false);

        if(checked)
        {
            String name=sp.getString("user_name","");
            String pass=sp.getString("passwd","");
            user_name.setText(name);
            passwd.setText(pass);
        }

        check.setChecked(checked);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(check.isChecked())
        {
            save();
        } else if (!check.isChecked())
        {
            user_name.setText("");
            passwd.setText("");
        }
    }
}
