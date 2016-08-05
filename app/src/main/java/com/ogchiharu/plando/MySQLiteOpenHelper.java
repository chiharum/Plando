package com.ogchiharu.plando;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final String databaseName = "plando_data.db";
    static final String plansTable = "plans";
    static final String planTitlesTable = "planTitle";
    static final int databaseVersion = 1;

    public MySQLiteOpenHelper(Context context){

        super(context, databaseName, null, databaseVersion);
    }

    public void onCreate(SQLiteDatabase database){

        database.execSQL("create table " + plansTable + " (id integer primary key autoincrement not null, plan_title text not null, start_time integer not null, end_time integer not null, order integer not null, plan_id integer not null)");
        database.execSQL("create table " + planTitlesTable + " (id integer primary key autoincrement not null, plan_title integer not null, plan_id integer not null, date integer not null)");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){

    }
}
