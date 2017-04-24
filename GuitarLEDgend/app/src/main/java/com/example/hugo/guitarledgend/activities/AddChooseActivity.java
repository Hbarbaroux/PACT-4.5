package com.example.hugo.guitarledgend.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;

import org.w3c.dom.Text;

public class AddChooseActivity extends AppCompatActivity {

    public static Activity apa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choose);

        apa=this;

        TextView title = (TextView) findViewById(R.id.info3);
        Typeface century = Typeface.createFromAsset(getAssets(), "fonts/Century Gothic Bold.ttf");
        title.setTypeface(century);


        TextView textChoix = (TextView) findViewById(R.id.choisirPartition_butto);
        textChoix.setTypeface(century);
        Button choisirPartitionButton = (Button) findViewById(R.id.choisirPartition_butto);
        choisirPartitionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddChooseActivity.this, ChoosePartitionActivity.class);
                startActivity(intent);
            }
        });

        TextView textAdd = (TextView) findViewById(R.id.ajouterPartition_button);
        textAdd.setTypeface(century);
        Button ajouterPartitionButton = (Button) findViewById(R.id.ajouterPartition_button);
        ajouterPartitionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddChooseActivity.this, AddPartitionActivity.class);
                startActivity(intent);
            }
        });



    }
}