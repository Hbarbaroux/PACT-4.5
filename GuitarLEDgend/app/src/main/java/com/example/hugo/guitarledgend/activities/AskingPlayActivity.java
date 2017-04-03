package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.bluetooth.TestBluetoothActivity;

public class AskingPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_play);

        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);

        Button oui = (Button) findViewById(R.id.oui_button);
        oui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AskingPlayActivity.this, TestBluetoothActivity.class);
                intent.putExtra("partition_id", partition_id);
                startActivity(intent);
                finish();
            }
        });

        Button non = (Button) findViewById(R.id.non_button);
        non.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });





    }
}
