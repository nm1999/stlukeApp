package com.jcmtechug.stlukeapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jcmtechug.stlukeapp.Apis.Urls;
import com.jcmtechug.stlukeapp.DBHelper.DBhelper;
import com.jcmtechug.stlukeapp.DashboardActivities.HomeActivity;

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

public class Login extends AppCompatActivity {
    private Button register;
    private ArrayList<String> myarr;
    private TextView signin;
    private DBhelper mydbhelper;
    private SweetAlertDialog errorDialog;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydbhelper = new DBhelper(this);
        errorDialog = new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE);
        progressDialog = new ProgressDialog(Login.this);
        progressBar = findViewById(R.id.progressbar);

        myarr = new ArrayList<>();

        EditText firstname = findViewById(R.id.firstName);
        EditText surname = findViewById(R.id.surName);
        EditText email = findViewById(R.id.emailaddress);
        EditText contact = findViewById(R.id.contact);
        EditText password = findViewById(R.id.password);
        EditText confirm = findViewById(R.id.confirm_password);
        signin = findViewById(R.id.has_account);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),SignIn.class);
                startActivity(nxt);
            }
        });


        // acessing the data from the database
        Cursor cursor = mydbhelper.getData();
        if (cursor.getCount()>0){
//            Toast.makeText(this, "Already have an account", Toast.LENGTH_SHORT).show();
            Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(nxt);
            finish();

        }

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname_str = firstname.getText().toString();
                String surname_str = surname.getText().toString();
                String email_str = email.getText().toString();
                String contact_str = contact.getText().toString();
                String psw = password.getText().toString();
                String conf_psw =confirm.getText().toString();

                if (TextUtils.isEmpty(firstname_str)){
                    firstname.setError("Enter your Christain Name ");
                    firstname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(surname_str)){
                    surname.setError("Enter your Other Name ");
                    surname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email_str)){
                    email.setError("Enter your email");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(contact_str)){
                    email.setError("Enter your phone number");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(psw)){
                    password.setError("Enter your password");
                    password.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(conf_psw)){
                    password.setError("Enter your confirm password");
                    password.requestFocus();
                    return;
                }

                if (!psw.equals(conf_psw)){
                    password.setError("Password mismatch");
                    password.requestFocus();
                    return;
                }

                if (isConnected()){
//                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(firstname_str,surname_str,email_str,contact_str,psw);
                }else{
                    new SweetAlertDialog(Login.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Failure")
                            .setContentText("Connect to internet and Try again")
                            .show();
                }


            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public void registerUser(String firstname_str, String surname_str, String email_str, String contact_str, String psw) {
        OkHttpClient client  = new OkHttpClient();
        progressDialog.setTitle("Registering ....");
        progressDialog.show();

        RequestBody data = new FormBody.Builder()
                .add("christian_name",firstname_str)
                .add("other_name",surname_str)
                .add("email",email_str)
                .add("phone_number",contact_str)
                .add("password",psw)
                .build();
        Request request = new Request.Builder()
                .url(Urls.REGISTER_URL)
                .post(data)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        errorDialog.setTitle("Failure");
                        errorDialog.setContentText("No Internet connection");
                        errorDialog.show();
                    }
                });

                Log.e("failure",e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();

                String json = responseBody.string();
                Log.i("data",json);
                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!json.isEmpty()){
                            try {
                                JSONObject jsonObject = new JSONObject(json);

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

                                    progressDialog.dismiss();
                                    //redirect to home
                                    Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(nxt);
                                    finish();

                                }else{
                                    progressDialog.dismiss();
                                    errorDialog.setTitle(jsonObject.getString("status"));
                                    errorDialog.setContentText(jsonObject.getString("message"));
                                    errorDialog.show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

//        SaveUserOnlocally(firstname_str,surname_str,email_str,contact_str,psw);
    }

    private void SaveUserOnlocally(String firstname_str, String surname_str, String email_str, String contact_str, String psw) {
        Boolean res = mydbhelper.insertuser(firstname_str,surname_str,email_str,contact_str);
        if (res==true){
            Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(nxt);
        }else {
            Toast.makeText(Login.this, "Not registered", Toast.LENGTH_SHORT).show();
        }
    }

}