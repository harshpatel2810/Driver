package com.classicphoto.rpmfordriver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classicphoto.rpmfordriver.Adapter.DrawerItemCustomAdapter;
import com.classicphoto.rpmfordriver.Model.DataModel;
import com.classicphoto.rpmfordriver.Model.Lrno;
import com.classicphoto.rpmfordriver.utils.AppConfig;
import com.classicphoto.rpmfordriver.utils.ParsedResponse;
import com.classicphoto.rpmfordriver.utils.Soap;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DeliveryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btn_submit, btn_go, btn_code, btn_submit_next;
    AutoCompleteTextView auto_branch, auto_lr;
    EditText et_lr, et_consinnename, et_totalpackage, et_totalweight, et_from_branch, et_to_branch, et_machineqty;
    ImageView img_camera, img_set;
    LinearLayout l_detail, l_camera, l_submit;
    Uri mImageUri;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    String branch_code;
    String lr_no;
    String str_lr, str_consineename, str_consigorname, str_totalpackage, str_totalweight,
            str_from_branch, str_to_branch, str_machineqty;
    String encodedImageData = "";
    String str_branchcode, str_lrno;
    //    String[] language ={"AHM"};
    String response = "";

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyBRjYhJZr11lN7eITYhqivRLZF1UuG7JcY";
    ArrayList<Lrno> splitUps = new ArrayList<>();
    JSONArray dataArray = null;
    TextView textlr;
    private SQLiteDatabase db;
    String path;
    Bitmap bitmapImage, bitmapImage1;
    ProgressDialog pd;
    String message = "";
    String formattedDate;
    String userid = "", barcode = "";
    Toolbar toolbar;
    private CharSequence mDrawerTitle, mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String select_button = "";
    String select_type = "";
    ImageView image_add;
    Bitmap processedBitmap;
    String selection = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery2);

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        openRun();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());

        setupToolbar();

        Intent i = getIntent();
        userid = i.getStringExtra("userid");
        createDatabase();

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit_next = (Button) findViewById(R.id.btn_submit_next);
//        btn_code = (Button)findViewById(R.id.btn_code);
        img_camera = (ImageView) findViewById(R.id.imageView_camera);
        img_set = (ImageView) findViewById(R.id.imageView_set);
        btn_go = (Button) findViewById(R.id.btn_go);
        l_detail = (LinearLayout) findViewById(R.id.linear_detail);
        l_camera = (LinearLayout) findViewById(R.id.linear_camera);
        l_submit = (LinearLayout) findViewById(R.id.linear_submit);
        et_lr = (EditText) findViewById(R.id.et_lr);
        et_consinnename = (EditText) findViewById(R.id.et_consineename);
        et_from_branch = (EditText) findViewById(R.id.et_from_branch);
        et_to_branch = (EditText) findViewById(R.id.et_to_branch);
        et_totalpackage = (EditText) findViewById(R.id.et_totalpackage);
        et_totalweight = (EditText) findViewById(R.id.et_totalweight);
        et_machineqty = (EditText) findViewById(R.id.et_machineqty);
        auto_branch = (AutoCompleteTextView) findViewById(R.id.btn_branchcode);
        auto_lr = (AutoCompleteTextView) findViewById(R.id.btn_lrno);
        textlr = (TextView) findViewById(R.id.textviewlr);
        image_add = (ImageView) findViewById(R.id.image_add);

        DataModel[] drawerItem = new DataModel[5];

        drawerItem[0] = new DataModel(R.mipmap.new_file, "Manual");
        drawerItem[1] = new DataModel(R.mipmap.barcode, "Barcode");
        drawerItem[2] = new DataModel(R.drawable.search_icon, "Search LR");
        drawerItem[3] = new DataModel(R.mipmap.sync, "Sync");
        drawerItem[4] = new DataModel(R.mipmap.logout_icon, "Logout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

//        if (CropActivity.croppedImage != null){
//            img_set.setImageBitmap(CropActivity.croppedImage);
//        }

        et_totalweight.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(9, 3)});
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_lr = et_lr.getText().toString().trim();
                str_consineename = et_consinnename.getText().toString().trim();
                str_totalpackage = et_totalpackage.getText().toString().trim();
                str_totalweight = et_totalweight.getText().toString().trim();
                str_from_branch = et_from_branch.getText().toString().trim();
                str_to_branch = et_to_branch.getText().toString().trim();
                str_machineqty = et_machineqty.getText().toString().trim();

                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    if (str_lr.equals("") || str_consineename.equals("") || str_totalpackage.equals("") || str_totalweight.equals("")
                            || str_from_branch.equals("") || str_to_branch.equals("") || str_machineqty.equals("")) {

                        Toast.makeText(getApplicationContext(), "Please enter all field", Toast.LENGTH_LONG).show();
                    }  else {
                        select_button = "submit";
                        AsyncTaskRunner postReq = new AsyncTaskRunner();
                        postReq.execute("start");
//                        Alert("Please Capture Image");
                    }

                } else {
                    insertIntoDB();

                    auto_branch.setText("");
                    auto_lr.setText("");

                    l_detail.setVisibility(View.GONE);
                    l_camera.setVisibility(View.GONE);
                    l_submit.setVisibility(View.GONE);

                    img_camera.setVisibility(View.VISIBLE);
                    img_set.setVisibility(View.GONE);
                    image_add.setVisibility(View.GONE);

                    Intent submit = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(submit);
                    overridePendingTransition(0, 0);
//                    if (bitmapImage != null) {
//
//                    } else {
//                        Alert("Please Capture Image");
//                    }
                }
            }
        });

        btn_submit_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_lr = et_lr.getText().toString().trim();
                str_consineename = et_consinnename.getText().toString().trim();
                str_totalpackage = et_totalpackage.getText().toString().trim();
                str_totalweight = et_totalweight.getText().toString().trim();
                str_from_branch = et_from_branch.getText().toString().trim();
                str_to_branch = et_to_branch.getText().toString().trim();
                str_machineqty = et_machineqty.getText().toString().trim();

                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    if (str_lr.equals("") || str_consineename.equals("") || str_totalpackage.equals("") || str_totalweight.equals("")
                            || str_from_branch.equals("") || str_to_branch.equals("") || str_machineqty.equals("")) {

                        Toast.makeText(getApplicationContext(), "Please enter all field", Toast.LENGTH_LONG).show();
                    } else {
                        select_button = "submitnext";
                        AsyncTaskRunner postReq = new AsyncTaskRunner();
                        postReq.execute("start");
                    }

                } else {
                    insertIntoDB();

                    auto_branch.setText("");
                    auto_lr.setText("");

                    l_detail.setVisibility(View.GONE);
                    l_camera.setVisibility(View.GONE);
                    l_submit.setVisibility(View.GONE);

                    img_camera.setVisibility(View.VISIBLE);
                    img_set.setVisibility(View.GONE);

                    Intent submit = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(submit);
                    overridePendingTransition(0, 0);
//                    if (bitmapImage != null) {
//
//                    } else {
//                        Alert("Please Capture Image");
//                    }
                }
            }
        });

        et_lr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

//        btn_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentIntegrator integrator = new IntentIntegrator(DeliveryActivity.this);
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//                integrator.setPrompt("Scan");
//                integrator.setCameraId(0);
//                integrator.setBeepEnabled(true);
//                integrator.setBarcodeImageEnabled(false);
//                integrator.initiateScan();
//            }
//        });

        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camaraImage();
            }
        });

        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapImage != null){
                    camaraImage1();
                }

            }
        });

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                branch_code = auto_branch.getText().toString().trim();
                lr_no = auto_lr.getText().toString().trim();
                if (branch_code.equals("") && lr_no.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all field", Toast.LENGTH_LONG).show();
                } else {
                    str_branchcode = branch_code.trim();
                    str_lrno = lr_no.trim();
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {
                        String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + branch_code + "&lrno=" + lr_no;
                        new LongOperation().execute(ip);
                    } else {
                        Alert("Network Not Available");
                        l_detail.setVisibility(View.VISIBLE);
                        l_camera.setVisibility(View.VISIBLE);
                        l_submit.setVisibility(View.VISIBLE);

                        et_lr.setVisibility(View.VISIBLE);
                        et_consinnename.setVisibility(View.VISIBLE);
                        et_totalpackage.setVisibility(View.VISIBLE);
                        et_totalweight.setVisibility(View.VISIBLE);
                        et_from_branch.setVisibility(View.VISIBLE);
                        et_to_branch.setVisibility(View.VISIBLE);
                        et_machineqty.setVisibility(View.VISIBLE);

                        et_lr.setText(branch_code + " - " + lr_no);
                    }
                }
            }
        });
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();

    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Image Pick From Camera
    private void camaraImage() {
        Intent intent_camera = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent_camera, 0);
    }

    //Image Pick From Camera
    private void camaraImage1() {
        Intent intent_camera = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent_camera, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                try {
                    path = Environment.getExternalStorageDirectory() + File.separator + "image.jpg";
                    mImageUri = Uri.parse(path);
                    if (mImageUri != null) {

//                        bitmapImage = BitmapFactory.decodeFile(String.valueOf(mImageUri));
                        img_camera.setVisibility(View.GONE);
                        img_set.setVisibility(View.VISIBLE);
//                        img_set.setImageBitmap(bitmapImage);
                        image_add.setVisibility(View.VISIBLE);

                        selection = "1";

                        Intent intent = new Intent(DeliveryActivity.this, CropActivity.class);
                        intent.putExtra("pictuerPath", path);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

//                        byte[] dataimage = null;
//
//                        try {
//                            InputStream in = new FileInputStream(path);
//                            dataimage = new byte[in.available()];
//                            in.read(dataimage);
//                            in.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        encodedImageData = Base64.encodeToString(dataimage, Base64.DEFAULT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == 1) {
                try {
                    path = Environment.getExternalStorageDirectory() + File.separator + "image.jpg";
                    mImageUri = Uri.parse(path);
                    if (mImageUri != null) {
                        img_set.setImageResource(0);
                        bitmapImage1 = BitmapFactory.decodeFile(String.valueOf(mImageUri));
                        img_camera.setVisibility(View.GONE);
                        img_set.setVisibility(View.VISIBLE);
                        image_add.setVisibility(View.GONE);

                        selection = "2";

                        Intent intent = new Intent(DeliveryActivity.this, CropActivity.class);
                        intent.putExtra("pictuerPath", path);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            if (requestCode == 2){
//                String image = data.getStringExtra("data");
//                img_set.setImageBitmap(CropActivity.croppedImage);
//            }
        }
    }

    @Override
    protected void onResume() {
        if (selection.equals("1")){
            bitmapImage = CropActivity.croppedImage;
            img_set.setImageBitmap(CropActivity.croppedImage);

            if (bitmapImage != null){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 45, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                encodedImageData = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }

        }else if (selection.equals("2")){
            bitmapImage1 = CropActivity.croppedImage;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new YourAsyncTask(DeliveryActivity.this).execute();
                }
            },500);
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public YourAsyncTask(DeliveryActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("please wait.");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        @Override
        protected Void doInBackground(Void... args) {
            // do background work here
            processedBitmap = ProcessingBitmap();
            if(processedBitmap != null){

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                processedBitmap.compress(Bitmap.CompressFormat.JPEG, 45, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImageData = Base64.encodeToString(byteArray, Base64.DEFAULT);

            }else{
                dialog.dismiss();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            img_set.setImageBitmap(processedBitmap);
        }
    }

    private Bitmap ProcessingBitmap(){
        Bitmap newBitmap = null;

        int w = bitmapImage.getWidth() + bitmapImage1.getWidth();
        int h;
        if(bitmapImage.getHeight() >= bitmapImage1.getHeight()){
            h = bitmapImage.getHeight();
        }else{
            h = bitmapImage1.getHeight();
        }

        Bitmap.Config config = bitmapImage.getConfig();
        if(config == null){
            config = Bitmap.Config.ARGB_8888;
        }

        newBitmap = Bitmap.createBitmap(w, h, config);
        Canvas newCanvas = new Canvas(newBitmap);

        newCanvas.drawBitmap(bitmapImage, 0, 0, null);
        newCanvas.drawBitmap(bitmapImage1, bitmapImage.getWidth(), 0, null);

        return newBitmap;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                mDrawerLayout.closeDrawers();
                select_type = "manual";
                break;
            case 1:
                mDrawerLayout.closeDrawers();
                final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                        .withActivity(DeliveryActivity.this)
                        .withEnableAutoFocus(true)
                        .withBleepEnabled(true)
                        .withBackfacingCamera()
                        .withText("Scanning...")
                        .withBarcodeFormats(Barcode.AZTEC | Barcode.EAN_13 | Barcode.CODE_93)
                        .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                            @Override
                            public void onResult(Barcode barcode) {
                                Barcode barcodeResult = barcode;
//                                Toast.makeText(getApplicationContext(),barcode.rawValue,Toast.LENGTH_LONG).show();
                                String input = barcode.rawValue.replaceAll(" ", "");
                                String currentString = input.trim();
                                String[] separated = currentString.split("-");
                                auto_branch.setText("");
                                auto_lr.setText("");
                                auto_branch.setText(separated[0]);
                                auto_lr.setText(separated[1]);
                                branch_code = separated[0].trim();
                                lr_no = separated[1].trim();
                                select_type = "barcode";

                                String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + branch_code + "&lrno=" + lr_no;
                                new LongOperation().execute(ip);
                            }
                        })
                        .build();
                materialBarcodeScanner.startScan();
                break;
            case 2:
                mDrawerLayout.closeDrawers();
                Intent i1 = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(i1);
                overridePendingTransition(0, 0);
                break;
            case 3:
                mDrawerLayout.closeDrawers();
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                break;
            case 4:
                SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.putString("userid", "");
                editor.clear();
                editor.apply();
                finish();

                Intent log = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(log);
                finish();

                break;

            default:
                break;
        }
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent e) {
//
//        if(e.getAction()==KeyEvent.ACTION_DOWN){
//            char pressedKey = (char) e.getUnicodeChar();
//            if (e.getUnicodeChar() == 0){
//
//            }
//            else {
//                barcode += pressedKey;
//            }
//        }
//        if (e.getAction()==KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//
////            barcode="";
//            String input = barcode.replaceAll(" ", "");
//            String currentString = input.trim();
//            String[] separated = currentString.split("-");
//            auto_branch.setText("");
//            auto_lr.setText("");
//            auto_branch.setText(separated[0]);
//            auto_lr.setText(separated[1]);
//            branch_code = separated[0].trim();
//            lr_no = separated[1].trim();
//
//            String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + branch_code + "&lrno=" + lr_no;
//            new LongOperation().execute(ip);
//        }
//
//        return super.dispatchKeyEvent(e);
//    }

    private void openRun() {
        if (Build.VERSION.SDK_INT >= 23) {
            insertDummyContactWrapper();
            // Marshmallow+
        } else {
            // Pre-Marshmallow
        }
    }

    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read Storage");
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write Storage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }
//        insertDummyContact();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                } else {
                    // Permission Denied
                    openRun();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("LongLogTag")
    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    private class LongOperation extends AsyncTask<String, String, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(DeliveryActivity.this);

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
            barcode = "";
            if (Error != null) {

            } else {
                usercategory(Content);
            }
        }

    }

    private void usercategory(String content) {

        if (!content.equals("")) {
            try {
                JSONObject jsobjectcategory = new JSONObject(content);

                String requestcode = jsobjectcategory.getString("statuscode");
                String message = jsobjectcategory.getString("message");
                String data = jsobjectcategory.getString("data");

                if (message.equals("success")) {

                    l_detail.setVisibility(View.VISIBLE);
                    l_camera.setVisibility(View.VISIBLE);
                    l_submit.setVisibility(View.VISIBLE);

                    JSONArray jsonarray = new JSONArray(data);

                    for (int j = 0; j < jsonarray.length(); j++) {
                        dataArray = jsonarray.getJSONArray(j);
                        JSONObject jsobcat = dataArray.getJSONObject(j);
                        Lrno lrno = new Lrno();
                        lrno.id = jsobcat.getString("Id");
                        lrno.lr_branchcode = jsobcat.getString("FromBranchCode");
                        lrno.lr_challanno = jsobcat.getString("ChallanNo");
                        lrno.lr_package = jsobcat.getString("Package");
                        lrno.lr_ConsignorName = jsobcat.getString("ConsignorName");
                        lrno.lr_ConsigneeName = jsobcat.getString("ConsigneeName");
                        lrno.lr_weight = jsobcat.getString("GrossWeight");
                        lrno.lr_challanentries = jsobcat.getString("ChallanEntries");
                        lrno.lr_tobranchname = jsobcat.getString("ToBranchName");
                        lrno.lr_station = jsobcat.getString("Station");
                        lrno.lr_branchname = jsobcat.getString("BranchName");
                        lrno.lr_machineqty = jsobcat.getString("MachineQty");

                        et_lr.setText(lrno.lr_branchcode + " - " + lrno.lr_challanno);
                        et_totalpackage.setText(lrno.lr_package);
                        et_consinnename.setText(lrno.lr_ConsigneeName);
                        et_totalweight.setText(lrno.lr_weight);
                        et_from_branch.setText(lrno.lr_branchname);
                        et_machineqty.setText(lrno.lr_machineqty);

                        if (lrno.lr_station.equals("")) {
                            et_to_branch.setText(lrno.lr_tobranchname);
                        } else {
                            et_to_branch.setText(lrno.lr_station);
                        }

                        str_branchcode = lrno.lr_branchcode;
                        str_lrno = lrno.lr_challanno;

                    }
                } else {
                    Alert("Data Not Found");
                    auto_branch.setText("");
                    auto_lr.setText("");

                    l_detail.setVisibility(View.GONE);
                    l_camera.setVisibility(View.GONE);
                    l_submit.setVisibility(View.GONE);

                    img_set.setVisibility(View.GONE);
                    img_camera.setVisibility(View.VISIBLE);
                    image_add.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void createDatabase() {
        db = openOrCreateDatabase("Transports", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Transports(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, branchcode VARCHAR, lrno INTEGER, " +
                "image BLOB NOT NULL, date VARCHAR, userid INTEGER);");
    }

    protected void insertIntoDB() {

        str_lr = et_lr.getText().toString().trim();
        str_consineename = et_consinnename.getText().toString().trim();
        str_totalpackage = et_totalpackage.getText().toString().trim();
        str_totalweight = et_totalweight.getText().toString().trim();

        String query = "INSERT INTO Transports (branchcode,lrno,image,date,userid) " +
                "VALUES('" + str_branchcode + "', '" + str_lrno + "', '" + path + "', '" + formattedDate + "', '" + userid + "');";
        db.execSQL(query);
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.sync:
                auto_branch.setText("");
                auto_lr.setText("");

                l_detail.setVisibility(View.GONE);
                l_camera.setVisibility(View.GONE);
                l_submit.setVisibility(View.GONE);

                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(i);
                break;

            case R.id.logout:
                auto_branch.setText("");
                auto_lr.setText("");

                l_detail.setVisibility(View.GONE);
                l_camera.setVisibility(View.GONE);
                l_submit.setVisibility(View.GONE);

                SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.putString("userid", "");
                editor.clear();
                editor.commit();
                finish();

//                SharedPreferences preferences1 =getSharedPreferences("Loginlocal",Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor1 = preferences1.edit();
//                editor1.putString("username", "");
//                editor1.putString("password", "");
//                editor1.putString("userid","");
//                editor1.clear();
//                editor1.commit();
//                finish();

                Intent log = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(log);
                finish();
                break;
        }
        return true;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private ProgressDialog Dialog = new ProgressDialog(DeliveryActivity.this);

        @Override
        protected String doInBackground(String... params) {
            response = "";

            try {

                String urls = AppConfig.URL + "api/ApprovedByAuditor.ashx";
                URL url = new URL(urls);

                String strEncode = encodedImageData.replace("\n","");

                JSONObject cred = new JSONObject();
                cred.put("BranchCode", str_branchcode);
                cred.put("LrNo", str_lrno);
                cred.put("ImgFileBase64", strEncode);
                cred.put("UserId", userid);

                String data = cred.toString();

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setFixedLengthStreamingMode(data.getBytes().length);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data);

                Log.d("data ", "Data to php = " + data);

                writer.flush();
                writer.close();
                out.close();
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                in.close();
                response = sb.toString();

                Log.d("Vicky", "Response from php = " + response);

                connection.disconnect();

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
                    auto_branch.setText("");
                    auto_lr.setText("");

                    l_detail.setVisibility(View.GONE);
                    l_camera.setVisibility(View.GONE);
                    l_submit.setVisibility(View.GONE);

                    img_camera.setVisibility(View.VISIBLE);
                    img_set.setVisibility(View.GONE);

                    if (select_button.equals("submit")){

                        if (select_type.equals("manual")){

                        }
                        else if (select_type.equals("barcode")){
                            final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                                    .withActivity(DeliveryActivity.this)
                                    .withEnableAutoFocus(true)
                                    .withBleepEnabled(true)
                                    .withBackfacingCamera()
                                    .withText("Scanning...")
                                    .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                        @Override
                                        public void onResult(Barcode barcode) {
                                            Barcode barcodeResult = barcode;
//                                Toast.makeText(getApplicationContext(),barcode.rawValue,Toast.LENGTH_LONG).show();
                                            String input = barcode.rawValue.replaceAll(" ", "");
                                            String currentString = input.trim();
                                            String[] separated = currentString.split("-");
                                            auto_branch.setText("");
                                            auto_lr.setText("");
                                            auto_branch.setText(separated[0]);
                                            auto_lr.setText(separated[1]);
                                            branch_code = separated[0].trim();
                                            lr_no = separated[1].trim();
                                            select_type = "barcode";

                                            String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + branch_code + "&lrno=" + lr_no;
                                            new LongOperation().execute(ip);
                                        }
                                    })
                                    .build();
                            materialBarcodeScanner.startScan();
                        }

                    }
                    else if (select_button.equals("submitnext")){
                        if (select_type.equals("manual")){

                        }
                        else if (select_type.equals("barcode")){
                            final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                                    .withActivity(DeliveryActivity.this)
                                    .withEnableAutoFocus(true)
                                    .withBleepEnabled(true)
                                    .withBackfacingCamera()
                                    .withText("Scanning...")
                                    .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                        @Override
                                        public void onResult(Barcode barcode) {
                                            Barcode barcodeResult = barcode;
//                                Toast.makeText(getApplicationContext(),barcode.rawValue,Toast.LENGTH_LONG).show();
                                            String input = barcode.rawValue.replaceAll(" ", "");
                                            String currentString = input.trim();
                                            String[] separated = currentString.split("-");
                                            auto_branch.setText("");
                                            auto_lr.setText("");
                                            auto_branch.setText(separated[0]);
                                            auto_lr.setText(separated[1]);
                                            branch_code = separated[0].trim();
                                            lr_no = separated[1].trim();
                                            select_type = "barcode";

                                            String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + branch_code + "&lrno=" + lr_no;
                                            new LongOperation().execute(ip);
                                        }
                                    })
                                    .build();
                            materialBarcodeScanner.startScan();
                        }

                    }

                } else if (requestcode.equals("401")) {
                    Alert("Already Delivered");

                    auto_branch.setText("");
                    auto_lr.setText("");

                    l_detail.setVisibility(View.GONE);
                    l_camera.setVisibility(View.GONE);
                    l_submit.setVisibility(View.GONE);

                    img_camera.setVisibility(View.VISIBLE);
                    img_set.setVisibility(View.GONE);

                    bitmapImage = null;
                    bitmapImage1 = null;


                } else {
                    Alert("Data Error");

                    auto_branch.setText("");
                    auto_lr.setText("");

                    l_detail.setVisibility(View.GONE);
                    l_camera.setVisibility(View.GONE);
                    l_submit.setVisibility(View.GONE);

                    img_camera.setVisibility(View.VISIBLE);
                    img_set.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void Alert(final String start) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(start)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things

                        if (start.equals("Already Delivered")){
                            if (select_type.equals("manual")){

                            }
                            else if (select_type.equals("barcode")){
                                final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                                        .withActivity(DeliveryActivity.this)
                                        .withEnableAutoFocus(true)
                                        .withBleepEnabled(true)
                                        .withBackfacingCamera()
                                        .withText("Scanning...")
                                        .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                            @Override
                                            public void onResult(Barcode barcode) {
                                                Barcode barcodeResult = barcode;
//                                Toast.makeText(getApplicationContext(),barcode.rawValue,Toast.LENGTH_LONG).show();
                                                String input = barcode.rawValue.replaceAll(" ", "");
                                                String currentString = input.trim();
                                                String[] separated = currentString.split("-");
                                                auto_branch.setText("");
                                                auto_lr.setText("");
                                                auto_branch.setText(separated[0]);
                                                auto_lr.setText(separated[1]);
                                                branch_code = separated[0].trim();
                                                lr_no = separated[1].trim();
                                                select_type = "barcode";

                                                String ip = AppConfig.URL + "api/LRInquiry.ashx?apiname=lrinquiry&code=" + branch_code + "&lrno=" + lr_no;
                                                new LongOperation().execute(ip);
                                            }
                                        })
                                        .build();
                                materialBarcodeScanner.startScan();
                            }
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
