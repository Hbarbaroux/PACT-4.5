package com.example.hugo.guitarledgend;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        StatsSQLiteHelper ssqlh = new StatsSQLiteHelper(this,"MyStats",null,1);

        SQLiteDatabase db = ssqlh.getReadableDatabase();







        db.close();



    }
}
