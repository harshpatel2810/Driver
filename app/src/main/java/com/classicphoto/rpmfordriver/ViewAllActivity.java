package com.classicphoto.rpmfordriver;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.classicphoto.rpmfordriver.Model.Report;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewAllActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button imgView;
    ImageButton imgCalenderFrom, imgCalenderTo;
    private Calendar calendar;
    private int year, month, day;
    EditText edt_date_To, edt_date_From;
    DatabaseHelper databaseHelper;
    public ArrayList<Report> reports;
    public ArrayList<Report> reportsT;
    ListView listReport;
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edt_date_To = (EditText) findViewById(R.id.edt_date_To);
        edt_date_From = (EditText) findViewById(R.id.edt_date_From);
        imgView = (Button) findViewById(R.id.imgView);
        imgCalenderTo = (ImageButton) findViewById(R.id.imgCalenderTo);
        imgCalenderFrom = (ImageButton) findViewById(R.id.imgCalenderFrom);
        listReport = (ListView) findViewById(R.id.listReport);

        reports = new ArrayList<>();
        reportsT = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getApplicationContext());

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        showDate2(year, month + 1, day);

        imgCalenderFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate2(v);
            }
        });

        imgCalenderTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reports.clear();
                reports.addAll(databaseHelper.getAllNotesDate(edt_date_From.getText().toString().trim(), edt_date_To.getText().toString().trim()));
                // Toast.makeText(getApplicationContext(), String.valueOf(reports.size()), Toast.LENGTH_SHORT).show();
                adapter = new MyListAdapter(ViewAllActivity.this, reports);
                listReport.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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

    @SuppressWarnings("deprecation")
    public void setDate2(View view) {
        showDialog(888);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        } else if (id == 888) {
            return new DatePickerDialog(this,
                    myDateListener2, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        edt_date_To.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate2(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate2(int year, int month, int day) {
        edt_date_From.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private class MyListAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<Report> reportsList;

        MyListAdapter(ViewAllActivity viewAllActivity, ArrayList<Report> reports) {
            super();
            this.activity = viewAllActivity;
            this.reportsList = reports;
        }

        @Override
        public int getCount() {
            return reportsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.mylist, null, true);

            Report report = reportsList.get(position);

            EditText edt_date = (EditText) rowView.findViewById(R.id.edt_date);
            EditText edt_staff = (EditText) rowView.findViewById(R.id.edt_staff);
            EditText edt_telephone = (EditText) rowView.findViewById(R.id.edt_telephone);
            EditText edt_pest_cntrl = (EditText) rowView.findViewById(R.id.edt_pest_cntrl);
            EditText edt_electric = (EditText) rowView.findViewById(R.id.edt_electric);
            EditText edt_diesel = (EditText) rowView.findViewById(R.id.edt_diesel);
            EditText edt_stationary = (EditText) rowView.findViewById(R.id.edt_stationary);


            edt_date.setText(report.getDate());
            edt_staff.setText(report.getStaff());
            edt_telephone.setText(report.getTelephone());
            edt_pest_cntrl.setText(report.getPest_cntrl());
            edt_electric.setText(report.getElectric());
            edt_diesel.setText(report.getDiesel());
            edt_stationary.setText(report.getStationary());


            return rowView;

        }


    }
}
