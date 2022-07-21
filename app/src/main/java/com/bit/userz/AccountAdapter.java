package com.bit.userz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {
    private List<Account> accounts = new ArrayList<>();
    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_sample, parent, false);
        return new AccountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account currentAccount = accounts.get(position);
        holder.username.setText(currentAccount.getUsername());
        holder.platform.setText(currentAccount.getPlatform());
        holder.creationDate.setText("creation date");
        holder.appIcon.setImageResource(currentAccount.getIcon());
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void setAccounts(List<Account> accounts){
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    class AccountHolder extends RecyclerView.ViewHolder{
        private TextView username, platform, creationDate;
        private ImageView appIcon;
        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            platform = itemView.findViewById(R.id.platform);
            creationDate = itemView.findViewById(R.id.creation_date);
            appIcon = itemView.findViewById(R.id.app_icon);
        }
    }
}
