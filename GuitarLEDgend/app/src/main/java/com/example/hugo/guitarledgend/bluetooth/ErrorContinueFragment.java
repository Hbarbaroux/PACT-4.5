package com.example.hugo.guitarledgend.bluetooth;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ErrorContinueFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Vous ne pouvez pas continuer : soit l'Arduino n'est pas connecté, soit les LEDs ne s'affichent pas correctement.")
                .setTitle("Erreur");


        // Create the AlertDialog object and return it
        return builder.create();
    }


}