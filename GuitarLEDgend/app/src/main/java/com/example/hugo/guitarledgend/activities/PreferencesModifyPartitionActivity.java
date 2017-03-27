package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;

public class PreferencesModifyPartitionActivity extends AppCompatActivity {

    private PartitionDAO database;
    private EditText nom;
    private EditText auteur;
    private EditText genre;
    private long partition_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_modify_partition);

        database = new PartitionDAO(PreferencesModifyPartitionActivity.this);
        database.open();

        Intent i =getIntent();
        partition_id=i.getLongExtra("partition_id",1L);
        Partition p = database.selectionner(partition_id);
        nom = (EditText) findViewById(R.id.editText);
        auteur = (EditText) findViewById(R.id.editText2);
        genre = (EditText) findViewById(R.id.editText3);

        nom.setText(p.getNom());
        auteur.setText(p.getAuteur());
        genre.setText(p.getGenre());


    }

    @Override
    public void onBackPressed() {
        database = new PartitionDAO(PreferencesModifyPartitionActivity.this);
        database.open();
        database.modifier(partition_id,nom.getText().toString(),auteur.getText().toString(),genre.getText().toString());
        Intent intent = new Intent(PreferencesModifyPartitionActivity.this,SettingsActivity.class);
        startActivity(intent);

        finish();
    }
}
