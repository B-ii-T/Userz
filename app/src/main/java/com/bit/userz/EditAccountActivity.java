package com.bit.userz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class EditAccountActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PASSWORD = "PASSWORD";
    public static final String EXTRA_ICON = "ICON";
    private EditText usernameEdit, emailEdit, passwordEdit, categoryEdit, platformEdit;
    private Button saveEditBtn;
    private Switch editModeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);


        usernameEdit = findViewById(R.id.username_edit_input);
        emailEdit = findViewById(R.id.email_edit_input);
        passwordEdit = findViewById(R.id.password_edit_input);
        categoryEdit = findViewById(R.id.category_edit_input);
        platformEdit = findViewById(R.id.platform_edit_input);
        saveEditBtn = findViewById(R.id.save_edit_btn);
        editModeSwitch = findViewById(R.id.edit_mode_switch);

        editModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    usernameEdit.setEnabled(true);
                    emailEdit.setEnabled(true);
                    passwordEdit.setEnabled(true);
                }else{
                    usernameEdit.setEnabled(false);
                    emailEdit.setEnabled(false);
                    passwordEdit.setEnabled(false);
                }
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            usernameEdit.setText(intent.getStringExtra(EXTRA_USERNAME));
            emailEdit.setText(intent.getStringExtra(EXTRA_EMAIL));
            passwordEdit.setText(intent.getStringExtra(EXTRA_PASSWORD));
            categoryEdit.setText(intent.getStringExtra(AddAccountActivity.EXTRA_CATEGORY));
            platformEdit.setText(intent.getStringExtra(AddAccountActivity.EXTRA_PLATFORM));
        }

        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEdits();
            }
        });
    }
    public void saveEdits(){
        String userName = usernameEdit.getText().toString().trim();
        String userEmail = emailEdit.getText().toString().trim();
        String userPassword = passwordEdit.getText().toString().trim();
        String accountCategory = getIntent().getStringExtra(AddAccountActivity.EXTRA_CATEGORY);
        String accountPlatform = getIntent().getStringExtra(AddAccountActivity.EXTRA_PLATFORM);
        int accountIcon = getIntent().getIntExtra(EXTRA_ICON, R.drawable.ic_launcher_foreground);
        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || accountCategory.isEmpty() || accountPlatform.isEmpty()){
            Toast.makeText(this, "all fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent editAccountData = new Intent();
        editAccountData.putExtra(EXTRA_USERNAME, userName);
        editAccountData.putExtra(EXTRA_EMAIL, userEmail);
        editAccountData.putExtra(EXTRA_PASSWORD, userPassword);
        editAccountData.putExtra(AddAccountActivity.EXTRA_CATEGORY, accountCategory);
        editAccountData.putExtra(AddAccountActivity.EXTRA_PLATFORM, accountPlatform);
        editAccountData.putExtra(EXTRA_ICON, accountIcon);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            editAccountData.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, editAccountData);
        finish();
    }
}