package com.example.hugo.guitarledgend.activities.stats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.PartitionsAdapter;
import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;
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
        final long[] ids = new long[values.size()];
        for (int i=0;i<values.size();i++){
            ids[i]=values.get(i).getId();
        }


        PartitionsAdapter adapter = new PartitionsAdapter(ChoosePartitionInStatsActivity.this, values);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                long partition_id=ids[position];

                database_user = new UserDAO(ChoosePartitionInStatsActivity.this);
                database_user.open();
                if( database_user.nombreStats(ProfilesActivity.getUser().getId(),partition_id) == 0){
                    Toast.makeText(ChoosePartitionInStatsActivity.this,"Pas de stats pour cette partition",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ChoosePartitionInStatsActivity.this, StatsGlobalActivity.class);
                    intent.putExtra("partition_id", partition_id);
                    startActivity(intent);
                }

            }
        });
    }

}
