package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hugo.guitarledgend.R;

public class ChooseSpeedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_speed);


        Intent intent =getIntent();
        long partition_id=intent.getLongExtra("partition_id",1L);




    }
}
