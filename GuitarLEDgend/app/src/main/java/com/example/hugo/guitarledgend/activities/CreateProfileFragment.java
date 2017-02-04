package com.example.hugo.guitarledgend.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.UserDAO;

public class CreateProfileFragment extends Fragment {

    private ProfilesAdapter mFragmentPagerAdapter = ProfilesActivity.getmFragmentPagerAdapter();

    private UserDAO database;

    EditText nom_edit;
    RadioGroup sexe_check;
    Button create_button;
    String sexe = "homme";

    public CreateProfileFragment() {
    }


    public static CreateProfileFragment newInstance() {
        CreateProfileFragment fragment = new CreateProfileFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_create, container, false);

        database = new UserDAO(getActivity());
        database.open();

        nom_edit = (EditText) rootView.findViewById(R.id.nom);
        TextView nom_text_wiew = (TextView) rootView.findViewById(R.id.nom);
        Typeface insomnia = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Century Gothic.ttf");
        nom_text_wiew.setTypeface(insomnia);

        sexe_check = (RadioGroup) rootView.findViewById(R.id.sexe);

        create_button = (Button) rootView.findViewById(R.id.create_button);
        TextView create_text_view = (TextView) rootView.findViewById(R.id.create_button);
        Typeface century = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Century Gothic Bold.ttf");
        create_text_view.setTypeface(century);
        create_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String nom = nom_edit.getText().toString();

                if (sexe_check.getCheckedRadioButtonId() == R.id.femme) sexe = "femme";

                Profile profile = new Profile(0, nom, sexe);

                database.ajouter(profile);
                database.close();

                mFragmentPagerAdapter.addTabPage("profile");

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        return rootView;
    }

}
