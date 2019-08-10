package com.classicphoto.rpmfordriver;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shine-2 on 6/15/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="Transports";

    public static final String KEY_ID="id";

    public static final String TABLE_NAME="Transports";

    public static final String KEY_Lrno="lrno";

    public static final String KEY_Brach="branchcode";

    public static final String KEY_image="image";

    public static final String KEY_date="date";

    public static final String KEY_userid="userid";

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

//        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_Lrno+" INTEGER)";
//        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Transports where id="+id+"", null );
        return res;
    }

//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }

}
