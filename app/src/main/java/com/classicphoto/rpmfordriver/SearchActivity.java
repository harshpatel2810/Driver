package com.classicphoto.rpmfordriver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classicphoto.rpmfordriver.Model.Lrno1;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends Activity {

    EditText branch, lr;
    ImageView img_register;
    String str_branch, str_lr;
    JSONArray dataArray = null;
    String result_noteno, result_status, result_date, result_from, result_to, result_consignor,
            result_consignee, result_pkt, result_weight, result_pod, result_statioaddress,
            result_challandate, result_vehicleno, result_cntype;
    String message;
    TextView title;
    LinearLayout img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        branch = (EditText) findViewById(R.id.et_branch);
        lr = (EditText) findViewById(R.id.et_lrno);
        img_register = (ImageView) findViewById(R.id.submit);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        title = findViewById(R.id.title);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_branch = branch.getText().toString().trim();
                str_lr = lr.getText().toString().trim();

                if (str_branch.equals("")) {
                    branch.setError("Please enter branch code");
                } else if (str_lr.equals("")) {
                    lr.setError("Please enter lr no");
                } else {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    @SuppressLint("MissingPermission") NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {
                        String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + str_branch + "&lrno=" + str_lr;
                        new LongOperation().execute(ip);
                    } else {
                        Alert("Please Check Internet Connection");

                    }
                }
            }
        });
    }

    private class LongOperation extends AsyncTask<String, String, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SearchActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            Dialog.show();
            Dialog.setMessage("Please Wait....");
            Dialog.setCancelable(false);
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
                usercategory(Content);
                if (message.equals("success")) {


                    Date dateNew = null;
                    Date datefi = null;

                    String ackwardRipOff = result_date.replace("/Date(", "").replace(")/", "");
                    Long timeInMillis = Long.valueOf(ackwardRipOff);
                    Date d = new Date(timeInMillis);
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate1 = df1.format(d);


                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c);

                    try {
                        dateNew = df1.parse(formattedDate1);
                        datefi = df.parse(formattedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long different = datefi.getTime() - dateNew.getTime();
                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;

                    long elapsedDays = different / daysInMilli;
                    different = different % daysInMilli;
                    long elapsedHours = different / hoursInMilli;
                    different = different % hoursInMilli;
                    long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;
                    long elapsedSeconds = different / secondsInMilli;

                    int day = 60;

                    if(elapsedDays < day){
                        Intent submit = new Intent(getApplicationContext(), ResultActivity.class);
                        submit.putExtra("noteno", result_noteno);
                        submit.putExtra("status", result_status);
                        submit.putExtra("consignor", result_consignor);
                        submit.putExtra("consignee", result_consignee);
                        submit.putExtra("packet", result_pkt);
                        submit.putExtra("weight", result_weight);
                        submit.putExtra("destination", result_from);
                        submit.putExtra("destinationto", result_to);
                        submit.putExtra("date", result_date);
                        submit.putExtra("docketno", str_lr);
                        submit.putExtra("pod", result_pod);
                        submit.putExtra("stationaddress", result_statioaddress);
                        submit.putExtra("chalandate", result_challandate);
                        submit.putExtra("vehno", result_vehicleno);
                        submit.putExtra("cntype", result_cntype);
                        submit.putExtra("branch_code", branch.getText().toString());
                        startActivity(submit);
                        branch.setText("");
                        lr.setText("");
                    }else {
                        Intent submit = new Intent(getApplicationContext(), Result2Activity.class);
                        startActivity(submit);
                        branch.setText("");
                        lr.setText("");
                    }

                } else {
                    branch.setText("");
                    lr.setText("");
                }
            }
        }
    }

    private void usercategory(String content) {

        if (!content.equals("")) {
            try {
                JSONObject jsobjectcategory = new JSONObject(content);

                String requestcode = jsobjectcategory.getString("statuscode");
                message = jsobjectcategory.getString("message");
                String data = jsobjectcategory.getString("data");

                if (message.equals("success")) {

                    JSONArray jsonarray = new JSONArray(data);

                    for (int j = 0; j < jsonarray.length(); j++) {
                        dataArray = jsonarray.getJSONArray(j);
                        JSONObject jsobcat = dataArray.getJSONObject(j);
                        Lrno1 lrno = new Lrno1();
                        lrno.id = jsobcat.getString("Id");
                        lrno.lr_branchcode = jsobcat.getString("FromBranchCode");
                        lrno.lr_challanno = jsobcat.getString("ChallanNo");
                        lrno.lr_package = jsobcat.getString("Package");
                        lrno.lr_ConsignorName = jsobcat.getString("ConsignorName");
                        lrno.lr_ConsigneeName = jsobcat.getString("ConsigneeName");
                        lrno.lr_weight = jsobcat.getString("GrossWeight");
                        lrno.lr_tobranchname = jsobcat.getString("ToBranchName");
                        lrno.lr_station = jsobcat.getString("Station");
                        lrno.lr_branchname = jsobcat.getString("BranchName");
                        lrno.lr_machineqty = jsobcat.getString("MachineQty");
                        lrno.lr_status = jsobcat.getString("Status");
                        lrno.lr_partyinvoice = jsobcat.getString("PartyInvoiceNo");
                        lrno.lr_lrdate = jsobcat.getString("Lrdate");
                        lrno.lr_podfilename = jsobcat.getString("PODFileName");
                        lrno.lr_stationaddress = jsobcat.getString("StationAddress");
                        lrno.lr_challanentries = jsobcat.getString("ChallanEntries");
                        lrno.lr_cntype = jsobcat.getString("CNType");
                        lrno.lr_fromstation = jsobcat.getString("FromStation");

                        result_noteno = lrno.lr_partyinvoice;
                        result_status = lrno.lr_status;
                        result_consignor = lrno.lr_ConsignorName;
                        result_consignee = lrno.lr_ConsigneeName;
                        result_weight = lrno.lr_weight;
                        result_pkt = lrno.lr_package;

//                        result_to = lrno.lr_branchname;
                        result_statioaddress = lrno.lr_stationaddress;
                        result_cntype = lrno.lr_cntype;

                        String date = lrno.lr_lrdate;
                        date = date.replace("/Date(", "");
                        date = date.replace(")/", "");
                        result_date = date;

                        if (lrno.lr_fromstation.equals("")) {
                            result_from = lrno.lr_branchname;
                        } else {
                            result_from = lrno.lr_fromstation;
                        }

                        if (lrno.lr_station.equals("")) {
                            result_to = lrno.lr_tobranchname;
                        } else {
                            result_to = lrno.lr_station;
                        }

                        if (lrno.lr_podfilename.equals("")) {
                            result_pod = "";
                        } else {
                            result_pod = lrno.lr_podfilename;
                        }

                        String array = lrno.lr_challanentries;

                        JSONArray jsonarray1 = new JSONArray(array);
                        for (int k = 0; k < jsonarray1.length(); k++) {
                            JSONObject jsobcat1 = jsonarray1.getJSONObject(j);
                            lrno.lr_cchallandate = jsobcat1.getString("ChallaDate");
                            lrno.lr_cvehno = jsobcat1.getString("VehNo");

                            String date1 = lrno.lr_cchallandate;
                            date1 = date1.replace("/Date(", "");
                            date1 = date1.replace(")/", "");
                            result_challandate = date1;

                            result_vehicleno = lrno.lr_cvehno;
                        }
                    }
                } else {
                    Alert("Data Not Found");
                    branch.setText("");
                    lr.setText("");
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

}
