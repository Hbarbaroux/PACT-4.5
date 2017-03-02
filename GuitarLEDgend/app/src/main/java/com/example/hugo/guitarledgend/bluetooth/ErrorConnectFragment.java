package com.example.hugo.guitarledgend.bluetooth;

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

public class ErrorConnectFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Impossible de se connecter à l'Arduino. Vérifiez qu'il est à portée, et allumé.")
                .setTitle("Erreur");


        // Create the AlertDialog object and return it
        return builder.create();
    }
}