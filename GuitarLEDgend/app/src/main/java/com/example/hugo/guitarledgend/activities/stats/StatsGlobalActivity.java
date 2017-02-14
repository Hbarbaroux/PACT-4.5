package com.example.hugo.guitarledgend.activities.stats;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;


public class StatsGlobalActivity extends AppCompatActivity {
    public static final int DISPLAYED_STATS=10;


    public static Activity sa;

    private UserDAO database_user;
    private PartitionDAO database_partition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_global);

        sa = this;

        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);


        database_partition = new PartitionDAO(StatsGlobalActivity.this);
        database_partition.open();

        database_user = new UserDAO(StatsGlobalActivity.this);
        database_user.open();

        Partition partition = database_partition.selectionner(partition_id);
        Profile profil = ProfilesActivity.getUser();



        Button chercher = (Button) findViewById(R.id.dernieres_stats);
        chercher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StatsGlobalActivity.this, StatsDetailActivity.class);
                intent.putExtra("partition_id", (long) partition_id);
                startActivity(intent);
            }
        });


        //GRAPHE

        List<Stats> values = database_user.getAllStats(profil.getId(),partition_id);




        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        DataPoint[] d= new DataPoint[DISPLAYED_STATS+1];
        for (int i=1;i<=Math.min(DISPLAYED_STATS,values.size());i++){
            d[i]=new DataPoint(i,values.get(i-1).getScore());
            series.appendData(d[i],true,500);
        }


        series.setThickness(35);
        series.setColor(Color.BLACK);

        series.setDrawBackground(true);
        series.setBackgroundColor(Color.LTGRAY);

        series.setDrawDataPoints(false);
        series.setDataPointsRadius(30);

        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(Math.min(DISPLAYED_STATS,values.size())+1);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.addSeries(series);


        graph.setTitle("DERNIERS SCORES : " + profil.getNom() + "/" + partition.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);



    }
}
