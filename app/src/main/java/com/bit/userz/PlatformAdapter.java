package com.bit.userz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlatformAdapter extends ArrayAdapter<PlatformSuggestion> {
    private List<PlatformSuggestion> fullPlatformList;
    public PlatformAdapter(@NonNull Context context, @NonNull List<PlatformSuggestion> platformList) {
        super(context, 0, platformList);
        fullPlatformList = new ArrayList<>(platformList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return platformFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.platform_autocomplete_suggestion_layout, parent, false);
        }
        TextView platformViewName = convertView.findViewById(R.id.suggested_platform_name);
        ImageView platformViewImg = convertView.findViewById(R.id.suggested_platform_img);
        PlatformSuggestion platformSuggestion = getItem(position);
        if(platformSuggestion != null){
            platformViewName.setText(platformSuggestion.getPlatformName());
            platformViewImg.setImageResource(platformSuggestion.getPlatformImg());
        }
        return convertView;
    }

    private Filter platformFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            List<PlatformSuggestion> suggestions = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                suggestions.addAll(fullPlatformList);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(PlatformSuggestion ps : fullPlatformList){
                    if(ps.getPlatformName().toLowerCase().contains(filterPattern)){
                        suggestions.add(ps);
                    }
                }
            }
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((PlatformSuggestion) resultValue).getPlatformName();
        }
    };
}
