package com.classicphoto.rpmfordriver.Model;

public class Report {

    public static final String TABLE_NAME = "reports";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STAFF = "staff";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_PEST_CONTROL = "pest_cntrl";
    public static final String COLUMN_ELECTRIC = "electric";
    public static final String COLUMN_DIESEL = "diesel";
    public static final String COLUMN_STATIONARY = "stationary";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_STAFF + " TEXT,"
                    + COLUMN_TELEPHONE + " TEXT,"
                    + COLUMN_PEST_CONTROL + " TEXT,"
                    + COLUMN_ELECTRIC + " TEXT,"
                    + COLUMN_DIESEL + " TEXT,"
                    + COLUMN_STATIONARY + " TEXT"
                    + ")";


    String id,date, staff, telephone, pest_cntrl, electric, diesel, stationary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPest_cntrl() {
        return pest_cntrl;
    }

    public void setPest_cntrl(String pest_cntrl) {
        this.pest_cntrl = pest_cntrl;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getDiesel() {
        return diesel;
    }

    public void setDiesel(String diesel) {
        this.diesel = diesel;
    }

    public String getStationary() {
        return stationary;
    }

    public void setStationary(String stationary) {
        this.stationary = stationary;
    }
}
