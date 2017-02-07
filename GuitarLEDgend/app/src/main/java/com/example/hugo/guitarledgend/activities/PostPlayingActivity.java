package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostPlayingActivity extends AppCompatActivity {

    private UserDAO database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_playing);

        fileCreation();
        String file = newFile();

        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);

        Date now = new Date();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);

        Stats s= new Stats(0,nowAsString,file,score(file),partition_id,ProfilesActivity.getUser().getId());

        database = new UserDAO(PostPlayingActivity.this);
        database.open();
        database.ajouter(s);

        //GRAPHE

        List<Integer> tab = s.tabFromFile(this);

        DataPoint[] d= new DataPoint[tab.size()];
        for (int i=0;i<tab.size();i++){

            d[i]=new DataPoint(i, tab.get(i));
        }


        GraphView graph = (GraphView) findViewById(R.id.graph_PostPlayingActivity);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(d);

        series.setColor(R.color.colorPrimaryDark);
        series.setDataWidth(1);
        series.setSpacing(0);
        series.setAnimated(true);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(tab.size());
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.addSeries(series);

        //graph.setTitle("DERNIERS SCORES : " + profil.getNom() + "/" + partition.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);


    }

    //methode provisoire: cree un fichier faux des resultats aleatoirement dans le dossier reception
    public void fileCreation(){

    }

    public String newFile(){
        //va chercher le file dans reception et le renomme et deplace en data. retourne le nom du fichier
        return null;
    }

    public int score(String file){
        return 0;
    }






}
