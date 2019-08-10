package com.classicphoto.rpmfordriver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.classicphoto.rpmfordriver.Model.Report;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "reports_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Report.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Report.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertReport(Report note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        // values.put(Report.COLUMN_ID, note.getId());
        values.put(Report.COLUMN_DATE, note.getDate());
        values.put(Report.COLUMN_STAFF, note.getStaff());
        values.put(Report.COLUMN_TELEPHONE, note.getTelephone());
        values.put(Report.COLUMN_PEST_CONTROL, note.getPest_cntrl());
        values.put(Report.COLUMN_ELECTRIC, note.getElectric());
        values.put(Report.COLUMN_DIESEL, note.getDiesel());
        values.put(Report.COLUMN_STATIONARY, note.getStationary());

        // insert row
        long id = db.insert(Report.TABLE_NAME, null, values);

        // close db connection
        db.close();

        return id;
        // return newly inserted row id
    }

    public List<Report> getAllNotes() {
        List<Report> notes = new ArrayList<>();

        // String selectQuery = "SELECT * FROM " + Report.TABLE_NAME + " Limit 1";
        String selectQuery = "SELECT * FROM " + Report.TABLE_NAME + " ORDER BY " + Report.COLUMN_ID + " DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Report note = new Report();
                note.setId(cursor.getString(cursor.getColumnIndex(Report.COLUMN_ID)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Report.COLUMN_DATE)));
                note.setStaff(cursor.getString(cursor.getColumnIndex(Report.COLUMN_STAFF)));
                note.setTelephone(cursor.getString(cursor.getColumnIndex(Report.COLUMN_TELEPHONE)));
                note.setPest_cntrl(cursor.getString(cursor.getColumnIndex(Report.COLUMN_PEST_CONTROL)));
                note.setElectric(cursor.getString(cursor.getColumnIndex(Report.COLUMN_ELECTRIC)));
                note.setDiesel(cursor.getString(cursor.getColumnIndex(Report.COLUMN_DIESEL)));
                note.setStationary(cursor.getString(cursor.getColumnIndex(Report.COLUMN_STATIONARY)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<Report> getAllNotesDate(String dateFrom,String dateTo) {
        List<Report> notes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Report.TABLE_NAME + " WHERE " + Report.COLUMN_DATE +
                " BETWEEN '" + dateFrom + "' AND '" + dateTo + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Report note = new Report();
                note.setId(cursor.getString(cursor.getColumnIndex(Report.COLUMN_ID)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Report.COLUMN_DATE)));
                note.setStaff(cursor.getString(cursor.getColumnIndex(Report.COLUMN_STAFF)));
                note.setTelephone(cursor.getString(cursor.getColumnIndex(Report.COLUMN_TELEPHONE)));
                note.setPest_cntrl(cursor.getString(cursor.getColumnIndex(Report.COLUMN_PEST_CONTROL)));
                note.setElectric(cursor.getString(cursor.getColumnIndex(Report.COLUMN_ELECTRIC)));
                note.setDiesel(cursor.getString(cursor.getColumnIndex(Report.COLUMN_DIESEL)));
                note.setStationary(cursor.getString(cursor.getColumnIndex(Report.COLUMN_STATIONARY)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public boolean deleteRecord(String senderId, String receiverId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Report.TABLE_NAME, Report.COLUMN_ID +
                " = " + receiverId, null) > 0;
    }

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STAFF = "staff";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_PEST_CONTROL = "pest_cntrl";
    public static final String COLUMN_ELECTRIC = "electric";
    public static final String COLUMN_DIESEL = "diesel";
    public static final String COLUMN_STATIONARY = "stationary";

    public boolean updateRecord (Integer id, String date, String staff, String telephone, String pest_cntrl,
                                 String electric,String diesel,String stationary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Report.COLUMN_DATE, date);
        contentValues.put(Report.COLUMN_STAFF, staff);
        contentValues.put(Report.COLUMN_TELEPHONE, telephone);
        contentValues.put(Report.COLUMN_PEST_CONTROL, pest_cntrl);
        contentValues.put(Report.COLUMN_ELECTRIC, electric);
        contentValues.put(Report.COLUMN_DIESEL, diesel);
        contentValues.put(Report.COLUMN_STATIONARY, stationary);

        db.update(Report.TABLE_NAME, contentValues, Report.COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public List<Report> getAllNotesT() {
        List<Report> notes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Report.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Report note = new Report();
                note.setId(cursor.getString(cursor.getColumnIndex(Report.COLUMN_ID)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Report.COLUMN_DATE)));
                note.setStaff(cursor.getString(cursor.getColumnIndex(Report.COLUMN_STAFF)));
                note.setTelephone(cursor.getString(cursor.getColumnIndex(Report.COLUMN_TELEPHONE)));
                note.setPest_cntrl(cursor.getString(cursor.getColumnIndex(Report.COLUMN_PEST_CONTROL)));
                note.setElectric(cursor.getString(cursor.getColumnIndex(Report.COLUMN_ELECTRIC)));
                note.setDiesel(cursor.getString(cursor.getColumnIndex(Report.COLUMN_DIESEL)));
                note.setStationary(cursor.getString(cursor.getColumnIndex(Report.COLUMN_STATIONARY)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

}
