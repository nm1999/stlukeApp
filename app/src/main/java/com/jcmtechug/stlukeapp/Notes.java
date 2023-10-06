package com.jcmtechug.stlukeapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jcmtechug.stlukeapp.Adapter.MyListAdapter;
import com.jcmtechug.stlukeapp.Apis.Urls;
import com.jcmtechug.stlukeapp.DBHelper.DBhelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class Notes extends Fragment {
    private FloatingActionButton floatbtn;
    private DBhelper mydbhelper;
    private  TextView txt;
    private SweetAlertDialog errorDialog,successDialog;
    private EditText mytitle,myversus,notes;
    private Button addnotes;
    private AlertDialog alertDialog;
    private int NOT_SYNCED =0 ,SYNCED =1, user_id ;
    private OkHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        client = new OkHttpClient();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt("user_id",0);

        mydbhelper = new DBhelper(getActivity());


        // assign variable to input variables
        txt = view.findViewById(R.id.txt);
        floatbtn = view.findViewById(R.id.floatbtn);

        // initialize sweetalerts
        errorDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE);
        successDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE);

        //getting the dialog fields
        View mydialog = getLayoutInflater().inflate(R.layout.takenotes,null);
        addnotes = mydialog.findViewById(R.id.addrecord);

        //creating a dialog for create a note
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(mydialog);
        alertDialog= builder.create();

        getNotes(view);
        floatbtn.setOnClickListener(view1 -> alertDialog.show());

        addnotes.setOnClickListener(view12 -> {
            mytitle = mydialog.findViewById(R.id.mytitle);
            myversus = mydialog.findViewById(R.id.biblev);
            notes = mydialog.findViewById(R.id.notes);


            //stringify content
            String header = mytitle.getText().toString();
            String versus = myversus.getText().toString();
            String note = notes.getText().toString();

            SendDataToServer(view,header,versus,note,SYNCED);
            getNotes(view);
        });


        try {
            if (isInternetAvailable()){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SyncingTask task = new SyncingTask();
                        task.execute();
                    }
                });

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    public boolean isInternetAvailable() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ping -c 1 google.com");
        int exitCode = 0;

        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return exitCode == 0;
    }

    private void storeNotes(String header, String versus, String note, int sync_state) {

        // check if the necessary fields have been added
        if (header.isEmpty() && versus.isEmpty() && note.isEmpty()){
            errorDialog.setTitle("Error");
            errorDialog.setContentText("Empty fields");
            errorDialog.show();
        }else {

            Boolean isNoteSave = mydbhelper.insertnotes(header, versus, note,sync_state);

            if (isNoteSave) {
                alertDialog.dismiss();
            }

            mytitle.setText(" ");
            myversus.setText(" ");
            notes.setText(" ");
        }
    }

    private void SendDataToServer(View view, String header, String versus, String note, int sync_state) {

        RequestBody requestBody = new FormBody.Builder()
                .add("title",header)
                .add("versus",versus)
                .add("note",note)
                .add("user_id", String.valueOf(user_id))
                .build();

        Request request = new Request.Builder()
                .url(Urls.TAKE_NOTES)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("err",e.toString());
                storeNotes(header,versus,note,NOT_SYNCED);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        successDialog.setTitle("Success");
                        successDialog.setContentText("Notes saved offline successfully");
                        successDialog.show();
                    }
                });

                getNotes(view);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody result = response.body();
                String jsonData = result.string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ddata",jsonData);
                        if (result != null){
                            try {
                                JSONObject jsonObject = new JSONObject(jsonData);
                                if (!jsonObject.getBoolean("error")){
                                    storeNotes(header,versus,note,SYNCED);
                                    successDialog.setTitle(jsonObject.getString("status"));
                                    successDialog.setContentText(jsonObject.getString("message"));
                                    successDialog.show();

                                }else{
                                    storeNotes(header,versus,note,NOT_SYNCED);
                                    successDialog.setTitle(jsonObject.getString("status"));
                                    successDialog.setContentText(jsonObject.getString("message"));
                                    successDialog.show();
                                }

                            } catch (JSONException e) {
                                Log.e("sxxx",e.toString());
                                throw new RuntimeException(e);
                            }
                        }else{
                            // Empty result
                            Log.e("er","result empty");
                        }

                        getNotes(view);
                    }
                });
            }
        });



    }


    private void getNotes(View view) {
        Cursor get = mydbhelper.getNotes();

        ArrayList<String> title = new ArrayList();
        ArrayList<String> chapter = new ArrayList();
        ArrayList<String> lesson = new ArrayList();
        ArrayList<String> id = new ArrayList<>();
        ArrayList<Integer> sync_status = new ArrayList<Integer>();

        if (get.getCount()>0){
            txt.setVisibility(View.GONE);

            while (get.moveToNext()){
                id.add(get.getString(0));
                title.add(get.getString(1));
                chapter.add(get.getString(2));
                lesson.add(get.getString(3));
                sync_status.add(get.getInt(4));


                MyListAdapter myadapter = new MyListAdapter(getActivity(),id,title,chapter,lesson,sync_status);
                ListView list = view.findViewById(R.id.mylistview);
                list.setAdapter(myadapter);
            }
        }else{
            txt.setText("No summarises yet 🙄🙄");
        }
    }

    private String SyncNotes(){
        Cursor get = mydbhelper.getUnSyncedNotes();
        if (get.getCount()>0) {
            while (get.moveToNext()) {

                Log.e("state", String.valueOf(get.getInt(4)));
//                Log.e("id",);
                String note_id = get.getString(0);

                //checking for all notes that have not been backed up online
                if (get.getInt(4) == 0){

                    RequestBody requestBody = new FormBody.Builder()
                            .add("title",get.getString(1))
                            .add("versus",get.getString(2))
                            .add("note",get.getString(3))
                            .add("user_id", String.valueOf(user_id))
                            .build();

                    Request request = new Request.Builder()
                            .url(Urls.TAKE_NOTES)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e("sync_err",e.toString());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ResponseBody responseBody = response.body();
                            String resp = responseBody.string();
                            Log.e("success",resp);


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("stillid",note_id);
                                    Boolean update = mydbhelper.update_sync_state(note_id);
                                    if (update){
                                        Log.i("sync_report","Synced successfully");
                                    }else{
                                        Log.e("sync_report","Synced failed");
                                    }
                                }
                            });


                        }
                    });
                }

            }
        }
        return null;
    }

    public class SyncingTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return SyncNotes();
        }

        @Override
        protected void onPreExecute() {
            // Update the UI before the long-running operation starts.
        }

        @Override
        protected void onPostExecute(String result) {
            // Update the UI with the result of the long-running operation.
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update the UI with the progress of the long-running operation.
        }
    }

}