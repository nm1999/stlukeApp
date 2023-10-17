package com.jcmtechug.stlukeapp.DashboardActivities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jcmtechug.stlukeapp.Adapter.HymnsAdapter;
import com.jcmtechug.stlukeapp.Apis.CheckConnectivity;
import com.jcmtechug.stlukeapp.Apis.Urls;
import com.jcmtechug.stlukeapp.DBHelper.DBhelper;
import com.jcmtechug.stlukeapp.R;

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
    private ArrayAdapter<String> adpt;
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

//        uncomment when the data has successfully loaded in sqlite
        loadHymns();
//        getLatestHymns("0");

        back.setOnClickListener(view -> {
            Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(nxt);
        });

        sync_hymns.setOnClickListener(view ->{
            startSyncing();
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hymnsAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // incase a hymn is selected



    }

    private void loadHymns() {
        // load hymns
        ArrayList<String> arraylist = new ArrayList<>();
        ArrayList<String> arraySongs = new ArrayList<>();

        String str_array = null;

        Cursor cursor = dBhelper.fetchHymns();
        Log.e("ddata", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.i("son", cursor.getString(3));
                str_array = cursor.getString(3);
                break;
            }

            try {
                JSONObject jsonObject = new JSONObject(str_array);
                JSONArray result_obj = jsonObject.getJSONArray("results");
                Log.e("length", String.valueOf(result_obj.length()));
                if (result_obj.length() > 0) {
                    for (int i = 0; i < result_obj.length(); i++) {
                        JSONObject obj = result_obj.getJSONObject(i);
                        String hymn_no = obj.getString("id");
                        String title = obj.getString("title");
                        String song = obj.getString("song");

                        arraylist.add(hymn_no+". "+title);
                        arraySongs.add(song);

                        adpt = new ArrayAdapter<>(Hymns.this, R.layout.song_layout, arraylist);

                        hymnsAdapter = new HymnsAdapter(getApplicationContext(),arraylist,arraySongs);
                        list.setAdapter(hymnsAdapter);

//                        list.setOnItemClickListener((parent, view, position, id) -> {
//                            Intent intent = new Intent(Hymns.this, SelectedHymn.class);
////                            intent.putExtra("hymn_no",hymnNo.get((int) id));
////                            intent.putExtra("song",hymn.get((int) id));
////                            intent.putExtra("title",_title.get((int) id));
//                            startActivity(intent);
//                        });
                    }
                }
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Hymns.this, SelectedHymn.class);
                        intent.putExtra("hymn_no","");
                        intent.putExtra("song",arraySongs.get(position));
                        intent.putExtra("title",arraylist.get(position));
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
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
                    getLatestHymns("0");
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
                                .setConfirmButton("Okay", sweetAlertDialog -> {
                                    startActivity(new Intent(Hymns.this,HomeActivity.class));
                                    finish();
                                })
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
                            ArrayList<String> arraylist = new ArrayList<>();
                            ArrayList<String> hymn = new ArrayList<>();
                            ArrayList<String> _title = new ArrayList<>();
                            ArrayList<String> hymnNo = new ArrayList<>();

                            JSONObject jsonObject = new JSONObject(json);
                            if (!jsonObject.getBoolean("error")){
                                JSONArray result_obj =  jsonObject.getJSONArray("results");

                                // store an full string of an array in one cell
                                Boolean result = dBhelper.saveHymnsLocally("hdh d dhdhd d","tid dhdhdh title",json);
                                if (result){
                                    Log.i("success","hymn stored locally");
                                }else{
                                    Log.e("error","Failed saving hymns locally");
                                }

                                // end of new method


                                // check for length of the array
//                                if(result_obj.length() > 0){
//                                    for (int i =0; i< result_obj.length();i++){
//                                        JSONObject obj = result_obj.getJSONObject(i);
//                                        String hymn_no = obj.getString("id");
//                                        String title = obj.getString("title");
//                                        String song = obj.getString("song");
//
//                                        arraylist.add(hymn_no+". "+title);
//                                        hymn.add(song);
//                                        _title.add(title);
//                                        hymnNo.add(hymn_no);


//                                        adpt = new ArrayAdapter<>(Hymns.this, R.layout.song_layout,arraylist);
//                                        list.setAdapter(adpt);

//                                        list.setOnItemClickListener((parent, view, position, id) -> {
//                                            Intent intent = new Intent(Hymns.this,SelectedHymn.class);
//                                            intent.putExtra("hymn_no",hymnNo.get((int) id));
//                                            intent.putExtra("song",hymn.get((int) id));
//                                            intent.putExtra("title",_title.get((int) id));
//                                            startActivity(intent);
//                                        });

//                                        Boolean result = dBhelper.saveHymnsLocally(hymn_no,title,song);
//                                        if (result){
//                                            Log.i("success","hymn stored locally");
//                                        }else{
//                                            Log.e("error","Failed saving hymns locally");
//                                        }
//                                    }
//                                    loadHymns();
//                                }else{
//                                    new SweetAlertDialog(Hymns.this,SweetAlertDialog.SUCCESS_TYPE)
//                                            .setTitleText("Synced")
//                                            .setContentText("No New Hymn found !")
//                                            .setConfirmButton("Okay", sweetAlertDialog -> {
//                                                startActivity(new Intent(Hymns.this,HomeActivity.class));
//                                                finish();
//                                            })
//                                            .show();
//                                }

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