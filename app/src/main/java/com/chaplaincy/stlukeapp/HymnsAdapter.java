package com.chaplaincy.stlukeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HymnsAdapter extends BaseAdapter implements Filterable {
    private String[] hymn;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<String> originalList;
    private List<String> filteredList;

    HymnsAdapter(Context context, ArrayList<String> hymn){
        this.ctx = context;
        this.hymn = hymn.toArray(new String[0]);
        this.layoutInflater = LayoutInflater.from(ctx);
        this.originalList = new ArrayList<>(hymn);
        this.filteredList = new ArrayList<>(hymn);
    }
    @Override
    public int getCount() {
        return this.hymn.length;
    }

    @Override
    public Object getItem(int i) {
        return originalList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.hymns_layout_format,null);

        TextView music = view.findViewById(R.id.music);
        music.setText(hymn[i]);

        return view;
    }

    @Override
    public Filter getFilter() {
        return new ItemFilter();
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence == null || charSequence.length() == 0) {
                results.count = originalList.size();
                results.values = new ArrayList<>(originalList);
            } else {
                String filterString = charSequence.toString().toLowerCase();

                final List<String> filteredData = new ArrayList<>();

                for (String item : originalList) {
                    if (item.toLowerCase().contains(filterString)) {
                        filteredData.add(item);
                    }
                }

                results.count = filteredData.size();
                results.values = filteredData;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredList = (List<String>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
