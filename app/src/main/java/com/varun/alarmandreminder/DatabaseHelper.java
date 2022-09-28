package com.varun.alarmandreminder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Productivity.db", null, 1);
    }
    public final String tableAlarm = "TABLEALARM";
    public final String tableRemind = "TABLEREMIND";
    public final String name = "NAME";
    public final String message = "MESSAGE";
    public final String alarmTime = "ALARMTIME";
    public final String reminderDate = "REMINDERDATE";
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAlarmTable = "CREATE TABLE "+ tableAlarm +" ( ID INTEGER PRIMARY KEY, "+name+ " TEXT, "+alarmTime+" TEXT )" ;
        String createReminderTable = "CREATE TABLE "+tableRemind+" ( ID INTEGER PRIMARY KEY, "+message+ " TEXT, "+reminderDate+" TEXT )" ;
        db.execSQL(createAlarmTable);
        db.execSQL(createReminderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop = "DROP TABLE IF EXISTS ";
        db.execSQL(drop+tableAlarm);
        onCreate(db);
    }
    public Boolean addAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues alarmvalue = new ContentValues();
        alarmvalue.put("name", alarm.getTitle());
        alarmvalue.put("alarmTime",alarm.getTime());
        long k = db.insert("tableAlarm",null,alarmvalue);
        Log.d("Atag",Long.toString(k));
        Log.d("title",alarm.getTitle());
        Log.d("when",alarm.getTime());
        db.close();
        return k != -1;
    }
    public Boolean addReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues remindervalue = new ContentValues();
        remindervalue.put("message", reminder.getTitle());
        remindervalue.put("reminderDate",reminder.getDate());
        long k = db.insert("tableRemind",null,remindervalue);
        Log.d("Rtag",Long.toString(k));
        Log.d("title",reminder.getTitle());
        Log.d("when",reminder.getDate());
        db.close();
        return k != -1;
    }
    public Cursor getAlarm(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+tableAlarm,null);
        return cursor;
    }
    public Cursor getReminder(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+tableRemind,null);
        return cursor;
    }
}