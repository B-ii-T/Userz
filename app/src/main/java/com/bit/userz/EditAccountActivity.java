package com.bit.userz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class EditAccountActivity extends AppCompatActivity {
    private boolean enabled;
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PASSWORD = "PASSWORD";
    public static final String EXTRA_ICON = "ICON";
    private EditText usernameEdit, emailEdit, passwordEdit, categoryEdit, platformEdit;
    private Button saveEditBtn, copyBtn;
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
        copyBtn = findViewById(R.id.copy_btn);
        editModeSwitch = findViewById(R.id.edit_mode_switch);

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!usernameEdit.getText().toString().trim().equals("") &&
                !emailEdit.getText().toString().trim().equals("") &&
                !passwordEdit.getText().toString().trim().equals("")){
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData passwordClip = ClipData.newPlainText("password", passwordEdit.getText().toString().trim());
                    ClipData emailClip = ClipData.newPlainText("email", emailEdit.getText().toString().trim());
                    ClipData usernameClip = ClipData.newPlainText("username", usernameEdit.getText().toString().trim());
                    clipboard.setPrimaryClip(passwordClip);
                    clipboard.setPrimaryClip(emailClip);
                    clipboard.setPrimaryClip(usernameClip);
                    Toast.makeText(EditAccountActivity.this, "credentials copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    usernameEdit.setEnabled(true);
                    emailEdit.setEnabled(true);
                    passwordEdit.setEnabled(true);
                    enabled = true;
                }else{
                    usernameEdit.setEnabled(false);
                    emailEdit.setEnabled(false);
                    passwordEdit.setEnabled(false);
                    enabled = false;
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

        usernameEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(usernameEdit.isEnabled() && !usernameEdit.getText().toString().trim().equals("")){
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData usernameClip = ClipData.newPlainText("username", usernameEdit.getText().toString().trim());
                    clipboard.setPrimaryClip(usernameClip);
                    Toast.makeText(EditAccountActivity.this, "username copied", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        emailEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(emailEdit.isEnabled() && !emailEdit.getText().toString().trim().equals("")){
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData usernameClip = ClipData.newPlainText("email", emailEdit.getText().toString().trim());
                    clipboard.setPrimaryClip(usernameClip);
                    Toast.makeText(EditAccountActivity.this, "email copied", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        passwordEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(passwordEdit.isEnabled() && !passwordEdit.getText().toString().trim().equals("")){
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData usernameClip = ClipData.newPlainText("password", passwordEdit.getText().toString().trim());
                    clipboard.setPrimaryClip(usernameClip);
                    Toast.makeText(EditAccountActivity.this, "password copied", Toast.LENGTH_SHORT).show();
                }
                return true;
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