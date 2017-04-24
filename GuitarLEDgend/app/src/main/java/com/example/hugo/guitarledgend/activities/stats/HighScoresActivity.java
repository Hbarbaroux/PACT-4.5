package com.example.hugo.guitarledgend.activities.stats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    private UserDAO database;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);


        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);

        database=new UserDAO(this);
        database.open();

        mListView = (ListView) findViewById(android.R.id.list);

        List<Stats> values = database.getAllStats(partition_id);

        HighScoresAdapter adapter = new HighScoresAdapter(HighScoresActivity.this, values);
        mListView.setAdapter(adapter);




    }
}
