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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.bluetooth.BluetoothModule;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiFile;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiFileException;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiNote;
import com.example.hugo.guitarledgend.audio.midisheetmusic.MidiTrack;
import com.example.hugo.guitarledgend.audio.midisheetmusic.TimeSignature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestPlayingActivity extends Activity {

    private TextView mTextView;
    private TextView mTextViewState;
    private EditText mEditTextCorde;
    private EditText mEditTextFrette;
    private EditText mEditTextDoigt;
    private Button mButtonSearch;
    private Handler mHandler;
    private BluetoothModule myDevice;
    private ArrayList<MidiNote> mNotes;
    private TimeSignature mTimeSignature;


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

        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tvDevices);
        mTextViewState = (TextView) findViewById(R.id.textViewState);
        mEditTextCorde = (EditText) findViewById(R.id.editTextCorde);
        mEditTextFrette = (EditText) findViewById(R.id.editTextFrette);
        mEditTextDoigt = (EditText) findViewById(R.id.editTextDoigt);
        mButtonSearch = (Button) findViewById(R.id.button_id);

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothModule.ACTION_BATTERY_LOW);
        registerReceiver(mReceiver, mFilter);

        // Defines a Handler object that's attached to the UI thread
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                try {
                    byte[] shortenedMessage = Arrays.copyOf((byte[]) inputMessage.obj, inputMessage.arg1);
                    String value = new String(shortenedMessage, "UTF-8");
                    if (value.equals("1")) {
                        Intent intent = new Intent(BluetoothModule.ACTION_BATTERY_LOW);
                        sendBroadcast(intent);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            myDevice = new BluetoothModule(this, mHandler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        // MIDI TEST

        verifyStoragePermissions(this);

        String filename = "test.mid"; // Ask user?
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
        private float facteur;
        // TODO : initilaiser facteur

        @Override
        public void run() {
            noteArray = mNotes;
            myTimeSignature = mTimeSignature;

            for (int i = 1;i<noteArray.size();i++){

                double t1 = (double) noteArray.get(i-1).getStartTime()*myTimeSignature.getTempo()/(myTimeSignature.getQuarter()*1000*facteur);
                double t2 = (double) noteArray.get(i).getStartTime()*myTimeSignature.getTempo()/(myTimeSignature.getQuarter()*1000*facteur);
                try {
                    long delta = (long) (t2 - t1);
                    synchronized (this) {
                        this.wait(delta);
                    }
                    int[] stringAndFret = findStringAndFretFromNote(noteArray.get(i));
                    myDevice.send(stringAndFret[0], stringAndFret[1], 1); // doigt inutile pour l'instant

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    // convert a note to a string and fret
    public int[] findStringAndFretFromNote(MidiNote note) {
        int noteNumber = note.getNumber();
        int corde = (noteNumber-40)/5; // numerote a partir de 0
        if (corde > 5) { // Guitar limited to 5 strings
            corde = 5;
        }
        int frette = noteNumber-40-corde*5; // numerote a partir de 0
        if (corde == 5) { // Increment of 4 instead of 5 from the 4th to the 5th string
            frette ++;
        }

        return new int[] {corde, frette};
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


    public void onClick_Play(View v) {
        try {
            myDevice.send(Integer.parseInt(mEditTextCorde.getText().toString()), Integer.parseInt(mEditTextFrette.getText().toString()), Integer.parseInt(mEditTextDoigt.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

