package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;


public class StatsActivity extends AppCompatActivity {

    public static final int DISPLAYED_STATS=10;

    private UserDAO database;


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




        //GRAPHE

        database = new UserDAO(StatsActivity.this);
        database.open();

        List<Stats> values = database.getStats(0,0);

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

        graph.setTitle("Derniers scores");
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);





    }
}
