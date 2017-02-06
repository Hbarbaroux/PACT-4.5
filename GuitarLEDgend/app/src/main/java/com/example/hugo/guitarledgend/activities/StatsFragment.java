package com.example.hugo.guitarledgend.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
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
                getActivity().finish();
                StatsActivity.sa.finish();
            }
        });

        //GRAPHE
        database = new UserDAO(getActivity());
        database.open();

        List<Stats > stats  = database.getAllStats(ProfilesActivity.getUser().getId(), ((StatsShownActivity) getActivity()).getPartitionId());
        List<Integer> tab=stats.get(position-1).tabFromFile(getContext());


        DataPoint[] d= new DataPoint[tab.size()];
        for (int i=0;i<tab.size();i++){

            d[i]=new DataPoint(i, tab.get(i));
        }


        GraphView graph = (GraphView) rootView.findViewById(R.id.graph_stats_fragment);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(d);

        series.setColor(R.color.colorPrimaryDark);
        series.setDataWidth(1);
        series.setSpacing(0);
        series.setAnimated(true);



        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(tab.size());
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.addSeries(series);

        //graph.setTitle("DERNIERS SCORES : " + profil.getNom() + "/" + partition.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);

        //GRAPHE

        return rootView;
    }
}
