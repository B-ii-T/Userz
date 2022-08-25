package com.bit.userz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

public class SettingsActivity extends AppCompatActivity {
    private ImageView closeOptionsBtn;
    private Switch copySwitch, doubleTapSwitch, actionBarSwitch;
    SharedPreferences settingsPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPreferences = getSharedPreferences("settingsPreferences", MODE_PRIVATE);
        editor = settingsPreferences.edit();
        checkActionBar(settingsPreferences);
        setContentView(R.layout.activity_settings);


        copySwitch = findViewById(R.id.copy_switch);
        doubleTapSwitch = findViewById(R.id.double_back_switch);
        actionBarSwitch = findViewById(R.id.action_bar_switch);
        closeOptionsBtn = findViewById(R.id.close_options_btn);

        copySwitch.setChecked(settingsPreferences.getBoolean("copyOption", false));
        doubleTapSwitch.setChecked(settingsPreferences.getBoolean("doubleTapOption", false));
        actionBarSwitch.setChecked(settingsPreferences.getBoolean("actionBarOption", false));

        copySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean copyActive) {
                editor.putBoolean("copyOption", copyActive);
                editor.commit();
            }
        });
        doubleTapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean doubleTapActive) {
                editor.putBoolean("doubleTapOption", doubleTapActive);
                editor.commit();
            }
        });
        actionBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean actionBarActive) {
                editor.putBoolean("actionBarOption", actionBarActive);
                editor.commit();
                checkActionBar(settingsPreferences);
            }
        });

        closeOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void checkActionBar(SharedPreferences sh){
        if(!sh.getBoolean("actionBarOption", false)){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkActionBar(settingsPreferences);
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkActionBar(settingsPreferences);
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}
