package com.bit.userz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddAccountActivity extends AppCompatActivity {
    SharedPreferences sh;
    public static List<PlatformSuggestion> platformSuggestions;
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PASSWORD = "PASSWORD";
    public static final String EXTRA_CATEGORY = "CATEGORY";
    public static final String EXTRA_PLATFORM = "PLATFORM";
    private EditText username, email, password, category;
    private AutoCompleteTextView platform;
    private ImageView platformImage;
    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sh = getSharedPreferences("settingsPreferences", MODE_PRIVATE);
        checkActionBar(sh);
        setContentView(R.layout.activity_add_account);
        fillPlatformList();
        platform = findViewById(R.id.platform_input);
        platform.setThreshold(0);
        PlatformAdapter platformAdapter = new PlatformAdapter(this, platformSuggestions);
        platform.setAdapter(platformAdapter);

        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        category = findViewById(R.id.category_input);
        platformImage = findViewById(R.id.platform_img);
        saveBtn = findViewById(R.id.save_btn);

        platform.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlatformSuggestion platformSuggestion = platformAdapter.getItem(i);
                platformImage.setImageResource(platformSuggestion.getPlatformImg());
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                for(PlatformSuggestion ps : platformSuggestions){
                    if(editable.toString().toLowerCase().trim().equals(ps.getPlatformName())){
                        platformImage.setImageResource(ps.getPlatformImg());
                    }else{
                        platformImage.setImageResource(R.drawable.user);
                    }
                }
            }
        };
        platform.addTextChangedListener(textWatcher);

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

    private void fillPlatformList(){
        platformSuggestions = new ArrayList<>();
        platformSuggestions.add(new PlatformSuggestion("messenger", R.drawable.messenger));
        platformSuggestions.add(new PlatformSuggestion("facebook", R.drawable.facebook));
        platformSuggestions.add(new PlatformSuggestion("twitter", R.drawable.twitter));
        platformSuggestions.add(new PlatformSuggestion("instagram", R.drawable.instagram));
        platformSuggestions.add(new PlatformSuggestion("tiktok", R.drawable.tiktok));
        platformSuggestions.add(new PlatformSuggestion("microsoft", R.drawable.microsoft));
        platformSuggestions.add(new PlatformSuggestion("google", R.drawable.google));
        platformSuggestions.add(new PlatformSuggestion("viber", R.drawable.viber));
        platformSuggestions.add(new PlatformSuggestion("telegram", R.drawable.telegram));
        platformSuggestions.add(new PlatformSuggestion("reddit", R.drawable.reddit));
        platformSuggestions.add(new PlatformSuggestion("snapchat", R.drawable.snapchat));
        platformSuggestions.add(new PlatformSuggestion("pinterest", R.drawable.pinterest));
        platformSuggestions.add(new PlatformSuggestion("linkedin", R.drawable.linkedin));
    }
    public void checkActionBar(SharedPreferences sh){
        if(sh.getBoolean("actionBarOption", false)){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}