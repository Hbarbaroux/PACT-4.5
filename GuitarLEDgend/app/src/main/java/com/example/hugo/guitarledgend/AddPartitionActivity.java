package com.example.hugo.guitarledgend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddPartitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partition);

        Button ok = (Button) findViewById(R.id.ok_button_AddPartitionActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddPartitionActivity.this, PlayPartitionActivity.class);
                startActivity(intent);
            }
        });


    }
}
