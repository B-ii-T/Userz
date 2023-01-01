package com.bit.userz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatHolder> {
    private List<String> cats = new ArrayList<>();
    private onCatClickListener listener;
    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new CatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatHolder holder, int position) {
        String currentCat = cats.get(position);
        holder.catName.setText(currentCat);
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public void setCats(List<String> cats){
        this.cats = cats;
        notifyDataSetChanged();
    }

    public String getCatAtPosition(int position){
        return cats.get(position);
    }

    class CatHolder extends RecyclerView.ViewHolder{
        private TextView catName;
        public CatHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.cat_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.onCatClick(cats.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
    public interface onCatClickListener{
        void onCatClick(String cat);
    }
    public void setOnCategoryClickListener(onCatClickListener listener){
        this.listener = listener;
    }
}
