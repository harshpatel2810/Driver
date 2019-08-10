package com.classicphoto.rpmfordriver;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.classicphoto.rpmfordriver.Model.Report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SubmitRateActivity extends AppCompatActivity {

    ImageButton imgCalender;
    Button imgSubmit;
    EditText edt_staff, edt_telephone, edt_pest_cntrl, edt_electric, edt_diesel,
            edt_stationary, edt_date;
    private Calendar calendar;
    private int year, month, day;
    Toolbar toolbar;
    Report report;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_rate);

        imgCalender = (ImageButton) findViewById(R.id.imgCalender);
        imgSubmit = (Button) findViewById(R.id.imgSubmit);
        edt_staff = (EditText) findViewById(R.id.edt_staff);
        edt_telephone = (EditText) findViewById(R.id.edt_telephone);
        edt_pest_cntrl = (EditText) findViewById(R.id.edt_pest_cntrl);
        edt_electric = (EditText) findViewById(R.id.edt_electric);
        edt_diesel = (EditText) findViewById(R.id.edt_diesel);
        edt_stationary = (EditText) findViewById(R.id.edt_stationary);
        edt_date = (EditText) findViewById(R.id.edt_date);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);

        edt_date.setText(formattedDate);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        imgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                report = new Report();

                if (edt_date.getText().toString().length() == 0) {
                    edt_date.setError("Please Fill All Fields");
                } else if (edt_staff.getText().toString().length() == 0) {
                    edt_staff.setError("Please Fill All Fields");
                } else if (edt_telephone.getText().toString().length() == 0) {
                    edt_telephone.setError("Please Fill All Fields");
                } else if (edt_pest_cntrl.getText().toString().length() == 0) {
                    edt_pest_cntrl.setError("Please Fill All Fields");
                } else if (edt_electric.getText().toString().length() == 0) {
                    edt_electric.setError("Please Fill All Fields");
                } else if (edt_diesel.getText().toString().length() == 0) {
                    edt_diesel.setError("Please Fill All Fields");
                } else if (edt_stationary.getText().toString().length() == 0) {
                    edt_stationary.setError("Please Fill All Fields");
                } else {

                    report.setDate(edt_date.getText().toString().trim());
                    report.setStaff(edt_staff.getText().toString().trim());
                    report.setTelephone(edt_telephone.getText().toString().trim());
                    report.setPest_cntrl(edt_pest_cntrl.getText().toString().trim());
                    report.setElectric(edt_electric.getText().toString().trim());
                    report.setDiesel(edt_diesel.getText().toString().trim());
                    report.setStationary(edt_stationary.getText().toString().trim());
                    databaseHelper.insertReport(report);

                    Intent intent = new Intent(SubmitRateActivity.this, ViewReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        edt_date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
