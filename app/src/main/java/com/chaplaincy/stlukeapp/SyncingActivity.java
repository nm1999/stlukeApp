package com.chaplaincy.stlukeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaplaincy.stlukeapp.Apis.Urls;
import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SyncingActivity extends AppCompatActivity {
    private String local_christian_name,local_other_name,localemail,localcontact;
    private DBhelper mydbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncing);

        mydbhelper = new DBhelper(this);

        // for old users create a new columns i.e sync_state, editted, deleted
        mydbhelper.createColumnDoNotExist();

        Cursor cr = mydbhelper.getData();
//
        while (cr.moveToNext()){
            local_christian_name = (cr.getString(0));
            local_other_name = (cr.getString(1));
            localemail = (cr.getString(2));
            localcontact = (cr.getString(3));
        }

        Log.i("name","HERE IS THE NAME"+local_christian_name+" "+local_other_name+" email "+localemail+" contact"+localcontact);

        registerUser(local_christian_name, local_other_name, localemail, localcontact, localemail);
    }

    private void registerUser(String local_christian_name, String local_other_name, String localemail, String localcontact, String password) {
        OkHttpClient client  = new OkHttpClient();


        RequestBody data = new FormBody.Builder()
                .add("christian_name",local_christian_name)
                .add("other_name",local_other_name)
                .add("email",localemail)
                .add("phone_number",localcontact)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(Urls.REGISTER_URL)
                .post(data)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("d",e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String json = responseBody.string();
                Log.e("daaa",json);

                SyncingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(json);

                            if (!jsonObject.getBoolean("error")){

                                Log.i("success",jsonObject.getString("message"));
                                SharedPreferences sharedPreferences = getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                // getting the object of the message key
                                JSONObject details = jsonObject.getJSONObject("message");

                                editor.putInt("user_id",details.getInt("id"));
                                editor.putString("christian_name", details.getString("christian_name"));
                                editor.putString("other_name",details.getString("other_name"));
                                editor.putString("email",details.getString("email"));
                                editor.putString("contact",details.getString("contact"));
                                editor.apply();

                                //backup the notes for this user as well
                                SubmitNotes(details.getInt("id"));
                            }else{
                                Log.e("no data","No data in the response");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });

            }
        });
    }

    private void SubmitNotes(int id) {
        OkHttpClient client = new OkHttpClient();

        Cursor cursor = mydbhelper.getNotes();

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                RequestBody requestBody = new FormBody.Builder()
                        .add("title",cursor.getString(1))
                        .add("versus",cursor.getString(2))
                        .add("note",cursor.getString(3))
                        .add("user_id", String.valueOf(id))
                        .build();

                Request request = new Request.Builder()
                        .url(Urls.TAKE_NOTES)
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("err",e.toString());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        ResponseBody result = response.body();
                        String jsonData = result.string();
                        Log.i("success",jsonData);
                    }
                });
            }
        }

        //redirect to home
        Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(nxt);
        finish();
    }


}