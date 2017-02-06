package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugo.guitarledgend.R;

public class PostPlayingActivity extends AppCompatActivity {

    Intent intent =getIntent();
    final long partition_id=intent.getLongExtra("partition_id",1L);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_playing);


    }
}
