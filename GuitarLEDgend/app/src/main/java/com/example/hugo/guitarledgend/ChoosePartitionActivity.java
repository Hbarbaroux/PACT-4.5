package com.example.hugo.guitarledgend;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChoosePartitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_partition);

        Button ok = (Button) findViewById(R.id.ok_button_ChoosePartitionActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //recuperer le fichier grace au id et le transmettre a la nouvelle activite

                Intent intent = new Intent(ChoosePartitionActivity.this, ChooseSpeedActivity.class);
                startActivity(intent);
            }
        });

        EditText editTextNom = (EditText) findViewById(R.id.nom_choosePartition);
        String nom= editTextNom.getText().toString();

        EditText editTextAuteur = (EditText) findViewById(R.id.auteur_choosePartition);
        String auteur= editTextAuteur.getText().toString();

        EditText editTextGenre = (EditText) findViewById(R.id.genre_choosePartition);
        String genre= editTextGenre.getText().toString();


        StatsSQLiteHelper ssqlh = new StatsSQLiteHelper(this,"MyMusic",null,1);
        SQLiteDatabase db = ssqlh.getReadableDatabase();

        String[] args = new String[] {genre,auteur,nom};
        //Cursor c = db.rawQuery(" SELECT id FROM Music WHERE genre =? , auteur =? , nom =? ", args);


    }
}
