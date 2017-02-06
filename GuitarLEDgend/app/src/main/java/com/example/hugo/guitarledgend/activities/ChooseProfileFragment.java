package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

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

        next_button = (Button) rootView.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                database = new UserDAO(getActivity());
                database.open();
                Profile profil = database.selectionnerProfile(position);
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
                database.supprimerProfil(position);
                database.close();

                mFragmentPagerAdapter.removeTabPage(position - 1);

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        return rootView;
    }
}

