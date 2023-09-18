package com.chaplaincy.stlukeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SignIn extends AppCompatActivity {
    private Button login;
    private TextView register;
    private ProgressDialog progressDialog;

    private EditText useremail, userpassword;
    private SweetAlertDialog errorDialog,successDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
//        getSupportActionBar().hide();

        login = findViewById(R.id.loginbtn);
        register = findViewById(R.id.register_link);
        progressDialog = new ProgressDialog(SignIn.this);

        errorDialog = new SweetAlertDialog(SignIn.this,SweetAlertDialog.ERROR_TYPE);
        successDialog = new SweetAlertDialog(SignIn.this,SweetAlertDialog.SUCCESS_TYPE);

        DBhelper mydbhelper = new DBhelper(this);
        Cursor cursor = mydbhelper.getData();

        if (cursor.getCount()>0){
            Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(nxt);
            finish();

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useremail = findViewById(R.id.user_email);
                userpassword = findViewById(R.id.user_password);

                progressDialog.setMessage("Verifying account");
                progressDialog.show();
                String email = useremail.getText().toString();
                String password = userpassword.getText().toString();

                checkAuth(email,password);
//
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),Login.class);
                startActivity(nxt);
            }
        });
    }

    private void checkAuth(String email, String password) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("password",password)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.107/stlukeApp_Api/v1/auth.php?apiCall=login")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody result = response.body();
                String jsonData = result.string();

                SignIn.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonData != null){
                            try {
                                JSONObject jsonObject = new JSONObject(jsonData);

                                if (!jsonObject.getBoolean("error")){
                                    // get the object from the response
                                    JSONObject message_obj = jsonObject.getJSONObject("message");
                                    if (message_obj != null){
                                        // save data in sharedpreferences
                                        SharedPreferences sharedPreferences = getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        JSONObject details = jsonObject.getJSONObject("message");

                                        editor.putString("user_id",details.getString("id"));
                                        editor.putString("christian_name", details.getString("christian_name"));
                                        editor.putString("other_name",details.getString("other_name"));
                                        editor.putString("email",details.getString("email"));
                                        editor.putString("contact",details.getString("contact"));

                                        Intent nxt = new Intent(getApplicationContext(),Login.class);
                                        startActivity(nxt);
                                    }else{
                                        errorDialog.setTitle("Failure");
                                        errorDialog.setContentText("Invalid response");
                                        errorDialog.show();
                                    }
                                }else{
                                    errorDialog.setTitle(jsonObject.getString("status"));
                                    errorDialog.setContentText(jsonObject.getString("message"));
                                    errorDialog.show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
                            // no jsondata
                            Log.e("No server response","No data in the server response");
                            errorDialog.setTitle("Failure");
                            errorDialog.setContentText("Something went wrong. Try Again");
                            errorDialog.show();
                        }
                    }
                });
            }
        });
    }
}