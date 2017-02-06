package com.example.hugo.guitarledgend.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

/**
 * Created by jesusbm on 6/02/17.
 */

public class StatsFragment extends Fragment {

    Button ok_button;
    private int position;
    private UserDAO database;

    private ViewPager mViewPager = StatsShownActivity.getmViewPager();

    private StatsAdapter mFragmentPagerAdapter = StatsShownActivity.getmFragmentPagerAdapter();

    public StatsFragment() {
    }

    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);



        position = mViewPager.getCurrentItem()+1;

        ok_button = (Button) rootView.findViewById(R.id.ok_button_StatsFragment);
        ok_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StatsActivity.class);
                startActivity(intent);
            }
        });

        //GRAPHE
        database = new UserDAO(getActivity());
        database.open();

        List<Stats > stats  = database.getAllStats(ProfilesActivity.getUser().getId(), ((StatsShownActivity) getActivity()).getPartitionId());
        List<Boolean> tab=stats.get(position).tabFromFile();

        //exemple a suprimer une fois on aura des vrais stats
        DataPoint[] d= new DataPoint[tab.size()];
        for (int i=0;i<tab.size();i++){
            d[i]=new DataPoint(i,i);
        }
        //fin

        GraphView graph = (GraphView) rootView.findViewById(R.id.graph_stats_fragment);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(d);

        series.setThickness(35);
        series.setColor(Color.BLACK);

        series.setDrawBackground(true);
        series.setBackgroundColor(Color.LTGRAY);

        series.setDrawDataPoints(false);
        series.setDataPointsRadius(30);

        graph.addSeries(series);

        //graph.setTitle("DERNIERS SCORES : " + profil.getNom() + "/" + partition.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);

        //GRAPHE

        return rootView;
    }
}
