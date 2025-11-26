package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragments.AboutFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.ProfileFragment;
import com.example.myapplication.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAT = "phl";
    RadioGroup radioGroup;
    private static final String TAG_HOME = "home_fragment";
    private static final String TAG_PROFILE = "profile_fragment";
    private static final String TAG_SETTINGS = "settings_fragment";
    private static final String TAG_ABOUT = "about_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().setFragmentResultListener("profilekey",this,
                (profilekey,result)->{
                    String reply=result.getString("reply");
                    Toast.makeText(this,"来自ProfileFragment的消息"+reply,Toast.LENGTH_SHORT).show();
                });
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        radioGroup = findViewById(R.id.radioGroup);
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment(), TAG_HOME);
        }
        //监听切换事件
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment selected = null;
            String tag = null;
            if (checkedId == R.id.rbHome) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                if (selected == null) selected = new HomeFragment();
                tag= TAG_HOME;
            } else if (checkedId == R.id.rbProfile) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_PROFILE);
                if (selected == null) selected = new ProfileFragment();
                tag = TAG_PROFILE;
            } else if (checkedId == R.id.rbSettings) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS);
                if (selected == null) selected = new SettingsFragment();
                tag = TAG_SETTINGS;
            } else if (checkedId == R.id.rbAbout) {
                selected = getSupportFragmentManager().findFragmentByTag(TAG_ABOUT);
                if (selected == null) selected = new AboutFragment();
                tag= TAG_ABOUT;
            }
            if (selected != null) {
                replaceFragment(selected, tag);
            }
        });

    }
    class AirplaneModeReciever extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context,"飞行模式",Toast.LENGTH_LONG).show();
        }
    }
    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("key",editTexttext.getText().toString());
//        Log.d(TAT,"onStat");
//    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container,fragment);
//        ft.commit();
//    }


}