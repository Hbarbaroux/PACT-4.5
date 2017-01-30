package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;


public class StatsActivity extends AppCompatActivity {

    public static final int DISPLAYED_STATS=10;

    private UserDAO database;
    private PartitionDAO database_partition;
    String partition_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        Button chercher = (Button) findViewById(R.id.dernieres_stats);
        chercher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, StatsShownActivity.class);
                startActivity(intent);
            }
        });


        Intent intent =getIntent();
        long partition_id=intent.getLongExtra("partition_id",1L);


        database_partition = new PartitionDAO(StatsActivity.this);
        database_partition.open();

        Partition partition = database_partition.selectionner(partition_id+1);

        //GRAPHE

        database = new UserDAO(StatsActivity.this);
        database.open();

        List<Stats> values = database.getStats(ProfilesActivity.getUser(),partition_id+1);

        DataPoint[] d1= new DataPoint[DISPLAYED_STATS];
        for (int i=0;i<Math.min(DISPLAYED_STATS,values.size());i++){
            d1[i]=new DataPoint(i,values.get(i).getScore());
        }

        DataPoint[] d= new DataPoint[DISPLAYED_STATS];
        for (int i=0;i<DISPLAYED_STATS;i++){
            d[i]=new DataPoint(i,i);
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

        graph.setTitle("DERNIERS SCORES : " + partition.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);





    }
}
