package com.classicphoto.rpmfordriver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class ResultActivity extends Activity {

    EditText et_consingerno, et_from, et_to, et_docketdate, et_lrtype,
            et_consignor, et_consignee, et_packt, et_weight, et_branchaddress, et_challandate, et_vehicleno;
    String str_noteno, str_status, str_consignor, str_consignee, str_pkt,
            str_weight, str_date, str_destfrom, str_destto, str_docketno, str_pod, str_stationaddress,
            str_challandate, str_vehicleno, str_cntype, str_branch_code;
    Calendar calendar, calendar1;
    TextView txt_docketno, txt_status, txt_challandate, txt_vehicle;
    ImageView img_download, img_share, image_set;
    LinearLayout img_back;
    String date, date1;
    String URL = "http://e-transport.org/Upload/PodFile/";
    TextView txt_lr, txt_sta, txt_consider, txt_docketdate, txt_type, txt_from, txt_to, txt_pkt, txt_weight, txt_branch, titleR;
    ImageView image_download;
    ProgressDialog mProgressDialog;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        str_noteno = i.getStringExtra("noteno");
        str_status = i.getStringExtra("status");
        str_consignor = i.getStringExtra("consignor");
        str_consignee = i.getStringExtra("consignee");
        str_pkt = i.getStringExtra("packet");
        str_weight = i.getStringExtra("weight");
        str_destfrom = i.getStringExtra("destination");
        str_destto = i.getStringExtra("destinationto");
        str_date = i.getStringExtra("date");
        str_docketno = i.getStringExtra("docketno");
        str_pod = i.getStringExtra("pod");
        str_stationaddress = i.getStringExtra("stationaddress");
        str_challandate = i.getStringExtra("chalandate");
        str_vehicleno = i.getStringExtra("vehno");
        str_cntype = i.getStringExtra("cntype");
        str_branch_code = i.getStringExtra("branch_code");

        calendar = Calendar.getInstance();
        if(str_date != null) {
            String ackwardRipOff = str_date.replace("/Date(", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            Date d = new Date(timeInMillis);
            date = new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
            calendar.setTimeInMillis(timeInMillis);
        }
        calendar1 = Calendar.getInstance();
        if(str_challandate != null) {
            String ackwardRipOff1 = str_challandate.replace("/Date(", "").replace(")/", "");
            Long timeInMillis1 = Long.valueOf(ackwardRipOff1);
            Date d1 = new Date(timeInMillis1);
            date1 = new SimpleDateFormat("dd/MM/yyyy").format(d1).toString();
            calendar1.setTimeInMillis(timeInMillis1);
        }
        et_consingerno = (EditText) findViewById(R.id.et_consignernumber);
        et_docketdate = (EditText) findViewById(R.id.et_docketdate);
        et_from = (EditText) findViewById(R.id.et_from);
        et_to = (EditText) findViewById(R.id.et_to);
        et_consignor = (EditText) findViewById(R.id.et_consignor);
        et_consignee = (EditText) findViewById(R.id.et_consignee);
        et_packt = (EditText) findViewById(R.id.et_packet);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_branchaddress = (EditText) findViewById(R.id.et_branchaddress);
        et_challandate = (EditText) findViewById(R.id.et_chalandate);
        et_vehicleno = (EditText) findViewById(R.id.et_vehicleno);
        et_lrtype = (EditText) findViewById(R.id.et_lrtype);
        titleR = findViewById(R.id.titleR);
        image_set = (ImageView)findViewById(R.id.set_image);
        image_download = (ImageView)findViewById(R.id.download_image);

        txt_docketno = (TextView) findViewById(R.id.docketno);
        txt_status = (TextView) findViewById(R.id.status);
        txt_challandate = (TextView) findViewById(R.id.chalandate);
        txt_vehicle = (TextView) findViewById(R.id.vehicleno);
        txt_lr = (TextView) findViewById(R.id.txt_lr);
        txt_sta = (TextView) findViewById(R.id.txt_sta);
        txt_consider = (TextView) findViewById(R.id.consinumber);
        txt_type = (TextView) findViewById(R.id.lrtype);
        txt_from = (TextView) findViewById(R.id.from);
        txt_to = (TextView) findViewById(R.id.to);
        txt_pkt = (TextView) findViewById(R.id.packet);
        txt_weight = (TextView) findViewById(R.id.weight);
        txt_branch = (TextView) findViewById(R.id.branchaddress);
        txt_docketdate = (TextView) findViewById(R.id.docketdate);

        img_download = (ImageView) findViewById(R.id.download);
        img_share = (ImageView) findViewById(R.id.share);

        img_back = (LinearLayout) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        et_consingerno.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_docketdate.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_from.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_to.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_consignor.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_consignee.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_packt.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_weight.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_branchaddress.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_challandate.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_vehicleno.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);
        et_lrtype.getBackground().mutate().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP);


        if (str_noteno != null) {
            et_consingerno.setText(str_noteno);
        }
        if (date != null) {
            et_docketdate.setText(date);
        }
        if (str_consignor != null) {
            et_consignor.setText(str_consignor);
        }
        if (str_consignee != null) {
            et_consignee.setText(str_consignee);
        }
        if (str_pkt != null) {
            et_packt.setText(str_pkt);
        }
        if (str_weight != null) {
            et_weight.setText(str_weight);
        }
        if (str_destfrom != null) {
            et_from.setText(str_destfrom);
        }
        if (str_destto != null) {
            et_to.setText(str_destto);
        }
        if (str_stationaddress != null) {
            String str = str_stationaddress.replace("<br/>", "\n");
            et_branchaddress.setText(str);
        }
        if (date1 != null) {
            et_challandate.setText(date1);
        }
        if (str_vehicleno != null) {
            et_vehicleno.setText(str_vehicleno);
        }
        if (str_cntype != null) {
            et_lrtype.setText(str_cntype);
        }

        if (str_status != null) {
            txt_status.setText(str_status);
        }
        if (str_docketno != null) {
            txt_docketno.setText(str_branch_code + " - " + str_docketno);
        }

        if (str_pod != null){
            new DownloadImage().execute("http://bgfclogistics.in/Upload/PodFile/"+str_pod);
        }


        if (str_status.equals("In Transit")) {
            txt_challandate.setVisibility(View.VISIBLE);
            txt_vehicle.setVisibility(View.VISIBLE);
            et_challandate.setVisibility(View.VISIBLE);
            et_vehicleno.setVisibility(View.VISIBLE);
        } else {
            txt_challandate.setVisibility(View.GONE);
            txt_vehicle.setVisibility(View.GONE);
            et_challandate.setVisibility(View.GONE);
            et_vehicleno.setVisibility(View.GONE);
        }

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str_status.equals("In Transit")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "LR No: " + str_branch_code + " - " + str_docketno + "\n" + "Status: " +
                            str_status + "\n" + "Party Invoice No: " + str_noteno + "\n" + "LR Date: " + date + "\n"
                            + "LR Type: " + str_cntype + "\n" + "From Station: " + str_destfrom + "\n" + "To Station: " + str_destto + "\n" + "No of PKTS: " + str_pkt + "\n" +
                            "Total Weight: " + str_weight + "\n" + "Challan Date: " + date1 + "\n"
                            + "Vehicle No: " + str_vehicleno + "\n" + "Branch Address: " + str_stationaddress);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "LR No: " + str_docketno + "\n" + "Status: " +
                            str_status + "\n" + "Party Invoice No: " + str_noteno + "\n" + "LR Date: " + date + "\n"
                            + "LR Type: " + str_cntype + "\n" + "From Station: " + str_destfrom + "\n" + "To Station: " + str_destto + "\n" + "No of PKTS: " + str_pkt + "\n" +
                            "Total Weight: " + str_weight + "\n" + "Branch Address: " + str_stationaddress);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            }
        });

        img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str_pod.equals("")) {
                    Toast.makeText(getApplicationContext(), "No File Available", Toast.LENGTH_LONG).show();
//
                } else {

                }
            }
        });

        image_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str_pod.equals("")) {
                    Toast.makeText(getApplicationContext(), "No File Available", Toast.LENGTH_LONG).show();
//
                } else {
//                    new DownloadImage().execute("http://shreebalajicargomovers.net/Upload/PodFile/"+str_pod);
                }
            }
        });
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ResultActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            // Close progressdialog
            mProgressDialog.dismiss();
            image_set.setImageBitmap(result);
//            storeImage(result);
        }
    }

    public void showPdf() {
        File file = new File(Environment.getExternalStorageDirectory() + "/pdf/Read.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

    private String getDate(long timeStamp) {

        try {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(timeStamp);
            String date = DateFormat.format("dd-MM-yyyy", cal).toString();
            return date;
        } catch (Exception ex) {
            return "xx";
        }
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private  File getOutputMediaFile(){
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File mediaStorageDir = new File(root + "/SBCM_images");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}