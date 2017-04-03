package com.example.hugo.guitarledgend.activities.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.MainActivity;
import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

import java.util.List;

public class ChooseProfileFragment extends Fragment {

    TextView textView;
    Button next_button;
    Button delete_button;
    static String name;
    private UserDAO database;
    private int position;

    private ViewPager mViewPager = ProfilesActivity.getmViewPager();

    private ProfilesAdapter mFragmentPagerAdapter = ProfilesActivity.getmFragmentPagerAdapter();

    public ChooseProfileFragment() {
    }

    public static ChooseProfileFragment newInstance(String nom) {
        ChooseProfileFragment fragment = new ChooseProfileFragment();
        name = nom;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_choose, container, false);

        textView = (TextView) rootView.findViewById(R.id.nom_profile);
        textView.setText(name);

        position = mViewPager.getCurrentItem() + 1;

        database = new UserDAO(getActivity());
        database.open();

        List<Profile> values = database.getAllProfiles();
        final long[] ids = new long[values.size()];
        for (int i=0;i<values.size();i++){
            ids[i]=values.get(i).getId();
        }

        next_button = (Button) rootView.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Profile profil = database.selectionnerProfile(ids[position-1]);
                ProfilesActivity.setUser(profil);
                database.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        delete_button = (Button) rootView.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                database = new UserDAO(getActivity());
                database.open();
                database.supprimerProfil(ids[position-1]);
                database.close();

                mFragmentPagerAdapter.removeTabPage(position - 1);

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        return rootView;
    }
}

