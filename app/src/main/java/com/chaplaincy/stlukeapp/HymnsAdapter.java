package com.chaplaincy.stlukeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HymnsAdapter extends BaseAdapter {
    private String[] hymn;
    private Context ctx;
    private LayoutInflater layoutInflater;

    HymnsAdapter(Context context, ArrayList<String> hymn){
        this.ctx = context;
        this.hymn = hymn.toArray(new String[0]);
        this.layoutInflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return this.hymn.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.hymns_layout_format,null);

        TextView music = view.findViewById(R.id.music);
        music.setText(hymn[i]);

        return view;
    }
}
