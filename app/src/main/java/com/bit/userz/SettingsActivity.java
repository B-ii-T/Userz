package com.bit.userz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

public class SettingsActivity extends AppCompatActivity {
    private ImageView closeOptionsBtn;
    private Switch copySwitch, doubleTapSwitch, titleBarSwitch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settingsPreferences = getSharedPreferences("settingsPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = settingsPreferences.edit();


        copySwitch = findViewById(R.id.copy_switch);
        doubleTapSwitch = findViewById(R.id.double_back_switch);
        titleBarSwitch = findViewById(R.id.title_bar_switch);
        closeOptionsBtn = findViewById(R.id.close_options_btn);

        copySwitch.setChecked(settingsPreferences.getBoolean("copyOption", false));
        doubleTapSwitch.setChecked(settingsPreferences.getBoolean("doubleTapOption", false));
        titleBarSwitch.setChecked(settingsPreferences.getBoolean("titleBarOption", false));

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
        titleBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean titleBarActive) {
                editor.putBoolean("titleBarOption", titleBarActive);
                editor.commit();
            }
        });

        closeOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}
