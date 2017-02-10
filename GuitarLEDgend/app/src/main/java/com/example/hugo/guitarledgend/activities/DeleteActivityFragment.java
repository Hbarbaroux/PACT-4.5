package com.example.hugo.guitarledgend.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.databases.partitions.PartitionDAO;

public class DeleteActivityFragment extends DialogFragment {

    private PartitionDAO database;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Le fichier  correspondant est introuvable. Voulez-vous supprimer la partition ? (Attention, cette action supprimera aussi toutes les statistiques associées à cette partition.)")
                .setTitle("Erreur")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        database = new PartitionDAO(getActivity());
                        database.open();
                        database.supprimer(((ChoosePartitionActivity)getActivity()).getPosition() + 1);
                        database.close();

                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}