package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;

public class ChooseProfileFragment extends Fragment {
    TextView textView;
    Button next_button;
    static String name;

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

        next_button = (Button) rootView.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}

