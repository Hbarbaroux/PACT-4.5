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
    private UserDAO database;

    public ProfilesAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext=context;
        database = new UserDAO(mContext);
        database.open();
        this.mNumOfTabs = database.nombreProfils()+1;
        database.close();
        initiateItems();
    }

    private void initiateItems () {
        database = new UserDAO(mContext);
        database.open();
        for (int i = 0 ; i < mNumOfTabs - 1; i++) {
            tabItems.add(database.selectionnerProfile(i+1).getNom());
        }
        database.close();
    }

    @Override
    public Fragment getItem(int position) {
        database = new UserDAO(mContext);
        database.open();
        if (position == 0) {
            return CreateProfileFragment.newInstance();
        }
        else if (position <= database.nombreProfils()){
            String nom = database.selectionnerProfile(position).getNom();
            return ChooseProfileFragment.newInstance(nom);
        }
        database.close();
        return null;
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
        if (!tabItems.isEmpty() && position<tabItems.size()+1) {
            tabItems.remove(position);
            mNumOfTabs--;
            notifyDataSetChanged();
        }
    }

    public void addTabPage(String title) {
        tabItems.add(title);
        mNumOfTabs++;
        notifyDataSetChanged();
    }
}