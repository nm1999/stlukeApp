package com.chaplaincy.stlukeapp.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaplaincy.stlukeapp.R;

public class TestimonyStoriesAdapter extends RecyclerView.Adapter<TestimonyStoriesAdapter.ViewHolder> {
    private TestimonyList [] testimonyLists;

    public TestimonyStoriesAdapter(TestimonyList [] testimonyLists) {
        this.testimonyLists = testimonyLists;
    }

    @NonNull
    @Override
    public TestimonyStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testimony_views,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimonyStoriesAdapter.ViewHolder holder, int position) {
        holder.username.setText(testimonyLists[position].getUsername());
        holder.created_at.setText(testimonyLists[position].getCreated_at());
        holder.story.setText(testimonyLists[position].getDescription());
        holder.title.setText(testimonyLists[position].getTitle());

    }

    @Override
    public int getItemCount() {
        return testimonyLists.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username,created_at,story,title;
        private ImageView dp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            username = itemView.findViewById(R.id.username);
            created_at = itemView.findViewById(R.id.created_at);
            story = itemView.findViewById(R.id.story);
        }
    }
}
