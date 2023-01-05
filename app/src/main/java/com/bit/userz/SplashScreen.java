package com.bit.userz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sh;
    static BiometricManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent splashIntent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(splashIntent);
//                finish();
//            }
//        },1500);

        sh = getSharedPreferences("settingsPreferences", MODE_PRIVATE);

        manager = BiometricManager.from(SplashScreen.this);
        switch (manager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(SplashScreen.this, "Biometric authentication not supported", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(SplashScreen.this, "No registered fingerprints", Toast.LENGTH_SHORT).show();
                break;
        }
        BiometricPrompt prompt = new androidx.biometric.BiometricPrompt(SplashScreen.this, ContextCompat.getMainExecutor(SplashScreen.this), new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //nextActivity();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                nextActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        BiometricPrompt.PromptInfo info = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric authentication")
                .setNegativeButtonText("Cancel").build();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sh.getBoolean("fingerprintOption", false)){
                    prompt.authenticate(info);
                }else{
                    nextActivity();
                }
            }
        }, 5500);
        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sh.getBoolean("fingerprintOption", false)){
                    prompt.authenticate(info);
                }else{
                    nextActivity();
                }
            }
        });

    }
    public void nextActivity(){
        Intent splashIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(splashIntent);
        finish();
    }
}