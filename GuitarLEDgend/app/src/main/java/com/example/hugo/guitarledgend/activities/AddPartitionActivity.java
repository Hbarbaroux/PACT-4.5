package com.example.hugo.guitarledgend.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.R;

import java.io.File;
import java.util.List;

public class AddPartitionActivity extends AppCompatActivity {

    private PartitionDAO database;
    public static Activity apa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partition);

        apa = this;

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

                File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "GuitarLEDgend" + File.separator + fichier);

                if(TextUtils.isEmpty(fichier)) {
                    editTextFichier.setError("Veuillez rentrer un fichier");
                }
                else if(TextUtils.isEmpty(nom)) {
                    editTextNom.setError("Veuillez rentrer un nom");
                }
                else if(TextUtils.isEmpty(auteur)) {
                    editTextAuteur.setError("Veuillez rentrer un auteur");
                }
                else if(TextUtils.isEmpty(genre)) {
                    editTextGenre.setError("Veuillez rentrer un genre");
                }
                else if(!(file.exists())) {
                    editTextFichier.setError("Ce fichier n'existe pas. Assurez-vous d'avoir indiqué  la bonne extension");
                }


                else{
                    Partition p = new Partition(0, fichier, nom, auteur, genre);

                    database.ajouter(p);

                    List<Partition> values = database.getAllPartitions();

                    database.close();

                    Intent intent = new Intent(AddPartitionActivity.this, PlayPartitionActivity.class);
                    intent.putExtra("partition_id", (long) values.size());
                    startActivity(intent);
                    finish();
                }


            }
        });

    }

}
