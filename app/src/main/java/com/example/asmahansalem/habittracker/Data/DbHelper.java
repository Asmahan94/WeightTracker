package com.example.asmahansalem.habittracker.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asmahansalem.habittracker.HabitTracker;
import com.example.asmahansalem.habittracker.Data.Contract.HabitTrackerEntry;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asmahan Salem on 9/19/2017.
 */

/**
 * Here is a Java code source that helped me in implement this how to write this DbHelper with SQLiteOpenHelper
 * <p>
 * https://github.com/dkatic/HabitTracker/blob/master/app/src/main/java/com/example/david/habittracker/data/DatabaseHelper.java
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "habittracker.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitTrackerEntry.TABLE_NAME + " ("
                + HabitTrackerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitTrackerEntry.COLUMN_WEIGHT + " TEXT NOT NULL, "
                + HabitTrackerEntry.COLUMN_LOSS + " TEXT, "
                + HabitTrackerEntry.COLUMN_DATE + " LONG NOT NULL, "
                + HabitTrackerEntry.COLUMN_WATER + " TEXT);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertHabit(HabitTracker weight) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitTrackerEntry.COLUMN_WEIGHT, weight.getWeight());
        contentValues.put(HabitTrackerEntry.COLUMN_LOSS, weight.getLoss());
        contentValues.put(HabitTrackerEntry.COLUMN_DATE, weight.getDate());
        contentValues.put(HabitTrackerEntry.COLUMN_WATER, weight.getWater());

        return db.insert(HabitTrackerEntry.TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor getHabitsCursor() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                HabitTrackerEntry._ID,
                HabitTrackerEntry.COLUMN_WEIGHT,
                HabitTrackerEntry.COLUMN_LOSS,
                HabitTrackerEntry.COLUMN_DATE,
                HabitTrackerEntry.COLUMN_WATER};

        Cursor cursor = db.query(
                HabitTrackerEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public List<HabitTracker> readAllHabits() {
        List<HabitTracker> weight = new ArrayList<>();

        Cursor cursor = getHabitsCursor();

        int idColumnIndex = cursor.getColumnIndex(HabitTrackerEntry._ID);
        int weightColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_WEIGHT);
        int lossColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_LOSS);
        int dateColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_DATE);
        int waterColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_WATER);

        while (cursor.moveToNext()) {

            HabitTracker Weight = new HabitTracker(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(weightColumnIndex),
                    cursor.getString(lossColumnIndex),
                    cursor.getLong(dateColumnIndex),
                    cursor.getString(waterColumnIndex)
            );

            weight.add(Weight);
        }

        return weight;
    }
}
