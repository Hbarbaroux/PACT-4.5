package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugo.guitarledgend.R;

public class PartitionPlayingActivityTest extends AppCompatActivity {


    private long  partition_id=0;
    private int x1;
    private int x2;
    private int replay;
    private int vitesse; // vitesse normale : 100.
    private long statId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partition_playing_test);


        Intent intent =getIntent();
        partition_id=intent.getLongExtra("partition_id",1L);
        vitesse=intent.getIntExtra("vitesse",10);
        x1=intent.getIntExtra("X1",0);
        x2=intent.getIntExtra("X2",0);
        replay=intent.getIntExtra("replay",0);
        statId=intent.getLongExtra("statId",0);

        final float facteur = (float)vitesse/100;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(PartitionPlayingActivityTest.this, PostPlayingActivity.class);
                intent2.putExtra("partition_id", partition_id);
                intent2.putExtra("facteur", facteur);
                if (replay==1){
                    intent2.putExtra("X1", x1);
                    intent2.putExtra("X2", x2);
                    intent2.putExtra("statId",statId);
                    intent2.putExtra("replay",1);
                }
                startActivity(intent2);
                finish();

            }
        }, 2000);
    }


}
