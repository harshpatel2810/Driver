package com.classicphoto.rpmfordriver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.classicphoto.rpmfordriver.utils.AppConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ListActivity extends AppCompatActivity {

    ListView list;
    SQLiteHelper SQLITEHELPER;
    static SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    String idstr, branchstr, lrnostr, imagepathstr, Useridstr;
    SQLiteListAdapter ListAdapter;
    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> Lrno_ArrayList = new ArrayList<String>();
    ArrayList<String> Branch_ArrayList = new ArrayList<String>();
    ArrayList<String> Image_ArrayList = new ArrayList<String>();
    ArrayList<String> Date_ArrayList = new ArrayList<String>();
    ArrayList<String> UserId_ArrayList = new ArrayList<String>();
    private static final String SELECT_SQL = "SELECT * FROM Transports";
    //    private Cursor c;
    Uri mImageUri;
    Bitmap bitmapImage;
    String encodedImageData;
    String response = "";
    String useridstr = "";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list = (ListView) findViewById(R.id.list);
        SQLITEHELPER = new SQLiteHelper(this);

    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata();

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM Transports", null);

        ID_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));

                Lrno_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Lrno)));

                Branch_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Brach)));

                Image_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_image)));

                Date_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLITEHELPER.KEY_date)));

                UserId_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLITEHELPER.KEY_userid)));

            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(ListActivity.this,

                ID_ArrayList,
                Lrno_ArrayList,
                Branch_ArrayList,
                Image_ArrayList,
                Date_ArrayList,
                UserId_ArrayList

        );

        list.setAdapter(ListAdapter);
        cursor.close();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }


    class SQLiteListAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> userID;
        ArrayList<String> Userlr;
        ArrayList<String> branch;
        ArrayList<String> image;
        ArrayList<String> date;
        ArrayList<String> UserId;


        public SQLiteListAdapter(
                Context context2,
                ArrayList<String> id,
                ArrayList<String> Userlr,
                ArrayList<String> branch,
                ArrayList<String> image,
                ArrayList<String> date,
                ArrayList<String> UserId
        ) {

            this.context = context2;
            this.userID = id;
            this.Userlr = Userlr;
            this.branch = branch;
            this.image = image;
            this.date = date;
            this.UserId = UserId;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return userID.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public View getView(final int position, View child, ViewGroup parent) {

            final Holder holder;

            LayoutInflater layoutInflater;

            if (child == null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.contact_list_row, null);

                holder = new Holder();

                holder.textviewid = (TextView) child.findViewById(R.id.textname);
                holder.textviewname = (TextView) child.findViewById(R.id.text);
                holder.sync = (ImageView) child.findViewById(R.id.imageyes);

                child.setTag(holder);

            } else {

                holder = (Holder) child.getTag();
            }
            holder.textviewname.setText(Userlr.get(position) + " - " + branch.get(position));
            holder.textviewid.setText(date.get(position));

            holder.sync.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {
                        useridstr = userID.get(position);
                        // DATA FETCH IN SQLLITE DATABASE....
                        getEmployeeName(userID.get(position));

                    } else {
                        Alert("Network Not Available");
                    }

                }
            });

            return child;
        }

        public class Holder {
            TextView textviewid;
            TextView textviewname;
            ImageView sync;
        }

        public String getEmployeeName(String empNo) {
            String empName = "";
            try {
                String query = "SELECT * FROM Transports WHERE id = ?";
                cursor = SQLITEDATABASE.rawQuery(query, new String[]{empNo + ""});
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
            idstr = cursor.getString(0);
            branchstr = cursor.getString(1);
            lrnostr = cursor.getString(2);
            imagepathstr = cursor.getString(3);
            Useridstr = cursor.getString(5);

            mImageUri = Uri.parse(imagepathstr);
            if (mImageUri != null) {
                bitmapImage = BitmapFactory.decodeFile(String.valueOf(mImageUri));
                encodedImageData = getEncoded64ImageStringFromBitmap(bitmapImage);

                AsyncTaskRunner postReq = new AsyncTaskRunner();
                postReq.execute("start");
            }
        }

        private void updateList() {
            cursor.requery();
        }

        private class AsyncTaskRunner extends AsyncTask<String, String, String> {

            private ProgressDialog Dialog = new ProgressDialog(ListActivity.this);

            @Override
            protected String doInBackground(String... params) {
                response = "";

                try {
                    String url = AppConfig.URL + "api/ApprovedByAuditor.ashx";
                    URL object = new URL(url);

                    HttpURLConnection con = (HttpURLConnection) object.openConnection();


                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
                    con.setRequestMethod("POST");

                    JSONObject cred = new JSONObject();
                    cred.put("BranchCode", branchstr);
                    cred.put("LrNo", lrnostr);
                    cred.put("ImgFileBase64", encodedImageData);
                    cred.put("UserId", Useridstr);

                    DataOutputStream localDataOutputStream = new DataOutputStream(con.getOutputStream());
                    localDataOutputStream.writeBytes(cred.toString());
                    localDataOutputStream.flush();
                    localDataOutputStream.close();

                    con.connect();
                    int responseCode = con.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } else {
                        response = "";

                    }
                } catch (Exception e) {
                    Log.v("ErrorAPP", e.toString());
                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                Dialog.dismiss();
                Deliverydata(response);
                super.onPostExecute(s);
            }

            @Override
            protected void onPreExecute() {
                Dialog.show();
                Dialog.setMessage("Please Wait....");
                Dialog.setCancelable(false);
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }

        }

        private void Deliverydata(String response) {

            if (!response.equals("")) {
                try {

                    JSONObject jsobjectcategory = new JSONObject(response);

                    String requestcode = jsobjectcategory.getString("statuscode");
                    String message = jsobjectcategory.getString("message");
                    String data = jsobjectcategory.getString("data");
                    String count = jsobjectcategory.getString("count");

                    if (message.equals("success")) {
                        // DATA DELETE IN SQLLITE DATABASE....
                        SQLITEDATABASE.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.KEY_ID + "=" + useridstr, null);
                        notifyDataSetChanged();
                        updateList();

                        Intent refresh = new Intent(context, ListActivity.class);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
//                    ShowSQLiteDBdata() ;
                    } else if (requestcode.equals("401")) {
                        Alert("Already Delivered");
                        // DATA DELETE IN SQLLITE DATABASE....
                        SQLITEDATABASE.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.KEY_ID + "=" + useridstr, null);
                        notifyDataSetChanged();
                        updateList();

                        Intent refresh = new Intent(context, ListActivity.class);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    } else {
                        Alert("Data Error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            return imgString;
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
