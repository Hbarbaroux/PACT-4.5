package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;

import java.util.List;


public class ChoosePartitionInStatsActivity extends AppCompatActivity {

    private PartitionDAO database;
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

                Intent intent = new Intent(ChoosePartitionInStatsActivity.this, StatsActivity.class);
                intent.putExtra("partition_id", (long) position);
                startActivity(intent);
            }
        });

        Button ok = (Button) findViewById(R.id.ok_button_ChoosePartitionInStatsActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(ChoosePartitionInStatsActivity.this, StatsActivity.class);
                startActivity(intent);

            }
        });




    }

}
