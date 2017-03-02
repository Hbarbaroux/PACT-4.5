package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugo.guitarledgend.R;

public class ReplayAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_area);


        Intent intent =getIntent();
        int X1=intent.getIntExtra("X1",0);
        int X2=intent.getIntExtra("X2",0);



    }
}
