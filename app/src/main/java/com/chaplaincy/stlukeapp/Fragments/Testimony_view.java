package com.chaplaincy.stlukeapp.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.chaplaincy.stlukeapp.Adapter.PosterList;
import com.chaplaincy.stlukeapp.Adapter.TestimoniesAdapter;
import com.chaplaincy.stlukeapp.Adapter.TestimonyList;
import com.chaplaincy.stlukeapp.Adapter.TestimonyStoriesAdapter;
import com.chaplaincy.stlukeapp.Apis.Urls;
import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;
import com.chaplaincy.stlukeapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class Testimony_view extends Fragment {
    private RecyclerView poster_view, all_testimonies;
    private MaterialButton add_story,submit_testimony;
    private AlertDialog alertdialog;
    private EditText title, description;

    @SuppressLint("MissingInflatedId")
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
        add_story = view.findViewById(R.id.add_story);


        View dialog = getLayoutInflater().inflate(R.layout.add_story,null);
        submit_testimony = dialog.findViewById(R.id.publish);

        title = dialog.findViewById(R.id.title);
        description = dialog.findViewById(R.id.description);




        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(dialog);
        alertdialog = alertDialog.create();

        submit_testimony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_str = title.getText().toString();
                String desc_str = description.getText().toString();

                if (TextUtils.isEmpty(title_str)){
                    title.requestFocus();
                    title.setError("Title is required");
                    return;
                }
                if (TextUtils.isEmpty(desc_str)){
                    description.requestFocus();
                    description.setError("Description is required");
                    return;
                }

                sendTestimony(title_str,desc_str);
            }
        });

        add_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStoryDialog(v);
            }
        });

        getAllTestimonies(view);

        return view;
    }

    private void sendTestimony(String title_str, String desc_str) {
        OkHttpClient client = new OkHttpClient();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id",0);
        
        RequestBody requestBody = new FormBody.Builder()
                .add("title",title_str)
                .add("description",desc_str)
                .add("user_id", String.valueOf(user_id))
                .build();

        Request request = new Request.Builder()
                .url(Urls.TESTIMONIES)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("err",e.toString());
                alertdialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE)
                                .setContentText("Failure")
                                .setContentText("No Internet Connection")
                                .setConfirmButton("OKay",sweetAlertDialog -> {
                                    startActivity(new Intent(getActivity(), HomeActivity.class));
                                    getActivity().finish();
                                })
                                .show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String resp = responseBody.string();
                Log.i("info",resp);
                getAllTestimonies(getView());
                alertdialog.dismiss();
            }
        });
    }

    private void addStoryDialog(View v) {
        alertdialog.show();
    }

    private void getAllTestimonies(View view) {

        ArrayList<TestimonyList> arrayList = new ArrayList<>();
        int [] admin_pics = {R.drawable.banner,R.drawable.bible,R.drawable.churchlogo,R.drawable.eucharist,R.drawable.holycross};

        // making api call for the testimonies
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Urls.ALL_STORIES)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("err",e.toString());
                if (!e.toString().isEmpty()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE)
                                    .setContentText("Failure")
                                    .setContentText("No Internet Connection")
                                    .setConfirmButton("OKay",sweetAlertDialog -> {
                                        startActivity(new Intent(getActivity(), HomeActivity.class));
                                        getActivity().finish();
                                    })
                                    .show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String results = responseBody.string();
                Log.i("datas",results);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(results);

                            if (!jsonObject.getBoolean("error")){

                                JSONArray obj = jsonObject.getJSONArray("results");


                                for (int i=0;i<obj.length();i++){
                                    JSONObject row_obj = obj.getJSONObject(i);

                                    arrayList.add(
                                            new TestimonyList(row_obj.getString("title"),row_obj.getString("username"),row_obj.getString("created_at"),row_obj.getString("description"))
                                    );
                                }

                                TestimonyList[] testimonyList = arrayList.toArray(new TestimonyList[0]);
                                TestimonyStoriesAdapter testimonyStoriesAdapter = new TestimonyStoriesAdapter(testimonyList);
                                all_testimonies.setHasFixedSize(true);
                                all_testimonies.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                                all_testimonies.setAdapter(testimonyStoriesAdapter);
                            }else{
                                new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE)
                                        .setContentText(jsonObject.getString("results"))
                                        .setConfirmButton("Okay", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                            }
                                        })
                                        .show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
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