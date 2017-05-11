package com.example.hugo.guitarledgend.bluetooth;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;


import com.example.hugo.guitarledgend.MyApp;
import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.ChooseSpeedActivity;

import org.w3c.dom.Text;

public class TestBluetoothActivity extends AppCompatActivity {

    private BluetoothModule myDevice;

    private static final int REQ_CODE = 1;

    private long partition_id;

    private int[] resultatTests = new int[2];

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                // TODO : action when connection established
            }
            else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                // TODO : action when connection lost
            }
            else if (action.equals(BluetoothModule.ACTION_BATTERY_LOW)) {
                // TODO : action when battery low
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_bluetooth);

        Typeface century = Typeface.createFromAsset(getAssets(), "fonts/Century Gothic Bold.ttf");

        TextView title = (TextView) findViewById(R.id.info4);
        title.setTypeface(century);

        TextView textConnect = (TextView) findViewById(R.id.buttonConnect);
        textConnect.setTypeface(century);

        TextView textTest = (TextView) findViewById(R.id.buttonTest);
        textTest.setTypeface(century);

        TextView textVitesse = (TextView) findViewById(R.id.versvitesse);
        textVitesse.setTypeface(century);

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothModule.ACTION_BATTERY_LOW);
        registerReceiver(mReceiver, mFilter);

        Intent intent =getIntent();
        partition_id=intent.getLongExtra("partition_id",1L);


        // Initialize and connect the BluetoothModule if none exists
        myDevice = ((MyApp)getApplicationContext()).getDevice();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }


    public void onClick_Connect(View v) {

        // Irrelevant
        try {
            myDevice.connect();
            Toast.makeText(TestBluetoothActivity.this,"Connecté à l'Arduino",Toast.LENGTH_SHORT).show();
            resultatTests[0] = 1;
        } catch (Exception e) {
            DialogFragment newFragment = new ErrorConnectFragment();
            newFragment.show(getSupportFragmentManager(), null);
        }

    }

    public void onClick_Next(View v) {
        if (testContinue()) {
            Intent intent = new Intent(TestBluetoothActivity.this, ChooseSpeedActivity.class);
            intent.putExtra("partition_id", partition_id);
            startActivity(intent);
            finish();
        }
        else {
            DialogFragment newFragment = new ErrorContinueFragment();
            newFragment.show(getSupportFragmentManager(), null);
        }
    }


    public void onClick_Test(View v) {
        try {
            myDevice.send(0,0,1);
            DialogFragment newFragment = new AskingLedFragment();
            newFragment.show(getSupportFragmentManager(), null);
        } catch (Exception e) {
            DialogFragment newFragment = new ErrorLedFragment();
            newFragment.show(getSupportFragmentManager(), null);
        }
    }

    public void onUserSelectValue(int selectedValue) {
        resultatTests[1] = selectedValue;
        Log.d("Resultat 0: ", String.valueOf(resultatTests[0]));
        Log.d("Resultat 1: ", String.valueOf(resultatTests[1]));
    }

    /*
    public boolean testContinue () {
        if ((resultatTests[0] == 1) && (resultatTests[1] == 1)) {
            return true;
        }
        return false;
    }

    */

    public boolean testContinue () {
        return true;
    }




}