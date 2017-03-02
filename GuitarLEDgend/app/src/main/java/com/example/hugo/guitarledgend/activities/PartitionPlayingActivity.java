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

        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getPath()+"/GuitarLEDgend/audio/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir,"audiorecordtest.wav");
        String mFileName=f.getPath();

        final MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(mFileName);
        try{
            recorder.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
        recorder.start();


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recorder.stop();
                recorder.release();
                Intent intent = new Intent(PartitionPlayingActivity.this, PostPlayingActivity.class);
                intent.putExtra("partition_id", partition_id);
                startActivity(intent);
                finish();            }
        },TEMPS);

        

    }

}
