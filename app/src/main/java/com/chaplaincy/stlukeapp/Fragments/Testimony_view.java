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
import com.chaplaincy.stlukeapp.Adapter.TestimonyList;
import com.chaplaincy.stlukeapp.Adapter.TestimonyStoriesAdapter;
import com.chaplaincy.stlukeapp.R;

import java.util.ArrayList;


public class Testimony_view extends Fragment {
    private RecyclerView poster_view, all_testimonies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_testimony_view, container, false);

        // getting the posters cards content
        poster_view = view.findViewById(R.id.posters);
        getPosterContent(view);

        //getting the testimonies
        all_testimonies = view.findViewById(R.id.all_testimonies);
        getAllTestimonies(view);

        return view;
    }

    private void getAllTestimonies(View view) {
        ArrayList<TestimonyList> arrayList = new ArrayList<>();
        int [] admin_pics = {R.drawable.banner,R.drawable.bible,R.drawable.churchlogo,R.drawable.eucharist,R.drawable.holycross};

        for (int i=0;i<admin_pics.length;i++){
            arrayList.add(new TestimonyList("Matia Mathias","12/12/2023","God is so good to me God is so good to meGod is so good to meGod is so good to meGod is so good to me",admin_pics[i],"122k","34k"));
        }

        TestimonyList[] testimonyList = arrayList.toArray(new TestimonyList[0]);
        TestimonyStoriesAdapter testimonyStoriesAdapter = new TestimonyStoriesAdapter(testimonyList);
        all_testimonies.setHasFixedSize(true);
        all_testimonies.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        all_testimonies.setAdapter(testimonyStoriesAdapter);
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