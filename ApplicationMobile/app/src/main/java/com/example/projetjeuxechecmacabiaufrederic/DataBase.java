package com.example.projetjeuxechecmacabiaufrederic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase";

    private static final String TABLE_NAME = "mytable";
    private static final String PKEY = "pkey";
    private static final String COL1 = "col1";


    DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                PKEY + " INTEGER PRIMARY KEY," +
                COL1 + " TEXT);";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) { // Upgrade pas très fin
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    public void insertData(String s)
    {
        Log.i("APP"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL1, s);
        db.insertOrThrow(TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public String readData()
    {
        Log.i("APP", "Reading database...");
        String select = new String("SELECT * from " + TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("APP", "Number of entries: " + cursor.getCount());
        String result = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int i = cursor.getColumnIndex(COL1);
                Log.i("APP", "Reading: " + cursor.getString(i));
                result = cursor.getString(i);
            } while (cursor.moveToNext());
        }
        return result;
    }
}
