package com.bit.userz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

public class SettingsActivity extends AppCompatActivity {
    private ImageView closeOptionsBtn;
    private Switch copySwitch, doubleTapSwitch, actionBarSwitch, editModeSwitch;
    SharedPreferences settingsPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPreferences = getSharedPreferences("settingsPreferences", MODE_PRIVATE);
        editor = settingsPreferences.edit();
        checkActionBar(settingsPreferences);
        setContentView(R.layout.activity_settings);


        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.black));


        copySwitch = findViewById(R.id.copy_switch);
        doubleTapSwitch = findViewById(R.id.double_back_switch);
        actionBarSwitch = findViewById(R.id.action_bar_switch);
        closeOptionsBtn = findViewById(R.id.close_options_btn);
        editModeSwitch = findViewById(R.id.edit_mode_switch_option);

        copySwitch.setChecked(settingsPreferences.getBoolean("copyOption", false));
        doubleTapSwitch.setChecked(settingsPreferences.getBoolean("doubleTapOption", false));
        actionBarSwitch.setChecked(settingsPreferences.getBoolean("actionBarOption", false));
        editModeSwitch.setChecked(settingsPreferences.getBoolean("editModeOption", false));

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
        editModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean editModeActive) {
                editor.putBoolean("editModeOption", editModeActive);
                editor.commit();
            }
        });

        closeOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.visit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnBit();
            }
        });
        findViewById(R.id.share_userz_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnUserz();
            }
        });
        findViewById(R.id.rate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Userz not deployed yet.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkActionBar(SharedPreferences sh){
        if(sh.getBoolean("actionBarOption", false)){
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

    public void launchURL(String url){
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(url));
                startActivity(viewIntent);
    }
    public void clickOnBit(){
        launchURL("https://bit-official.carrd.co");
    }public void clickOnUserz(){
        launchURL("https://userz-app.carrd.co");
    }
}

