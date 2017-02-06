package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.List;


public class ChoosePartitionInStatsActivity extends AppCompatActivity {

    private PartitionDAO database;
    private UserDAO database_user;

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_partition_in_stats);

        database = new PartitionDAO(ChoosePartitionInStatsActivity.this);
        database.open();



        mListView = (ListView) findViewById(android.R.id.list);

        List<Partition> values = database.getAllPartitions();

        PartitionsAdapter adapter = new PartitionsAdapter(ChoosePartitionInStatsActivity.this, values);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                database_user = new UserDAO(ChoosePartitionInStatsActivity.this);
                database_user.open();
                if( database_user.nombreStats(ProfilesActivity.getUser().getId(),(long) position+1) == 0){
                    Toast.makeText(ChoosePartitionInStatsActivity.this,"Pas de stats pour cette partition",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ChoosePartitionInStatsActivity.this, StatsActivity.class);
                    intent.putExtra("partition_id", (long) position+1);
                    startActivity(intent);
                }

            }
        });
    }

}
