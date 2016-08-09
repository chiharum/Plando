package com.ogchiharu.plando;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    int year, month, day, date;
    long recordsAmount;
    String[] planTitles;

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        database = mySQLiteOpenHelper.getWritableDatabase();

        //Getting planTitles here and putting them in String[]
        recordsAmount = DatabaseUtils.queryNumEntries(database, MySQLiteOpenHelper.planTitlesTable);
        planTitles = new String[(int)recordsAmount];
        planTitles[0] = getString(R.string.newPlan_text);
        for (int a = 1; a <= (int)recordsAmount; a++){

            planTitles[a] = searchPlans(a);
        }

        //Getting today's date and applying it to date
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        date = day + 100 * month + 10000 * year;
    }

    public void goToPlan(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setTitle(getString(R.string.planChoosingDialogTitle));
        alertDialogBuilder.setItems(planTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {

                    newPlanDialog();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, PlanActivity.class);
                    startActivity(intent);
                    //いろいろわたしてね
                }
            }
        });
    }

    public void goToDo(View view){

        Intent intent =new Intent();
        intent.setClass(StartActivity.this, DoActivity.class);
        startActivity(intent);
    }

    public void newPlanDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View alertDialogLayout = layoutInflater.inflate(R.layout.new_plan_layout, null);
        alertDialogBuilder.setTitle(getString(R.string.planNewPlan_title));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        alertDialogLayout.findViewById(R.id.useToDateButton).setOnClickListener(listener);
    }

    public String searchPlans(int id){

        String result = "";

        Cursor cursor = null;

        try{
            cursor = database.query(MySQLiteOpenHelper.planTitlesTable, new String[]{"planTitles"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

            int indexPlanTitles = cursor.getColumnIndex("planTitles");

            while(cursor.moveToNext()){
                result = cursor.getString(indexPlanTitles);
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }

        return result;
    }
}
