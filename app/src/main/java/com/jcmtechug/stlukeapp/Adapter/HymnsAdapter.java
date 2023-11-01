package com.jcmtechug.stlukeapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.jcmtechug.stlukeapp.R;

import java.util.ArrayList;
import java.util.List;

public class HymnsAdapter extends BaseAdapter implements Filterable {
    private String[] hymn;
    private String[] title;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<String> originalList;
    private List<String> filteredList;

    public HymnsAdapter(Context context, ArrayList<String> title, ArrayList<String> hymn){
        this.ctx = context;
        this.hymn = hymn.toArray(new String[0]);
        this.title = title.toArray(new String[0]);
        this.layoutInflater = LayoutInflater.from(ctx);
        this.originalList = new ArrayList<>(title);
        this.filteredList = new ArrayList<>(title);
    }

    @Override
    public int getCount() {
        return this.title.length;
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

//        TextView music = view.findViewById(R.id.music);
        TextView hymn_txt = view.findViewById(R.id.title);
//        music.setText(hymn[i]);
        hymn_txt.setText(title[i]);


        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<String> filteredTitles = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredTitles.addAll(originalList);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (String title : originalList) {
                        if (title.toLowerCase().contains(filterPattern)) {
                            filteredTitles.add(title);
                        }
                    }
                }

                Log.i("newlist", String.valueOf(filteredTitles));

                results.values = filteredTitles;
                results.count = filteredTitles.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList.clear();
                filteredList.addAll((List<String>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
