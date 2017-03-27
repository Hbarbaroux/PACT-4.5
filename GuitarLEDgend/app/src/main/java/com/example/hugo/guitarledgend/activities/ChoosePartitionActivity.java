package com.example.hugo.guitarledgend.activities;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hugo.guitarledgend.bluetooth.TestBluetoothActivity;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.R;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ChoosePartitionActivity extends AppCompatActivity {

    private PartitionDAO database;
    ListView mListView;
    private int position;

    public int getPosition() {
        return position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_partition);

        database = new PartitionDAO(ChoosePartitionActivity.this);
        database.open();

        mListView = (ListView) findViewById(android.R.id.list);

        List<Partition> values = database.getAllPartitions();
        final long[] ids = new long[values.size()];
        for (int i=0;i<values.size();i++){
            ids[i]=values.get(i).getId();
        }

        PartitionsAdapter adapter = new PartitionsAdapter(ChoosePartitionActivity.this, values);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                long partition_id=ids[position];

                String fichier = database.selectionner(partition_id).getFichier();
                File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "GuitarLEDgend" + File.separator + fichier);

                ChoosePartitionActivity.this.position = position;
                if (!(file.exists())) {
                    askingForDeletion();
                } else {
                    Intent intent = new Intent(ChoosePartitionActivity.this, TestBluetoothActivity.class);
                    intent.putExtra("partition_id", partition_id);
                    startActivity(intent);
                }
            }
        });
    }

    public void askingForDeletion() {
        DialogFragment newFragment = new DeleteActivityFragment();
        newFragment.show(getSupportFragmentManager(), "delete");
    }




}
