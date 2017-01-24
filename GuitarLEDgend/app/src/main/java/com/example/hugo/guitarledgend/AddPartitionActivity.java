package com.example.hugo.guitarledgend;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPartitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partition);

        Button ok = (Button) findViewById(R.id.ok_button_AddPartitionActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //insert the new partition into the database

                Intent intent = new Intent(AddPartitionActivity.this, PlayPartitionActivity.class);
                startActivity(intent);
            }
        });

        EditText editTextFichier = (EditText) findViewById(R.id.fichier_addPartition);
        String fichier= editTextFichier.getText().toString();

        EditText editTextNom = (EditText) findViewById(R.id.nom_addPartition);
        String nom= editTextNom.getText().toString();

        EditText editTextAuteur = (EditText) findViewById(R.id.auteur_addPartition);
        String auteur= editTextAuteur.getText().toString();

        EditText editTextGenre = (EditText) findViewById(R.id.genre_addPartition);
        String genre= editTextGenre.getText().toString();


        StatsSQLiteHelper ssqlh = new StatsSQLiteHelper(this,"MyMusic",null,1);
        SQLiteDatabase db = ssqlh.getWritableDatabase();



    }
}
