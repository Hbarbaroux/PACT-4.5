package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;
import com.example.hugo.guitarledgend.activities.stats.ChoosePartitionInStatsActivity;
import com.example.hugo.guitarledgend.audio.comparaison.CompTable;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiFile;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiFileException;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiNote;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiTrack;
import com.example.hugo.guitarledgend.audio.midisheetmusic.TimeSignature;
import com.example.hugo.guitarledgend.audio.sheets.Tablature;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostPlayingActivity extends AppCompatActivity {

    private final String receptionFile = "receptionFile.txt";
    private UserDAO database;
    private PartitionDAO database_partition;
    private long user_id = ProfilesActivity.getUser().getId();

    private MediaPlayer mPlayer = null;
    private String mFileName = null;
    boolean mStartPlaying = true;

    private static final int DISPLAYED_STATS=20;
    private DataPoint[] d1= new DataPoint[2];
    private PointsGraphSeries<DataPoint> series2 = null;
    private int i;

    private int X1;
    private int X2;
    private long statId;

    private long partition_id;



    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_playing);

        Intent intent = getIntent();
        partition_id = intent.getLongExtra("partition_id", 1L);
        X1=intent.getIntExtra("X1",0);
        X2=intent.getIntExtra("X2",0);
        statId=intent.getLongExtra("statId",0);
        final int replay=intent.getIntExtra("replay",0);
        final float facteur=intent.getFloatExtra("facteur", 1);

        database_partition = new PartitionDAO(PostPlayingActivity.this);
        database_partition.open();

        String filename = database_partition.selectionner(partition_id).getFichier();
        database_partition.close();
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "GuitarLEDgend/midiFiles/" + filename);

        Tablature maTablature = createTablature(filename, facteur);

        CompTable compTable = new CompTable(null, 0);
        compTable.evaluate(Environment.getExternalStorageDirectory().getPath()+"/GuitarLEDgend/audio/audiorecordtest.wav",maTablature);

        File dir = new File(sdcard.getPath()+"/GuitarLEDgend/audio/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir,"audiorecordtest.wav");
        mFileName=f.getPath();


        final Button play = (Button) findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    play.setText("stop");
                } else {
                    play.setText("play");
                }
                mStartPlaying = !mStartPlaying;
            }
        });


        Button replayAreaButton = (Button) findViewById(R.id.replay_area_button);
        replayAreaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(d1[0].getX()==-1 || d1[1].getX()==-1){
                    Toast.makeText(PostPlayingActivity.this,"Placez deux points dans le graphe",Toast.LENGTH_SHORT).show();
                }


                else{
                    Intent intent = new Intent(PostPlayingActivity.this, ChooseSpeedActivity.class);
                    int x1=(int) d1[0].getX();
                    x1+=X1;
                    int x2=(int) d1[1].getX();
                    x2+=X1;
                    intent.putExtra("X1", x1);
                    intent.putExtra("X2", x2);
                    intent.putExtra("statId",statId);
                    intent.putExtra("replay",1);
                    intent.putExtra("partition_id",partition_id);
                    startActivity(intent);
                    finish();
                }

            }
        });

        Button replayButton = (Button) findViewById(R.id.replay_button);
        replayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PostPlayingActivity.this, ChooseSpeedActivity.class);
                intent.putExtra("partition_id",partition_id);
                startActivity(intent);
                finish();
            }
        });

        Button menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                ChoosePartitionActivity.apa.finish();
                AddChooseActivity.apa.finish();
            }
        });





        List<Integer> tab;
        int score;
        String title1;
        String title2;
        if (replay==0){

            Date now = new Date();
            String nowAsString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now);

            String dataFile = "User_" + user_id + "-Part_" + partition_id + "-Date_" + nowAsString + ".txt";

            fileCreation(compTable.getevalnotes());
            moveFile(dataFile);

            score = score(dataFile);

            Stats s = new Stats(0, nowAsString, dataFile, score, partition_id, user_id);

            tab = s.tabFromFile(this);

            database = new UserDAO(PostPlayingActivity.this);
            database.open();
            database.ajouter(s);

            //guardar el id de la estadistica y transmitirlo en el intent si replay para reconstruir el grafico
            List<Stats> values = database.getAllStats(user_id,partition_id);
            statId= values.get(values.size()-1).getId();

            database_partition = new PartitionDAO(PostPlayingActivity.this);
            database_partition.open();
            Partition p = database_partition.selectionner(partition_id);

            title1=p.getNom();
            title2=s.getDate();

        }

        else{

            database = new UserDAO(PostPlayingActivity.this);
            database.open();
            Stats s = database.selectionnerStats(statId);

            tab = s.tabFromFile(this);

            score= (int) s.getScore();

            database_partition = new PartitionDAO(PostPlayingActivity.this);
            database_partition.open();
            Partition p = database_partition.selectionner(partition_id);

            title1=p.getNom();
            title2=s.getDate();


        }

        //GRAPHE



        DataPoint[] d = new DataPoint[tab.size()];
        for (int i = 0; i < tab.size(); i++) {
            d[i] = new DataPoint(i, tab.get(i));
        }

        final GraphView graph = (GraphView) findViewById(R.id.graph_PostPlayingActivity);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(d);

        series.setColor(R.color.colorPrimaryDark);
        series.setDataWidth(1);
        series.setSpacing(0);
        series.setAnimated(true);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(Math.min(DISPLAYED_STATS,tab.size()));
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.5);

        graph.getViewport().setScalable(true);

        graph.addSeries(series);

        for (int i = 0; i < d1.length; i++) {
            d1[i] = new DataPoint(-1, 0);
        }
        series2 = new PointsGraphSeries<>(d1);
        graph.addSeries(series2);
        i=0;

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                d1[i%2]=(DataPoint) dataPoint;
                i++;

                DataPoint c;
                if (d1[0].getX()>d1[1].getX()){
                    i++;
                    c=d1[0];
                    d1[0]=d1[1];
                    d1[1]=c;
                }

                series2.resetData(d1);
            }
        });



        TextView titleView1 = (TextView) findViewById(R.id.graph_title_postplayingactivity);
        titleView1.setText(title1);
        titleView1.setTextSize(25);
        titleView1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        titleView1.setSingleLine(true);
        titleView1.setMarqueeRepeatLimit(5);
        titleView1.setSelected(true);

        TextView titleView2 = (TextView) findViewById(R.id.graph_title2_postplayingactivity);
        titleView2.setText(title2);
        titleView2.setTextSize(25);

        TextView score_view = (TextView) findViewById(R.id.score);
        score_view.setText(String.valueOf(score)+"%");




    }





    public void fileCreation(Boolean[] bolTab) {
        File phone = this.getFilesDir();

        File dir = new File(phone.getPath() + "/statsData/reception/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        PrintWriter pw = null;
        try {
            File f = new File(dir, receptionFile);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            pw = new PrintWriter(f);

            for (int i = 0; i < bolTab.length; i++) {
                if (bolTab[i]==false)
                    pw.println(0);
                else
                    pw.println(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //methode provisoire
    /*
    public void replayFileCreation(int X1, int X2){
        File phone = this.getFilesDir();

        File dir = new File(phone.getPath()+"/statsData/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        PrintWriter pw = null;
        try {
            File f=new File (dir,replayFile);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            pw = new PrintWriter(f);

            for (int i=X1;i<X2;i++){
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
    */

    //methode provisoire: cree un fichier faux des resultats aleatoirement dans le dossier reception
    public void fileCreation() {
        File phone = this.getFilesDir();

        File dir = new File(phone.getPath()+"/statsData/reception/");
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
        File phone = this.getFilesDir();

        File fromDir = new File(phone.getPath()+"/statsData/reception/");
        if (!fromDir.exists()) {
            fromDir.mkdirs();
        }
        File toDir = new File(phone.getPath()+"/statsData/");
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
        File phone = this.getFilesDir();

        File dir = new File(phone.getPath()+"/statsData/");
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

    // create an array of parsed data from raw midi file
    private byte[] checkFile(File file) {
        try {
            // FileInputStream in = this.openFileInput(name);
            // InputStream in = getAssets().open(name);
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[4096];
            int total = 0, len = 0;
            while (true) {
                len = in.read(data, 0, 4096);
                if (len > 0)
                    total += len;
                else
                    break;
            }
            in.close();
            data = new byte[total];
            // in = this.openFileInput(name);
            // in = getAssets().open(name);
            in = new FileInputStream(file);
            int offset = 0;
            while (offset < total) {
                len = in.read(data, offset, total - offset);
                if (len > 0)
                    offset += len;
            }
            in.close();
            return data;
        }
        catch (IOException e) {
            Toast toast = Toast.makeText(this, "CheckFile: " + e.toString(), Toast.LENGTH_LONG);
            toast.show();
        }
        catch (MidiFileException e) {
            Toast toast = Toast.makeText(this, "CheckFile midi: " + e.toString(), Toast.LENGTH_LONG);
            toast.show();
        }
        return new byte[0];
    }

    // convert a note to a string and fret
    public int[] findStringAndFretFromNote(MidiNote note) {
        int noteNumber = note.getNumber();
        int corde = (noteNumber-41)/5; // from 0
        if (corde > 5) { // guitar limited to 5 strings
            corde = 5;
        }
        int frette = noteNumber-41-corde*5; // from 0
        if (corde == 5) { // increment of 4 instead of 5 from the 4th to the 5th string
            frette ++;
        }

        return new int[] {corde, frette};
    }

    public Tablature createTablature(String filename, float vitesse) {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "GuitarLEDgend/midiFiles/" + filename);
        byte[] rawdata = checkFile(file);
        MidiFile myFile = new MidiFile(rawdata, filename);
        ArrayList<MidiTrack> trackList = myFile.getTracks();
        ArrayList<MidiNote> notes = trackList.get(0).getNotes();
        TimeSignature timeSignature = myFile.getTime();
        int tempo = timeSignature.getTempo();
        int quarternote = timeSignature.getQuarter();

        int[] cordes = new int[notes.size()];
        int[] doigts = new int[notes.size()];
        int[] frettes = new int[notes.size()];
        Float[] temps = new Float[notes.size()];

        for (int i=0;i<notes.size();i++) {
            temps[i] = (float) notes.get(i).getStartTime()*tempo/(quarternote*1000000*vitesse); // in seconds
            int[] tab = findStringAndFretFromNote(notes.get(i));
            cordes[i] = tab[0];
            frettes[i] = tab[1];
            doigts[i] = 0; // unused
        }

        return new Tablature(cordes, doigts, frettes, temps);
    }


}