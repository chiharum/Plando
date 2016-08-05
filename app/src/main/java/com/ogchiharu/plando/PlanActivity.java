package com.ogchiharu.plando;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class PlanActivity extends AppCompatActivity {

    EditText taskTitleEditText, startTimeEdit, endTimeEdit;

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        database = mySQLiteOpenHelper.getWritableDatabase();

        taskTitleEditText = (EditText)findViewById(R.id.planTitleEditText);
        startTimeEdit = (EditText)findViewById(R.id.startTimeEdit);
        endTimeEdit = (EditText)findViewById(R.id.endTimeEdit);


    }

    public void searchPlanTitles(){

    }
}
