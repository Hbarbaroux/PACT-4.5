package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugo.guitarledgend.R;

public class PlayPartitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_partition);

        Button oui = (Button) findViewById(R.id.oui_button);
        oui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PlayPartitionActivity.this, ChooseSpeedActivity.class);
                startActivity(intent);
            }
        });

        Button non = (Button) findViewById(R.id.non_button);
        non.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PlayPartitionActivity.this, StartPlayActivity.class);
                startActivity(intent);
            }
        });





    }
}
