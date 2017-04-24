package com.example.hugo.guitarledgend.activities.stats;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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

public class StatsDetailFragment0 extends Fragment {
    private static final int DISPLAYED_STATS=10;


    private UserDAO database;
    private PartitionDAO database_partition;

    private ViewPager mViewPager = StatsDetailActivity.getmViewPager();

    private StatsDetailAdapter mFragmentPagerAdapter = StatsDetailActivity.getmFragmentPagerAdapter();

    public StatsDetailFragment0() {
    }

    public static StatsDetailFragment0 newInstance() {
        StatsDetailFragment0 fragment = new StatsDetailFragment0();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats_detail, container, false);



        //GRAPHE
        database = new UserDAO(getActivity());
        database.open();

        List<Stats > stats  = database.getAllStats(ProfilesActivity.getUser().getId(), ((StatsDetailActivity) getActivity()).getPartitionId());
        Stats s = stats.get(0);
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
        graph.getViewport().setMaxX(tab.size());
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.5);

        graph.addSeries(series);

        database_partition = new PartitionDAO(getActivity());
        database_partition.open();
        Partition p=database_partition.selectionner(((StatsDetailActivity) getActivity()).getPartitionId());

        String title1=p.getNom();
        String title2=s.getDate();
        TextView titleView1 = (TextView) rootView.findViewById(R.id.graph_title_statsdetailfragment);
        titleView1.setText(title1);
        titleView1.setTextSize(25);
        titleView1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        titleView1.setSingleLine(true);
        titleView1.setMarqueeRepeatLimit(5);
        titleView1.setSelected(true);
        TextView titleView2 = (TextView) rootView.findViewById(R.id.graph_title2_statsdetailfragment);
        titleView2.setText(title2);
        titleView2.setTextSize(25);


        //GRAPHE

        TextView score_view = (TextView) rootView.findViewById(R.id.score_statsFragment);
        score_view.setText(String.valueOf(s.getScore())+"%");

        return rootView;
    }
}
