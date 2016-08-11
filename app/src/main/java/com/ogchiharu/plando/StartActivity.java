package com.ogchiharu.plando;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    int year, month, day, date;
    long recordsAmount;
    String[] planTitles;

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    static final String intentPlanTitleId = "planTitleId";

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
        choosePlanDialog(PlanActivity.class);
    }

    public void goToDo(View view){
        choosePlanDialog(DoActivity.class);
    }

    public void choosePlanDialog(final Class goingClass){

        AlertDialog dialog;

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setTitle(getString(R.string.planChoosingDialogTitle));
        alertDialogBuilder.setItems(planTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    newPlanDialog(goingClass);
                    dialog.dismiss();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, goingClass);
                    intent.putExtra(intentPlanTitleId, which);
                    startActivity(intent);
                    //いろいろわたしてね
                }
            }
        });
        dialog = alertDialogBuilder.show();
    }

    public void newPlanDialog(final Class goingClass){

        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View alertDialogLayout = layoutInflater.inflate(R.layout.new_plan_layout, null);
        final EditText newPlanTitleEdit = (EditText)alertDialogLayout.findViewById(R.id.newPlanEditText);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setTitle(getString(R.string.planNewPlan_title));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitleString = newPlanTitleEdit.getText() + String.valueOf(date);
                newPlanTitleEdit.setText(newTitleString);
            }
        };
        alertDialogLayout.findViewById(R.id.useToDateButton).setOnClickListener(listener);
        alertDialogBuilder.setPositiveButton(getString(R.string.done_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, goingClass);
                intent.putExtra(intentPlanTitleId, (int) recordsAmount + 1);
                startActivity(intent);
            }
        });
        alertDialogBuilder.show();
    }

    public String searchPlans(int id){

        String result = "";

        Cursor cursor = null;

        try{
            cursor = database.query(MySQLiteOpenHelper.planTitlesTable, new String[]{MySQLiteOpenHelper.plan_title}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

            int indexPlanTitle = cursor.getColumnIndex(MySQLiteOpenHelper.plan_title);

            while(cursor.moveToNext()){
                result = cursor.getString(indexPlanTitle);
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }

        return result;
    }

    public void insert(String newTitle){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.plan_title, newTitle);
        contentValues.put(MySQLiteOpenHelper.plan_id, 1);
    }
}
