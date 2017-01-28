package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugo.guitarledgend.R;

public class StartPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_play);


        Button choisirPartitionButton = (Button) findViewById(R.id.choisirPartition_button);
        choisirPartitionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartPlayActivity.this, ChoosePartitionActivity.class);
                startActivity(intent);
            }
        });

        Button ajouterPartitionButton = (Button) findViewById(R.id.ajouterPartition_button);
        ajouterPartitionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartPlayActivity.this, AddPartitionActivity.class);
                startActivity(intent);
            }
        });



    }
}