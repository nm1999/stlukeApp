package com.chaplaincy.stlukeapp.DashboardActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.chaplaincy.stlukeapp.Adapter.HymnsAdapter;
import com.chaplaincy.stlukeapp.Apis.CheckConnectivity;
import com.chaplaincy.stlukeapp.Apis.Urls;
import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class Hymns extends AppCompatActivity {
    private ListView  list ;

    ArrayAdapter<String> adapter;
    private HymnsAdapter hymnsAdapter;
    private CheckConnectivity checkConnectivity;
    private ProgressDialog progressDialog;
    private DBhelper dBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymns);
        list = findViewById(R.id.list);

        dBhelper = new DBhelper(getApplicationContext());

        checkConnectivity = new CheckConnectivity();
        progressDialog = new ProgressDialog(this);

        SearchView search = findViewById(R.id.search);
        ImageView back = findViewById(R.id.back);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView sync_hymns = findViewById(R.id.syncing_hymn);

        loadHymns();

        back.setOnClickListener(view -> {
            Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(nxt);
        });

        sync_hymns.setOnClickListener(view ->{
            startSyncing();
        });




    }

    private void loadHymns() {
        // load hymns
        ArrayList<String> arraylist = new ArrayList<>();
        ArrayList<String> arraytitle = new ArrayList<>();

        Cursor cursor = dBhelper.fetchHymns();
        if (cursor.getCount() > 0){
            while (cursor.moveToFirst()){
                String song_no = cursor.getString(0);
                String title = cursor.getString(1);
                String song = cursor.getString(2);

                arraylist.add(song_no+". "+song);
//                arraytitle.add(title);


//                hymnsAdapter = new HymnsAdapter(getApplicationContext(),arraytitle,arraylist);
//                list.setTextFilterEnabled(true);
//                list.setAdapter(hymnsAdapter);
            }

            ArrayAdapter<String> adpt = new ArrayAdapter<>(Hymns.this, android.R.layout.simple_list_item_1,arraylist);
            list.setAdapter(adpt);

        }else{
            new SweetAlertDialog(Hymns.this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("No Hymns")
                    .setContentText("Do you want latest hymns ?")
                    .setConfirmButton("Yes", sweetAlertDialog -> {
                        startSyncing();
                        sweetAlertDialog.dismiss();
                    }).show();
        }




        //end loading hymns
    }

    private void startSyncing() {
        try {
            if (checkConnectivity.isInternetAvailable()){
                progressDialog.setTitle("Syncing...");
                progressDialog.setMessage("Please wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                // if there is no record in sqlite
                // fetch all available hymns.
                Cursor local_song_number = dBhelper.getLastHymnNumber();
                if (local_song_number.moveToFirst()){
                    String hymn_id = local_song_number.getString(0);
                    getLatestHymns(hymn_id);
                }else{
                    getLatestHymns("0");
                }


            }else{
                new SweetAlertDialog(Hymns.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Failure")
                        .setContentText("No internet Connection.")
                        .show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLatestHymns(String hymn_id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Urls.HYMN+hymn_id)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("errr",e.toString());
                Hymns.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SweetAlertDialog(Hymns.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Failure")
                                .setContentText("Something went wrong !")
                                .show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String json = responseBody.string();

                Log.i("ddata",json);
                Hymns.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            if (!jsonObject.getBoolean("error")){
                                JSONArray result_obj =  jsonObject.getJSONArray("results");

                                // check for length of the array
                                if(result_obj.length() > 0){
                                    for (int i =0; i< result_obj.length();i++){
                                        JSONObject obj = result_obj.getJSONObject(i);
                                        String hymn_no = obj.getString("id");
                                        String title = obj.getString("title");
                                        String song = obj.getString("song");

                                        Boolean result = dBhelper.saveHymnsLocally(hymn_no,title,song);
                                        if (result){
                                            Log.i("success","hymn stored locally");
                                        }else{
                                            Log.e("error","Failed saving hymns locally");
                                        }
                                    }
                                    loadHymns();
                                }else{
                                    new SweetAlertDialog(Hymns.this,SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Synced")
                                            .setContentText("No New Hymn found !")
                                            .show();
                                }

                                progressDialog.dismiss();
                            }else{
                                Log.i("report","An error occured when fetching json data");
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

    }

    private void sendSongs(ArrayList<String> arraylist) {
        OkHttpClient client = new OkHttpClient();

//        FormBody responseBody = new FormBody.Builder()
//                .add("songs", String.valueOf(arraylist))
//                .build();

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (String song : arraylist) {
            formBodyBuilder.addEncoded("songs[]", song);
        }
        FormBody formBody = formBodyBuilder.build();

        Request request = new Request.Builder()
                .post(formBody)
                .url(Urls.HYMN)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("error",e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody resp = response.body();
                String json = resp.string();

                Hymns.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("resp",json);
                    }
                });
            }
        });
    }


}