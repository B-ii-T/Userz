package com.bit.userz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ACCOUNT_REQUEST = 1;
    private AccountViewModel viewModel;
    private TextView catNumberText, accountsCount;
    private ImageView addBtn;
    private List<String> categoriesList = new ArrayList<>();
    private List<Account> accountsByCat = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catNumberText = findViewById(R.id.category_number_text);
        accountsCount = findViewById(R.id.accounts_count);
        addBtn = findViewById(R.id.add_btn);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AccountAdapter adapter = new AccountAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        viewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                //updating he recycler view
                adapter.setAccounts(accounts);
            }
        });
        viewModel.getCatNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer catNumber) {
                catNumberText.setText("("+catNumber+")");
            }
        });
        viewModel.getAllCat().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                // update categories list view
                categoriesList = categories;
            }
        });
        viewModel.getAccountsByCat("service").observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                accountsByCat = accounts;
            }
        });
        viewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                accountsCount.setText("("+count+")");
            }
        });

        findViewById(R.id.sort_category_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(String s : categoriesList){
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.sort_category_btn).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                adapter.setAccounts(accountsByCat);
                return false;
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddAccountActivity.class), ADD_ACCOUNT_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteAccount(adapter.getAccountAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
    public void nextSettings (View v){
    View view = findViewById(R.id.option_bar);
        view.setVisibility(View.GONE);
    view = findViewById(R.id.contentmain);
        view.setVisibility(View.GONE);
    view = findViewById(R.id.Frst);
        view.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_ACCOUNT_REQUEST && resultCode == RESULT_OK){
            String username = data.getStringExtra(AddAccountActivity.EXTRA_USERNAME);
            String email = data.getStringExtra(AddAccountActivity.EXTRA_EMAIL);
            String password = data.getStringExtra(AddAccountActivity.EXTRA_PASSWORD);
            String category = data.getStringExtra(AddAccountActivity.EXTRA_CATEGORY);
            String platform = data.getStringExtra(AddAccountActivity.EXTRA_PLATFORM);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Account account = new Account(username, email, password, category, platform, R.drawable.ic_launcher_foreground, sdf.format(new Date()));
            viewModel.insertAccount(account);
            Toast.makeText(this, "new account saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "new account not saved", Toast.LENGTH_SHORT).show();
        }
    }
}