package com.bit.userz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AccountViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

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
            public void onChanged(Integer integer) {
                Toast.makeText(MainActivity.this, integer+" category(ies)", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getAllCat().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                for (String cat : categories) {
                    Toast.makeText(MainActivity.this, cat, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}