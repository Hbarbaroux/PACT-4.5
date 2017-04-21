package com.example.hugo.guitarledgend.activities;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.hugo.guitarledgend.MyApp;
import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.audio.WavRecorder;
import com.example.hugo.guitarledgend.audio.sheets.Tablature;
import com.example.hugo.guitarledgend.bluetooth.BluetoothModule;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiFile;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiFileException;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiNote;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiTrack;
import com.example.hugo.guitarledgend.audio.midisheetmusic.TimeSignature;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PartitionPlayingActivity extends Activity {

    private BluetoothModule myDevice;
    private ArrayList<MidiNote> mNotes;
    private TimeSignature mTimeSignature;
    private long  partition_id=0;
    private int vitesse; // vitesse normale : 10.
    private PartitionDAO database;
    private int x1;
    private int x2;
    private int replay;



    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                // TODO : action when connection established
            }
            else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                // TODO : action when connection lost
            }
            else if (action.equals(BluetoothModule.ACTION_BATTERY_LOW)) {
                // TODO : action when battery low
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_partition_playing);

        Intent intent =getIntent();
        partition_id=intent.getLongExtra("partition_id",1L);
        vitesse=intent.getIntExtra("vitesse",10);
        x1=intent.getIntExtra("X1",0);
        x2=intent.getIntExtra("X2",0);
        replay=intent.getIntExtra("replay",0);

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothModule.ACTION_BATTERY_LOW);
        registerReceiver(mReceiver, mFilter);


        // Initialize and connect the BluetoothModule if none exists
        myDevice = ((MyApp)getApplicationContext()).getDevice();


        // MIDI TEST

        verifyStoragePermissions(this);

        database = new PartitionDAO(PartitionPlayingActivity.this);
        database.open();

        String filename = database.selectionner(partition_id).getFichier();
        database.close();
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "GuitarLEDgend/midiFiles/" + filename);

        byte[] rawdata = checkFile(file);
        MidiFile myFile = new MidiFile(rawdata, filename);
        ArrayList<MidiTrack> list = myFile.getTracks();
        ArrayList<MidiNote> notes = list.get(0).getNotes();
        mNotes = notes;
        TimeSignature myTimeSignature = myFile.getTime();
        mTimeSignature = myTimeSignature;
        playNotes play = new playNotes();
        play.start();
    }

    private class playNotes implements Runnable {
        private ExecutorService executor = Executors.newSingleThreadExecutor();
        private Future<?> publisher = null;
        private ArrayList<MidiNote> noteArray;
        private TimeSignature myTimeSignature;
        private float facteur = vitesse/10;
        private final int mx1 = x1;
        private final int mx2 = x2;
        private final int mreplay = replay;

        @Override
        public void run() {
            noteArray = mNotes;
            myTimeSignature = mTimeSignature;

            File sdcard = Environment.getExternalStorageDirectory();
            File dir = new File(sdcard.getPath()+"/GuitarLEDgend/audio/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir,"audiorecordtest.wav");
            String mFileName=f.getPath();
            final WavRecorder wavRecorder = new WavRecorder(mFileName);

            wavRecorder.startRecording();

            for (int i = 1;i<noteArray.size();i++){

                double t1 = (double) noteArray.get(i-1).getStartTime()*myTimeSignature.getTempo()/(myTimeSignature.getQuarter()*1000*facteur);
                double t2 = (double) noteArray.get(i).getStartTime()*myTimeSignature.getTempo()/(myTimeSignature.getQuarter()*1000*facteur);
                try {
                    long delta = (long) (t2 - t1);
                    synchronized (this) {
                        this.wait(delta);
                    }
                    int[] stringAndFret = findStringAndFretFromNote(noteArray.get(i));
                    myDevice.send(stringAndFret[0]+1, stringAndFret[1]+1, 1); // doigt inutile pour l'instant

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            wavRecorder.stopRecording();
            Intent intent2 = new Intent(PartitionPlayingActivity.this, PostPlayingActivity.class);
            intent2.putExtra("partition_id", partition_id);
            intent2.putExtra("facteur", facteur);
            if (mreplay==1){
                intent2.putExtra("X1", mx1);
                intent2.putExtra("X2", mx2);
                intent2.putExtra("replay",1);
            }
            startActivity(intent2);
            finish();
        }

        public void start(){
            publisher = executor.submit(this);
        }

        public void pause() {
            publisher.cancel(true);
        }

        public void resume() {
            start();
        }

        public void stop() {
            executor.shutdownNow();
        }

        public boolean isStarted() {
            if (publisher == null) {
                return false;
            }
            return true;
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
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
}

