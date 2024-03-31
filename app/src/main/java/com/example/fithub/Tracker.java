package com.example.fithub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Tracker extends SQLiteOpenHelper {
    public Tracker(Context context) {
        super(context, "trackData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("trackData", "onCreate called");
        db.execSQL("CREATE TABLE IF NOT EXISTS TrackData(id INTEGER PRIMARY KEY AUTOINCREMENT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TrackData");
        onCreate(db);
    }

    public long addRecord(String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", status);

        long newRowId = db.insert("TrackData", null, values);
        db.close();

        return newRowId;
    }

    public int getLatestId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM TrackData", null);
        int latestId = 1;
        if (cursor.moveToFirst()) {
            latestId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return latestId;
    }

    public String getStatusForDay(int dayId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"status"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(dayId)};
        Cursor cursor = db.query("TrackData", projection, selection, selectionArgs, null, null, null);

        String status = null;
        if (cursor.moveToFirst()) {
            status = cursor.getString(0);
        }

        Log.d("Tracker", "Day " + dayId + ": " + status); // Add this line to print the status

        cursor.close();
        db.close();
        return status;
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private OnDataChangedListener listener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }
}

