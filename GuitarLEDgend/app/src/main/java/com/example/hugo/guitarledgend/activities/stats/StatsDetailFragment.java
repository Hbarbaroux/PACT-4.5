package com.example.hugo.guitarledgend.activities.stats;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;
import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.List;

/**
 * Created by jesusbm on 6/02/17.
 */

public class StatsDetailFragment extends Fragment {
    private static final int DISPLAYED_STATS=10;


    private int position;
    private UserDAO database;
    private PartitionDAO database_partition;

    private ViewPager mViewPager = StatsDetailActivity.getmViewPager();

    private StatsDetailAdapter mFragmentPagerAdapter = StatsDetailActivity.getmFragmentPagerAdapter();

    public StatsDetailFragment() {
    }

    public static StatsDetailFragment newInstance() {
        StatsDetailFragment fragment = new StatsDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats_detail, container, false);



        position = mViewPager.getCurrentItem()+1;

        //GRAPHE
        database = new UserDAO(getActivity());
        database.open();

        List<Stats > stats  = database.getAllStats(ProfilesActivity.getUser().getId(), ((StatsDetailActivity) getActivity()).getPartitionId());
        Stats s = stats.get(position-1);
        List<Integer> tab=s.tabFromFile(getContext());

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

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(Math.min(DISPLAYED_STATS,tab.size()));
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.5);

        graph.getViewport().setScalable(true);

        graph.addSeries(series);

        database_partition = new PartitionDAO(getActivity());
        database_partition.open();
        Partition p=database_partition.selectionner(((StatsDetailActivity) getActivity()).getPartitionId());

        graph.setTitle(s.getDate() + " / " + p.getNom());
        graph.setTitleColor(Color.BLACK);
        graph.setTitleTextSize(100);

        //GRAPHE

        TextView score_view = (TextView) rootView.findViewById(R.id.score_statsFragment);
        score_view.setText(String.valueOf(s.getScore())+"%");

        return rootView;
    }
}
