package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.audio.WavRecorder;

import java.io.File;
import java.io.IOException;

/**
 * Created by jesusbm on 6/02/17.
 */

public class PartitionPlayingActivity extends AppCompatActivity {
    private final int TEMPS = 5000;

    private Handler mHandler = new Handler();

    private long  partition_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partition_playing);

        Intent intent =getIntent();
        partition_id=intent.getLongExtra("partition_id",1L);
        final int X1=intent.getIntExtra("X1",0);
        final int X2=intent.getIntExtra("X2",0);
        final int replay=intent.getIntExtra("replay",0);

        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getPath()+"/GuitarLEDgend/audio/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir,"audiorecordtest.wav");
        String mFileName=f.getPath();

        /* final MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(mFileName);
        try{
            recorder.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
        recorder.start(); */

        final WavRecorder wavRecorder = new WavRecorder(mFileName);
        wavRecorder.startRecording();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                /* recorder.stop();
                recorder.release(); */

                wavRecorder.stopRecording();
                Intent intent = new Intent(PartitionPlayingActivity.this, PostPlayingActivity.class);
                intent.putExtra("partition_id", partition_id);
                if (replay==1){
                    intent.putExtra("X1", X1);
                    intent.putExtra("X2", X2);
                    intent.putExtra("replay",1);

                }
                startActivity(intent);
                finish();            }
        },TEMPS);

        

    }

}
