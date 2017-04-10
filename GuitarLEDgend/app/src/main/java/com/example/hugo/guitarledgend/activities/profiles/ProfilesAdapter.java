package com.example.hugo.guitarledgend.activities.profiles;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class ProfilesAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    int mNumOfTabs;
    private UserDAO database;

    public ProfilesAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext=context;
        database = new UserDAO(mContext);
        database.open();
        this.mNumOfTabs = database.nombreProfils()+1;
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

            List<Profile> values = database.getAllProfiles();
            final long[] ids = new long[values.size()];
            for (int i=0;i<values.size();i++){
                ids[i]=values.get(i).getId();
            }
            long id=ids[position-1];
            return ChooseProfileFragment.newInstance(id);
        }
        database.close();
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}