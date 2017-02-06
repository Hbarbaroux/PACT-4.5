package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;


public class StatsActivity extends AppCompatActivity {

    public static final int DISPLAYED_STATS=10;

    private String TAG = "DEBUG";

    private UserDAO database_user;
    private PartitionDAO database_partition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);


        database_partition = new PartitionDAO(StatsActivity.this);
        database_partition.open();

        database_user = new UserDAO(StatsActivity.this);
        database_user.open();

        Partition partition = database_partition.selectionner(partition_id);
        Profile profil = ProfilesActivity.getUser();



        Button chercher = (Button) findViewById(R.id.dernieres_stats);
        chercher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, StatsShownActivity.class);
                intent.putExtra("partition_id", (long) partition_id);
                startActivity(intent);
            }
        });


        //GRAPHE

        List<Stats> values = database_user.getAllStats(profil.getId(),partition_id);

        DataPoint[] d= new DataPoint[DISPLAYED_STATS];
        for (int i=0;i<Math.min(DISPLAYED_STATS,values.size());i++){
            d[i]=new DataPoint(i,values.get(i).getScore());
        }



        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(d);

        series.setThickness(35);
        series.setColor(Color.BLACK);

        series.setDrawBackground(true);
        series.setBackgroundColor(Color.LTGRAY);

        series.setDrawDataPoints(false);
        series.setDataPointsRadius(30);

        graph.addSeries(series);

        graph.setTitle("DERNIERS SCORES : " + profil.getNom() + "/" + partition.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);



    }
}
