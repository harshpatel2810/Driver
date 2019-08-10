package com.classicphoto.rpmfordriver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.classicphoto.rpmfordriver.utils.AppConfig;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends Activity {

    Button btn_login;
    EditText username, password;
    String str_user, str_pass;
    JSONArray dataArray = null;
    String share_username = "";
    String share_password = "";
    String share_userid = "";
    private SQLiteDatabase db;
    Cursor cursor;
    String sqluserid = "";
    String sqlpasswordid = "";
    String sqlid = "";
    ConnectivityManager ConnectionManager;
    NetworkInfo networkInfo;
    String output;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login2);

        createDatabase();

        StrictMode.VmPolicy.Builder newBuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newBuilder.build());

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);

        SharedPreferences splogin = LoginActivity.this.getSharedPreferences("Login", 0);
        share_username = splogin.getString("username", "");
        share_password = splogin.getString("password", "");
        share_userid = splogin.getString("userid", "");

        SharedPreferences spuser = LoginActivity.this.getSharedPreferences("Loginlocal", 0);
        sqluserid = spuser.getString("username", "");
        sqlpasswordid = spuser.getString("password", "");
        share_userid = spuser.getString("userid", "");

        if (share_username.equals("") && share_password.equals("")) {

        } else {
            Intent login = new Intent(getApplicationContext(), DeliveryActivity.class);
            login.putExtra("userid", share_userid);
            startActivity(login);
            finish();
            overridePendingTransition(0, 0);
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_user = username.getText().toString().trim();
                str_pass = password.getText().toString().trim();

                ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    if (str_user.equals("") && str_pass.equals("")) {
                        if (str_user.equals("")) {
                            username.setError("Please enter username");
                        } else if (str_pass.equals("")) {
                            password.setError("Please enter password");
                        }
                    } else {
                        if (sqluserid.equals("") && sqlpasswordid.equals("")) {
                            output = str_user.replace(" ", "%20");
                            String ip = AppConfig.URL + "api/UserInquiry.ashx?apiname=userinquiry&username=" + output + "&password=" + str_pass;
                            new LongOperation().execute(ip);
                        } else if (str_user.equals(sqluserid) && (str_pass.equals(sqlpasswordid))) {
                            output = str_user.replace(" ", "%20");
                            String ip = AppConfig.URL + "api/UserInquiry.ashx?apiname=userinquiry&username=" + output + "&password=" + str_pass;
                            new LongOperation().execute(ip);
                        } else {
                            Alert("Please Enter Correct Data");
                        }
                    }
                } else {

                    if (str_user.equals("") && str_pass.equals("")) {
                        Alert("Please fill in field");
                    } else {
                        if (str_user.equals(sqluserid) && str_pass.equals(sqlpasswordid)) {
                            Intent login = new Intent(getApplicationContext(), DeliveryActivity.class);
                            login.putExtra("userid", share_userid);
                            startActivity(login);
                            finish();
                            overridePendingTransition(0, 0);
                        } else {
                            Alert("Data Incorrect");
                        }
                    }
                }
//                else if (str_user.equals(user) || str_pass.equals(pass)){
//                    Intent login = new Intent(getApplicationContext(),DeliveryActivity.class);
//                    startActivity(login);
//                    finish();
//                    overridePendingTransition(0,0);
//                }
//                else if (str_user.matches(emailPattern))
//                {
//                    Intent login = new Intent(getApplicationContext(),DeliveryActivity.class);
//                    startActivity(login);
//                    finish();
//                    overridePendingTransition(0,0);
//                }
            }
        });
    }

    private class LongOperation extends AsyncTask<String, String, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            Dialog.show();
            Dialog.setMessage("Please Wait....");
            Dialog.setIndeterminate(false);
        }

        //Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            Dialog.dismiss();

            if (Error != null) {

            } else {
                userlogin(Content);
            }
        }
    }

    private void userlogin(String content) {

        if (!content.equals("")) {
            try {
                JSONObject jsobjectcategory = new JSONObject(content);

                String requestcode = jsobjectcategory.getString("statuscode");
                String message = jsobjectcategory.getString("message");
                String data = jsobjectcategory.getString("data");

                if (message.equals("success")) {

                    JSONArray jsonarray = new JSONArray(data);

                    for (int j = 0; j < jsonarray.length(); j++) {
                        dataArray = jsonarray.getJSONArray(j);
                        JSONObject jsobcat = dataArray.getJSONObject(j);

                        String permission = jsobcat.getString("permission");
                        userid = jsobcat.getString("UserId");

                        SharedPreferences splogin = LoginActivity.this.getSharedPreferences("Login", 0);
                        SharedPreferences.Editor Edlogin = splogin.edit();
                        Edlogin.putString("username", str_user);
                        Edlogin.putString("password", str_pass);
                        Edlogin.putString("userid", userid);
                        Edlogin.apply();

                        if (sqluserid.equals("") && sqlpasswordid.equals("")) {
//                            insertIntouser();
//                            getEmployeeName("1");
                            SharedPreferences spuser = LoginActivity.this.getSharedPreferences("Loginlocal", 0);
                            SharedPreferences.Editor Eduser = spuser.edit();
                            Eduser.putString("username", str_user);
                            Eduser.putString("password", str_pass);
                            Eduser.putString("userid", userid);
                            Eduser.apply();
                        }

                        Intent login = new Intent(getApplicationContext(), DeliveryActivity.class);
                        login.putExtra("userid", userid);
                        startActivity(login);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                } else {
                    Alert("Data Wrong");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void Alert(String start) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(start)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void createDatabase() {
        db = openOrCreateDatabase("Login", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Login(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username VARCHAR, password VARCHAR);");
    }

    protected void insertIntouser() {

        String query = "INSERT INTO Login (username,password) " +
                "VALUES('" + str_user + "', '" + str_pass + "');";
        db.execSQL(query);
    }

    public String getEmployeeName(String empNo) {
        String empName = "";
        try {
            String query = "SELECT * FROM Login WHERE id = ?";
            cursor = db.rawQuery(query, new String[]{empNo + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                showRecords();
            }
            return empName;
        } finally {
            cursor.close();
        }
    }

    protected void showRecords() {
        sqlid = cursor.getString(0);
        sqluserid = cursor.getString(1);
        sqlpasswordid = cursor.getString(2);
    }

}
