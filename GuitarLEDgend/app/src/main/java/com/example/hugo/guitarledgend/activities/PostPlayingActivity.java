package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostPlayingActivity extends AppCompatActivity {

    private final String receptionFile = "receptionFile.txt";
    private UserDAO database;
    private PartitionDAO database_partition;
    private long user_id = ProfilesActivity.getUser().getId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_playing);
        
        Intent intent = getIntent();
        final long partition_id = intent.getLongExtra("partition_id", 1L);

        Date now = new Date();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now);

        String dataFile = "User_" + user_id + "-Part_" + partition_id + "-Date_" + nowAsString + ".txt";

        fileCreation();
        moveFile(dataFile);

        int score = score(dataFile);

        Stats s = new Stats(0, nowAsString, dataFile, score, partition_id, user_id);

        database = new UserDAO(PostPlayingActivity.this);
        database.open();
        database.ajouter(s);

        //GRAPHE

        List<Integer> tab = s.tabFromFile(this);

        DataPoint[] d = new DataPoint[tab.size()];
        for (int i = 0; i < tab.size(); i++) {

            d[i] = new DataPoint(i, tab.get(i));
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

        database_partition = new PartitionDAO(PostPlayingActivity.this);
        database_partition.open();
        Partition p = database_partition.selectionner(partition_id);

        graph.setTitle(s.getDate() + " / " + p.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);

        TextView score_view = (TextView) findViewById(R.id.score);
        score_view.setText(String.valueOf(score)+"%");


    }

    //methode provisoire: cree un fichier faux des resultats aleatoirement dans le dossier reception
    public void fileCreation() {
        File sdcard = Environment.getExternalStorageDirectory();

        File dir = new File(sdcard.getPath()+"/GuitarLEDgend/statsData/reception/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        PrintWriter pw = null;
        try {
            File f=new File (dir,receptionFile);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            pw = new PrintWriter(f);

            for (int i=0;i<50;i++){
                double r = Math.random();
                if (r<0.5)
                    pw.println(0);
                else
                    pw.println(1);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(pw!=null){
                try{
                    pw.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void moveFile(String newFile) {
        //va chercher le file dans reception et le renomme et deplace en data.
        File sdcard = Environment.getExternalStorageDirectory();

        File fromDir = new File(sdcard.getPath()+"/GuitarLEDgend/statsData/reception/");
        if (!fromDir.exists()) {
            fromDir.mkdirs();
        }
        File toDir = new File(sdcard.getPath()+"/GuitarLEDgend/statsData/");
        if (!toDir.exists()) {
            toDir.mkdirs();
        }

        File from = new File(fromDir, receptionFile);
        File to = new File(toDir, newFile);

        BufferedReader bf = null;
        PrintWriter pw = null;

        if (from.exists()){
            try {

                to.createNewFile();

                bf = new BufferedReader(new FileReader(from));
                pw = new PrintWriter(to);

                String line = bf.readLine();
                while (line != null){
                    pw.println(line);
                    line = bf.readLine();
                }

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(bf!=null){
                    try{
                        bf.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if(pw!=null){
                    try{
                        pw.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public int score(String file) {
        File sdcard = Environment.getExternalStorageDirectory();

        File dir = new File(sdcard.getPath()+"/GuitarLEDgend/statsData/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File f =new File(dir,file);
        int score =0;
        int length=0;

        if (f.exists()){
            BufferedReader bf = null;

            try {

                bf = new BufferedReader(new FileReader(f));

                String line = bf.readLine();
                while (line != null){
                    if (line.equals("1")) {
                        score++;
                    }
                    length++;
                    line = bf.readLine();
                }
                if (length == 0) {
                    return -1;
                }
                return (int) 100*score/length;


            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(bf!=null){
                    try{
                        bf.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return -2;
        }
        return -3;


    }


}