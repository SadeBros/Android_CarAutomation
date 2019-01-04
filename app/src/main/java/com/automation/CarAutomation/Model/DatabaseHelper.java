package com.automation.CarAutomation.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//   Reference - YouTube: https://youtu.be/cp2rL3sAFmI

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME    = "DB_ALARMS.db";
    public static final String TABLE_NAME       = "TBL_ALARMS";
    public static final String COL_1            = "ID";
    public static final String COL_2            = "ALARM";
    public static final String COL_3            = "DAYTIME";
    public static final String COL_4            = "ENABLE";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, ALARM INTEGER, DAYTIME INTEGER, ENABLE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean insertData(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, alarm.id());
        contentValues.put(COL_2, alarm.alarm());
        contentValues.put(COL_3, alarm.dayTime());
        contentValues.put(COL_4, alarm.isEnable());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}

