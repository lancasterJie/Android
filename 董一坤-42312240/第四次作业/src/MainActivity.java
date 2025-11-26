package com.dyk.homework;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_input;
    private Button btn_save;
    private Button btn_load;
    private TextView tv_show;
    private static final String file_name = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        et_input = findViewById(R.id.et_input);
        btn_save = findViewById(R.id.btn_save_to_file);
        btn_load = findViewById(R.id.btn_load_from_file);
        tv_show = findViewById(R.id.tv_show);

        btn_load.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int v_id = v.getId();
        if(v_id == R.id.btn_save_to_file){
            String input = et_input.getText().toString();

            if(input.isEmpty()){
                Toast.makeText(this, "保存的内容不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }

            FileOutputStream fos = null;
            try{
                fos = openFileOutput(file_name, Context.MODE_APPEND);
                fos.write(input.getBytes());

                Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();
                et_input.setText("");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else if(v_id == R.id.btn_load_from_file){
            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try{
                fis = openFileInput(file_name);
                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);

                String line = "";
                StringBuilder content = new StringBuilder();
                while((line = br.readLine())!=null){
                    content.append(line).append("\n");
                }

                Toast.makeText(this, "数据加载成功", Toast.LENGTH_SHORT).show();
                tv_show.setText(content.toString());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}