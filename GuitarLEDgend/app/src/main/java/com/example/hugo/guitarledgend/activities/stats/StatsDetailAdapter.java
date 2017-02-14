package com.example.hugo.guitarledgend.activities.stats;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;
import com.example.hugo.guitarledgend.databases.users.UserDAO;


public class StatsDetailAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    int mNumOfTabs;
    private UserDAO database;
    private String TAG = "DEBUG";


    public StatsDetailAdapter(FragmentManager fm, Context context, long partition) {
        super(fm);
        this.mContext=context;
        database = new UserDAO(mContext);
        database.open();
        this.mNumOfTabs = database.nombreStats(ProfilesActivity.getUser().getId(), partition);
        database.close();

    }

    @Override
    public Fragment getItem(int position) {

        return StatsDetailFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
