package com.example.medicine_20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseConn extends SQLiteOpenHelper {

    public DataBaseConn(Context context) {
        super(context, "Medicinedb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MDTable (MedicineName TEXT, date TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertvalues(String medname, String meddate, String medtime) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MedicineName", medname);
        contentValues.put("date", meddate);
        contentValues.put("time", medtime);
        long res = database.insert("MDTable", null, contentValues);
        return res != -1;
    }

    public Cursor FetchData(String date, String time) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("SELECT * FROM MDTable WHERE date = ? AND time = ?", new String[]{date, time});
    }
}