package com.ogchiharu.plando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void goToPlan(View view){

        Intent intent = new Intent();
        intent.setClass(StartActivity.this, PlanActivity.class);
        startActivity(intent);
    }

    public void goToDo(View view){

        Intent intent =new Intent();
        intent.setClass(StartActivity.this, DoActivity.class);
        startActivity(intent);
    }
}
