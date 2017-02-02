package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.List;


public class StatsShownActivity extends AppCompatActivity{

    private UserDAO database_user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_shown);

        database_user = new UserDAO(StatsShownActivity.this);
        database_user.open();

        Intent intent =getIntent();
        long partition_id=intent.getLongExtra("partition_id",1L);

        long profile_id = ProfilesActivity.getUser();



        //GRAPHE

        List<Stats> values = database_user.getStats(profile_id,partition_id);

    }
}
