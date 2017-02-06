package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugo.guitarledgend.R;

/**
 * Created by jesusbm on 6/02/17.
 */

public class PartitionPlayingActivity extends AppCompatActivity {
    private final int TEMPS = 5000;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partition_playing);

        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PartitionPlayingActivity.this, PostPlayingActivity.class);
                intent.putExtra("partition_id", partition_id);
                startActivity(intent);
                finish();            }
        },TEMPS);





    }
}
