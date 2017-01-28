package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hugo.guitarledgend.databases.Partition;
import com.example.hugo.guitarledgend.databases.PartitionDAO;
import com.example.hugo.guitarledgend.R;

public class AddPartitionActivity extends AppCompatActivity {

    private PartitionDAO database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partition);

        database = new PartitionDAO(AddPartitionActivity.this);
        database.open();

        final EditText editTextFichier = (EditText) findViewById(R.id.fichier_addPartition);

        final EditText editTextNom = (EditText) findViewById(R.id.nom_addPartition);

        final EditText editTextAuteur = (EditText) findViewById(R.id.auteur_addPartition);

        final EditText editTextGenre = (EditText) findViewById(R.id.genre_addPartition);


        Button ok = (Button) findViewById(R.id.ok_button_AddPartitionActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String fichier = editTextFichier.getText().toString();
                String nom = editTextNom.getText().toString();
                String auteur = editTextAuteur.getText().toString();
                String genre = editTextGenre.getText().toString();


                Partition p = new Partition(0, fichier, nom, auteur, genre);

                database.ajouter(p);
                database.close();
                Intent intent = new Intent(AddPartitionActivity.this, PlayPartitionActivity.class);
                startActivity(intent);
            }
        });

    }

}
