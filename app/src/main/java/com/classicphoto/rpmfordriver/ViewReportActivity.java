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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.classicphoto.rpmfordriver.Model.Report;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewReportActivity extends AppCompatActivity {

    String id, date, staff, telephone, pest_cntrl, electric, diesel, stationary;
    EditText edt_staff, edt_telephone, edt_pest_cntrl, edt_electric, edt_diesel,
            edt_stationary, edt_date;
    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    public ArrayList<Report> reports;
    Button imgEdit, imgDone,imgViewFromTo;
    ImageButton imgCalender;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        reports = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        reports.addAll(databaseHelper.getAllNotes());

        edt_staff = (EditText) findViewById(R.id.edt_staff);
        edt_telephone = (EditText) findViewById(R.id.edt_telephone);
        edt_pest_cntrl = (EditText) findViewById(R.id.edt_pest_cntrl);
        edt_electric = (EditText) findViewById(R.id.edt_electric);
        edt_diesel = (EditText) findViewById(R.id.edt_diesel);
        edt_stationary = (EditText) findViewById(R.id.edt_stationary);
        edt_date = (EditText) findViewById(R.id.edt_date);
        imgEdit = (Button) findViewById(R.id.imgEdit);
        imgDone = (Button) findViewById(R.id.imgDone);
        imgCalender = (ImageButton) findViewById(R.id.imgCalender);
        imgViewFromTo = (Button) findViewById(R.id.imgViewFromTo);

        for (int i = 0; i < reports.size(); i++) {
            edt_staff.setText(reports.get(i).getStaff());
            edt_telephone.setText(reports.get(i).getTelephone());
            edt_pest_cntrl.setText(reports.get(i).getPest_cntrl());
            edt_electric.setText(reports.get(i).getElectric());
            edt_diesel.setText(reports.get(i).getDiesel());
            edt_stationary.setText(reports.get(i).getStationary());
            edt_date.setText(reports.get(i).getDate());

            id = reports.get(i).getId();
            date = reports.get(i).getDate();
            staff = reports.get(i).getStaff();
            telephone = reports.get(i).getTelephone();
            pest_cntrl = reports.get(i).getPest_cntrl();
            electric = reports.get(i).getElectric();
            diesel = reports.get(i).getDiesel();
            stationary = reports.get(i).getStationary();
        }

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDone.setVisibility(View.VISIBLE);
                imgCalender.setVisibility(View.VISIBLE);
                edt_staff.setClickable(true);
                edt_telephone.setClickable(true);
                edt_pest_cntrl.setClickable(true);
                edt_electric.setClickable(true);
                edt_diesel.setClickable(true);
                edt_stationary.setClickable(true);

                edt_staff.setFocusable(true);
                edt_telephone.setFocusable(true);
                edt_pest_cntrl.setFocusable(true);
                edt_electric.setFocusable(true);
                edt_diesel.setFocusable(true);
                edt_stationary.setFocusable(true);

                imgEdit.setVisibility(View.GONE);
            }
        });

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

        imgViewFromTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReportActivity.this, ViewAllActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_staff.setClickable(false);
                edt_telephone.setClickable(false);
                edt_pest_cntrl.setClickable(false);
                edt_electric.setClickable(false);
                edt_diesel.setClickable(false);
                edt_stationary.setClickable(false);

                edt_staff.setFocusable(false);
                edt_telephone.setFocusable(false);
                edt_pest_cntrl.setFocusable(false);
                edt_electric.setFocusable(false);
                edt_diesel.setFocusable(false);
                edt_stationary.setFocusable(false);

                imgDone.setVisibility(View.GONE);
                imgEdit.setVisibility(View.VISIBLE);
                imgCalender.setVisibility(View.GONE);

                databaseHelper.updateRecord(Integer.valueOf(id), edt_date.getText().toString().trim()
                        , edt_staff.getText().toString().trim(), edt_telephone.getText().toString().trim()
                        , edt_pest_cntrl.getText().toString().trim(),
                        edt_electric.getText().toString().trim(),
                        edt_diesel.getText().toString().trim(),
                        edt_stationary.getText().toString().trim());

                reports.clear();
                reports.addAll(databaseHelper.getAllNotes());

                for (int i = 0; i < reports.size(); i++) {
                    edt_staff.setText(reports.get(i).getStaff());
                    edt_telephone.setText(reports.get(i).getTelephone());
                    edt_pest_cntrl.setText(reports.get(i).getPest_cntrl());
                    edt_electric.setText(reports.get(i).getElectric());
                    edt_diesel.setText(reports.get(i).getDiesel());
                    edt_stationary.setText(reports.get(i).getStationary());
                    edt_date.setText(reports.get(i).getDate());

                    id = reports.get(i).getId();
                    date = reports.get(i).getDate();
                    staff = reports.get(i).getStaff();
                    telephone = reports.get(i).getTelephone();
                    pest_cntrl = reports.get(i).getPest_cntrl();
                    electric = reports.get(i).getElectric();
                    diesel = reports.get(i).getDiesel();
                    stationary = reports.get(i).getStationary();
                }
            }
        });

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

}
