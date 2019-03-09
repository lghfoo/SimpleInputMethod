package com.example.lgh.simpleinputmethod.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.lgh.simpleinputmethod.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SimpleInputMethod";

    EditText dbgView;
    Button toggleBtn, selectBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbgView = findViewById(R.id.dbgView);
        toggleBtn = findViewById(R.id.toggleBtn);
        selectBtn = findViewById(R.id.selectBtn);

        selectBtn.setOnClickListener(e -> {
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker();
        });

        toggleBtn.setOnClickListener(e -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(intent);
        });

    }


}
