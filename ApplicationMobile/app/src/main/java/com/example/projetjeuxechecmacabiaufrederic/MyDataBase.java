package com.example.projetjeuxechecmacabiaufrederic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyDataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "textsDatabase";
    private static final String TABLE_NAME = "databaseTable";
    private static final String PKEY = "pkey";
    public static final String COL1 = "partyNumber";
    public static final String COL2 = "hostStatus";
    public static final String COL3 = "playerStatus";

    MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                PKEY + " INTEGER PRIMARY KEY," +
                COL1 + " INTEGER,"+
                COL2 + " TEXT,"+
                COL3 + " TEXT)";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertDataInTable(String tableName, String colName, String s)
    {
        Log.i("APP"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(colName, s);
        db.insertOrThrow(tableName, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void insertData(String colName, String s)
    {
        Log.i("APP"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(colName, s);
        db.insertOrThrow(TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void insertCol1(int n)
    {
        Log.i("APP"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL1, n);
        db.insertOrThrow(TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public String readData(String colName)
    {
        Log.i("APP", "Reading database...");
        String select = new String("SELECT * from " + TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("APP", "Number of entries: " + cursor.getCount());
        String result = "";
        /*if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int i = cursor.getColumnIndex(colName);
                Log.i("APP", "Reading: " + cursor.getString(i));
                result = cursor.getString(i);
            } while (cursor.moveToNext());
        }*/
        cursor.moveToLast();
        int i = cursor.getColumnIndex(colName);
        Log.i("APP", "Reading: " + cursor.getString(i));
        result = cursor.getString(i);
        return result;
    }

    public int readCol1()
    {
        Log.i("APP", "Reading database...");
        String select = new String("SELECT * from " + TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("APP", "Number of entries: " + cursor.getCount());
        int result = 0;
        cursor.moveToLast();
        int i = cursor.getColumnIndex(COL1);
        Log.i("APP", "Reading: " + cursor.getInt(i)+"");
        result = cursor.getInt(i);
        return result;
    }
}
