package com.example.hugo.guitarledgend.activities.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jesusbm on 24/04/17.
 */

public class HighScoresAdapter extends ArrayAdapter<Stats> {

    private UserDAO database;

    public HighScoresAdapter(Context context, List<Stats> stats) {
        super(context, 0, stats);
        database= new UserDAO(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_high_scores,parent, false);
        }

        com.example.hugo.guitarledgend.activities.stats.HighScoresAdapter.StatViewHolder viewHolder = (com.example.hugo.guitarledgend.activities.stats.HighScoresAdapter.StatViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new com.example.hugo.guitarledgend.activities.stats.HighScoresAdapter.StatViewHolder();
            viewHolder.profile = (TextView) convertView.findViewById(R.id.profil);
            viewHolder.score = (TextView) convertView.findViewById(R.id.score);
            viewHolder.indice= (TextView) convertView.findViewById(R.id.indice);
            convertView.setTag(viewHolder);
        }

        Stats stat = getItem(position);

        database.open();
        Profile p= database.selectionnerProfile(stat.getProfil());

        viewHolder.indice.setText(String.valueOf(position+1));
        viewHolder.profile.setText(p.getNom());
        viewHolder.score.setText(String.valueOf(stat.getScore()));



        return convertView;
    }

    private class StatViewHolder{
        public TextView profile;
        public TextView score;
        public TextView indice;
    }
}