package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugo.guitarledgend.R;

public class ChooseSpeedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_speed);


        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);


        Button ok = (Button) findViewById(R.id.ok_button_ChooseSpeedActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChooseSpeedActivity.this, PartitionPlayingActivity.class);
                intent.putExtra("partition_id", partition_id);
                startActivity(intent);
                finish();
            }
        });


    }
}
