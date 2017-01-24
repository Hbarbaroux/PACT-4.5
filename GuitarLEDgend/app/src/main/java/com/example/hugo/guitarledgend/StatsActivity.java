package com.example.hugo.guitarledgend;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        Button chercher = (Button) findViewById(R.id.chercher);
        chercher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, StatsShownActivity.class);
                startActivity(intent);
            }
        });


        EditText editTextJour = (EditText) findViewById(R.id.jour);
        String jour= editTextJour.getText().toString();

        EditText editTextMois = (EditText) findViewById(R.id.mois);
        String mois= editTextMois.getText().toString();

        EditText editTextAnnee = (EditText) findViewById(R.id.annee);
        String annee= editTextAnnee.getText().toString();


        StatsSQLiteHelper ssqlh = new StatsSQLiteHelper(this,"MyStats",null,1);
        SQLiteDatabase db = ssqlh.getReadableDatabase();

        String[] args = new String[] {jour,mois,annee};
        //Cursor c = db.rawQuery(" SELECT id FROM Stats WHERE jour =? , mois =? , annee =? ", args);


        db.close();



    }
}
