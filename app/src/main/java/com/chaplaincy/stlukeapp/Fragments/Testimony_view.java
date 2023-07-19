package com.chaplaincy.stlukeapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaplaincy.stlukeapp.Adapter.PosterList;
import com.chaplaincy.stlukeapp.Adapter.TestimoniesAdapter;
import com.chaplaincy.stlukeapp.R;

import java.util.ArrayList;


public class Testimony_view extends Fragment {
    private RecyclerView poster_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_testimony_view, container, false);

        // getting the posters cards content
        poster_view = view.findViewById(R.id.posters);
        getPosterContent(view);

        return view;
    }

    private void getPosterContent(View view) {
        int [] admin_pics = {R.drawable.banner,R.drawable.bible,R.drawable.churchlogo,R.drawable.eucharist,R.drawable.holycross};

        ArrayList<PosterList> post = new ArrayList<PosterList>();
        for (int i=0;i<admin_pics.length;i++){
            post.add(new PosterList(admin_pics[i],"God loves u."));
        }

        PosterList[] posterLists = post.toArray(new PosterList[0]);
        TestimoniesAdapter testimoniesAdapter = new TestimoniesAdapter(posterLists);

        poster_view.setHasFixedSize(true);
        poster_view.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        poster_view.setAdapter(testimoniesAdapter);
    }
}