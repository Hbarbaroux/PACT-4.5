package com.example.hugo.guitarledgend.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.hugo.guitarledgend.databases.Partition;
import com.example.hugo.guitarledgend.databases.PartitionDAO;
import com.example.hugo.guitarledgend.R;

import java.util.List;

public class ChoosePartitionActivity extends ListActivity {

    private PartitionDAO database;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_partition);

        database = new PartitionDAO(ChoosePartitionActivity.this);
        database.open();

        mListView = (ListView) findViewById(android.R.id.list);

        List<Partition> values = database.getAllPartitions();

        PartitionsAdapter adapter = new PartitionsAdapter(ChoosePartitionActivity.this, values);
        mListView.setAdapter(adapter);

        Button ok = (Button) findViewById(R.id.ok_button_ChoosePartitionActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(ChoosePartitionActivity.this, ChooseSpeedActivity.class);
                startActivity(intent);
            }
        });



    }
}
