package com.bit.userz;

import android.animation.LayoutTransition;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sh;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    public static final int ADD_ACCOUNT_REQUEST = 1;
    public static final int EDIT_ACCOUNT_REQUEST = 2;
    private AccountViewModel viewModel;
    private TextView catNumberText, accountsCount;
    private ImageView addBtn, optionsBtn;
    private List<String> categoriesList = new ArrayList<>();
    private List<Account> accountsByCat = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sh = getSharedPreferences("settingsPreferences", MODE_PRIVATE);
        checkActionBar(sh);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        LinearLayout layout =  findViewById(R.id.title_container);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vi = findViewById(R.id.search_field);
                int v = (vi.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
                TransitionManager.beginDelayedTransition(layout,new AutoTransition());
                vi.setVisibility(v);
                }
        });
        findViewById(R.id.sort_category_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vi = findViewById(R.id.categories_recycler);
                int v = (vi.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
                TransitionManager.beginDelayedTransition(layout,new AutoTransition());
                vi.setVisibility(v);
                for (String s : categoriesList) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });



        catNumberText = findViewById(R.id.category_number_text);
        accountsCount = findViewById(R.id.accounts_count);
        addBtn = findViewById(R.id.add_btn);
        optionsBtn = findViewById(R.id.options_btn);
        SharedPreferences sh = getSharedPreferences("settingsPreferences", MODE_PRIVATE);

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
                catNumberText.setText(" ∙ " + catNumber );
            }
        });
        viewModel.getAllCat().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                // update categories list view
                categoriesList = categories;
                AddAccountActivity.categorySuggestions = categories;
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
                accountsCount.setText(" « " + count );
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
                if (sh.getBoolean("editModeOption", false)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Do you want to delete this account ?");
                    builder.setTitle("Delete account");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        viewModel.deleteAccount(adapter.getAccountAtPosition(viewHolder.getAdapterPosition()));
                        Toast.makeText(MainActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "edit mode is disabled", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnAccountClickListener(new AccountAdapter.OnAccountClickListener() {
            @Override
            public void onAccountClick(Account account) {
                if (sh.getBoolean("copyOption", false)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData passwordClip = ClipData.newPlainText("password", account.getPassword());
                    ClipData emailClip = ClipData.newPlainText("email", account.getEmail());
                    clipboard.setPrimaryClip(passwordClip);
                    clipboard.setPrimaryClip(emailClip);
                    Toast.makeText(MainActivity.this, "credentials copied to clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
                    intent.putExtra(EditAccountActivity.EXTRA_ID, account.getId());
                    intent.putExtra(EditAccountActivity.EXTRA_USERNAME, account.getUsername());
                    intent.putExtra(EditAccountActivity.EXTRA_EMAIL, account.getEmail());
                    intent.putExtra(EditAccountActivity.EXTRA_PASSWORD, account.getPassword());
                    intent.putExtra(AddAccountActivity.EXTRA_CATEGORY, account.getCategory());
                    intent.putExtra(AddAccountActivity.EXTRA_PLATFORM, account.getPlatform());
                    intent.putExtra(EditAccountActivity.EXTRA_ICON, account.getIcon());
                    startActivityForResult(intent, EDIT_ACCOUNT_REQUEST);
                }
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                CustomIntent.customType(MainActivity.this, "left-to-right");
            }
        });
    }

    public void nextSettings(View v) {
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
        if (requestCode == ADD_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
            String username = data.getStringExtra(AddAccountActivity.EXTRA_USERNAME);
            String email = data.getStringExtra(AddAccountActivity.EXTRA_EMAIL);
            String password = data.getStringExtra(AddAccountActivity.EXTRA_PASSWORD);
            String category = data.getStringExtra(AddAccountActivity.EXTRA_CATEGORY);
            String platform = data.getStringExtra(AddAccountActivity.EXTRA_PLATFORM).substring(0, 1).toUpperCase() + data.getStringExtra(AddAccountActivity.EXTRA_PLATFORM).substring(1);

            Account account = new Account(username, email, password, category, platform, fillIcon(platform), sdf.format(new Date()));
            viewModel.insertAccount(account);
            Toast.makeText(this, "new account saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
            int accountId = data.getIntExtra(EditAccountActivity.EXTRA_ID, -1);
            String username = data.getStringExtra(EditAccountActivity.EXTRA_USERNAME);
            String email = data.getStringExtra(EditAccountActivity.EXTRA_EMAIL);
            String password = data.getStringExtra(EditAccountActivity.EXTRA_PASSWORD);
            String category = data.getStringExtra(AddAccountActivity.EXTRA_CATEGORY);
            String platform = data.getStringExtra(AddAccountActivity.EXTRA_PLATFORM);
            int icon = data.getIntExtra(EditAccountActivity.EXTRA_ICON, R.drawable.ic_launcher_foreground);
            if (accountId == -1) {
                Toast.makeText(this, "can't update account", Toast.LENGTH_SHORT).show();
                return;
            }
            Account account = new Account(username, email, password, category, platform, icon, sdf.format(new Date()));
            account.setId(accountId);
            viewModel.updateAccount(account);
            Toast.makeText(this, "account updated", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "new account not saved", Toast.LENGTH_SHORT).show();
        }
    }

    public int fillIcon(String platform) {
        int icon = R.drawable.user;
        for (PlatformSuggestion ps : AddAccountActivity.platformSuggestions) {
            if (platform.toLowerCase().trim().equals(ps.getPlatformName())) {
                icon = ps.getPlatformImg();
                return icon;
            }
        }
        return icon;
    }

    @Override
    public void onBackPressed() {
        if (sh.getBoolean("doubleTapOption", false)) {
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    public void checkActionBar(SharedPreferences sh) {
        if (sh.getBoolean("actionBarOption", false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkActionBar(sh);
        if (sh.getBoolean("editModeOption", false)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main_activity_layout), "Edit mode is On", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.secondary100));
            snackbar.setTextColor(getResources().getColor(R.color.primary));
            snackbar.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkActionBar(sh);
    }

}