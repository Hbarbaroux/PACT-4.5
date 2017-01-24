package com.example.hugo.guitarledgend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChoosePartitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_partition);

        Button ok = (Button) findViewById(R.id.ok_button_ChoosePartitionActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePartitionActivity.this, ChooseSpeedActivity.class);
                startActivity(intent);
            }
        });


    }
}
