package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class StatsAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    int mNumOfTabs;
    private UserDAO database;
    private String TAG = "DEBUG";


    public StatsAdapter(FragmentManager fm, Context context, long partition) {
        super(fm);
        this.mContext=context;
        database = new UserDAO(mContext);
        database.open();
        this.mNumOfTabs = database.nombreStats(ProfilesActivity.getUser().getId(), partition);
        database.close();

    }

    @Override
    public Fragment getItem(int position) {

        return StatsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
