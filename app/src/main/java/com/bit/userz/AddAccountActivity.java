package com.bit.userz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAccountActivity extends AppCompatActivity {
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PASSWORD = "PASSWORD";
    public static final String EXTRA_CATEGORY = "CATEGORY";
    public static final String EXTRA_PLATFORM = "PLATFORM";
    private EditText username, email, password, category, platform;
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        category = findViewById(R.id.category_input);
        platform = findViewById(R.id.platform_input);
        saveBtn = findViewById(R.id.save_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAccount();
            }
        });
    }
    public void saveAccount(){
        String userName = username.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String accountCategory = category.getText().toString().trim();
        String accountPlatform = platform.getText().toString().trim();
        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || accountCategory.isEmpty() || accountPlatform.isEmpty()){
            Toast.makeText(this, "all fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent newAccountData = new Intent();
        newAccountData.putExtra(EXTRA_USERNAME, userName);
        newAccountData.putExtra(EXTRA_EMAIL, userEmail);
        newAccountData.putExtra(EXTRA_PASSWORD, userPassword);
        newAccountData.putExtra(EXTRA_CATEGORY, accountCategory);
        newAccountData.putExtra(EXTRA_PLATFORM, accountPlatform);

        setResult(RESULT_OK, newAccountData);
        finish();
    }
}