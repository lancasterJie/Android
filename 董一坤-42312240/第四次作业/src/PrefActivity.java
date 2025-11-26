package com.dyk.homework;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PrefActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private EditText et_account;
    private EditText et_password;
    private CheckBox ck_remember;
    private static final String pref_name = "settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pref);

        btn_login = findViewById(R.id.btn_login);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        ck_remember = findViewById(R.id.ck_remember);

        btn_login.setOnClickListener(this);

        loadPref();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPref();
    }

    private void loadPref() {
        SharedPreferences settings = getSharedPreferences(pref_name,MODE_PRIVATE);
        String account = settings.getString("account","");
        String password = settings.getString("password","");
        boolean remember = settings.getBoolean("remember",false);

        if(remember){
            et_account.setText(account);
            et_password.setText(password);
            ck_remember.setChecked(remember);
        }
    }

    @Override
    public void onClick(View v) {
        int v_id = v.getId();
        if(v_id == R.id.btn_login){
            savePref();

            et_account.setText("");
            et_password.setText("");
            ck_remember.setChecked(false);

            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void savePref() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        boolean remember = ck_remember.isChecked();

        SharedPreferences settings = getSharedPreferences(pref_name,MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("account",account);
        edit.putString("password",password);
        edit.putBoolean("remember",remember);
        edit.apply();
    }
}