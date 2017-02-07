package com.example.hugo.guitarledgend.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.R;

import java.io.File;
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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                String fichier = database.selectionner(position + 1).getFichier();
                File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "GuitarLEDgend" + File.separator + fichier);

                if (!(file.exists())) {
                    Toast.makeText(ChoosePartitionActivity.this, "Le fichier correspondant n'existe plus", Toast.LENGTH_SHORT);
                }

                Intent intent = new Intent(ChoosePartitionActivity.this, ChooseSpeedActivity.class);
                intent.putExtra("partition_id", (long) position+1);
                startActivity(intent);
            }
        });



    }
}
