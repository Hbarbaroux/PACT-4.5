package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.ArrayList;

public class ProfilesAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private final ArrayList<CharSequence> tabItems = new ArrayList<>();
    int mNumOfTabs;

    public ProfilesAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext=context;
        UserDAO database = new UserDAO(mContext);
        database.open();
        this.mNumOfTabs = database.nombreProfils();
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) return CreateProfileFragment.newInstance();

        else {
            UserDAO database = new UserDAO(mContext);
            database.open();
            return ChooseProfileFragment.newInstance(database.selectionnerProfile(position).getNom());
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }

    public void removeTabPage(int position) {
        if (!tabItems.isEmpty() && position<tabItems.size()) {
            tabItems.remove(position);
            notifyDataSetChanged();
        }
    }

    public void addTabPage(String title) {
        tabItems.add(title);
        mNumOfTabs++;
        notifyDataSetChanged();
    }
}