package com.jcmtechug.stlukeapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jcmtechug.stlukeapp.R;

public class TestimoniesAdapter extends RecyclerView.Adapter<TestimoniesAdapter.viewHolder> {
    private PosterList[] posterLists;

    public TestimoniesAdapter(PosterList[] posterLists){
        this.posterLists = posterLists;
    }
    @NonNull
    @Override
    public TestimoniesAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toptestimonies_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimoniesAdapter.viewHolder holder, int position) {
        holder.description.setText(posterLists[position].getPoster_description());
        holder.poster.setBackgroundResource(posterLists[position].getPoster_image());
    }

    @Override
    public int getItemCount() {
        return posterLists.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private LinearLayout poster;
        private TextView description;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster_image);
            description = itemView.findViewById(R.id.poster_description);
        }
    }
}
