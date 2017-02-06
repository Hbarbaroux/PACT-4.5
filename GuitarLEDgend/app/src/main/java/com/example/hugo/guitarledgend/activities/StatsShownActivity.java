package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Stats;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.List;


public class StatsShownActivity extends AppCompatActivity{

    private UserDAO database_user;

    public static int nbStats ;

    private static StatsAdapter mFragmentPagerAdapter;
    private static ViewPager mViewPager;

    public static ViewPager getmViewPager() {
        return mViewPager;
    }

    public static StatsAdapter getmFragmentPagerAdapter() {
        return mFragmentPagerAdapter;
    }
    private long partition_id;
    public long getPartitionId(){
        return partition_id;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_shown);

        database_user = new UserDAO(StatsShownActivity.this);
        database_user.open();

        Intent intent =getIntent();
        partition_id=intent.getLongExtra("partition_id",1L);

        long profile_id = ProfilesActivity.getUser().getId();






        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mFragmentPagerAdapter = new StatsAdapter(getSupportFragmentManager(),this,partition_id);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager_stats);
        mViewPager.setAdapter(mFragmentPagerAdapter);

    }
}
