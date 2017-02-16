package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;

import java.util.List;

public class PartitionsAdapter extends ArrayAdapter<Partition> {

    public PartitionsAdapter(Context context, List<Partition> partitions) {
        super(context, 0, partitions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_partitions_choose,parent, false);
        }

        PartitionViewHolder viewHolder = (PartitionViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PartitionViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.author = (TextView) convertView.findViewById(R.id.author);
            viewHolder.genre = (TextView) convertView.findViewById(R.id.genre);
            convertView.setTag(viewHolder);
        }

        Partition partition = getItem(position);

        viewHolder.name.setText(partition.getNom());
        viewHolder.author.setText(partition.getAuteur());
        viewHolder.genre.setText(partition.getGenre());

        return convertView;
    }

    private class PartitionViewHolder{
        public TextView name;
        public TextView author;
        public TextView genre;
    }
}