package com.example.hugo.guitarledgend.activities.profiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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

    long id2;
    TextView textView;
    Button next_button;
    Button delete_button;
    private UserDAO database;
    static long id;


    private ViewPager mViewPager = ProfilesActivity.getmViewPager();

    private ProfilesAdapter mFragmentPagerAdapter = ProfilesActivity.getmFragmentPagerAdapter();

    public ChooseProfileFragment() {
    }

    public static ChooseProfileFragment newInstance(long i) {
        ChooseProfileFragment fragment = new ChooseProfileFragment();
        id=i;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_choose, container, false);

        textView = (TextView) rootView.findViewById(R.id.nom_profile);
        database = new UserDAO(getActivity());
        database.open();
        String name=database.selectionnerProfile(id).getNom();
        textView.setText(name);

        id2=id;



        database = new UserDAO(getActivity());
        database.open();


        next_button = (Button) rootView.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                database = new UserDAO(getActivity());
                database.open();
                long i=id2;
                Profile profil = database.selectionnerProfile(i);
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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                long i=id2;
                                database.supprimerProfil(i);
                                database.supprimerStats(i);
                                database.close();



                                Intent intent = new Intent(getActivity(), ProfilesActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Êtes-vous sûr?").setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show();

            }
        });


        return rootView;
    }
}

