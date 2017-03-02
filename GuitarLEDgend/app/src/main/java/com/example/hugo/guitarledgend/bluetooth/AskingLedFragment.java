package com.example.hugo.guitarledgend.bluetooth;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AskingLedFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Les LEDs s'allument-elles correctement ? Sinon, vérifiez leur connectivité à l'Arduino.")
                .setTitle("Allumage des LEDs")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TestBluetoothActivity callingActivity = (TestBluetoothActivity) getActivity();
                        callingActivity.onUserSelectValue(1);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TestBluetoothActivity callingActivity = (TestBluetoothActivity) getActivity();
                        callingActivity.onUserSelectValue(0);
                        dialog.dismiss();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }


}