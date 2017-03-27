package com.example.hugo.guitarledgend.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BluetoothModule implements BluetoothModuleInterface {
    public static final String ACTION_BATTERY_LOW = "android.pact.action.BATTERY_LOW";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private Handler mHandler;
    private ConnectedThread mConnectedThread;
    private Activity mActivity;

    public BluetoothModule(Activity activity, Handler handler) throws Exception {
        mHandler = handler;
        mActivity = activity;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            throw new Exception("The device does not have bluetooth capabilities");
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(enableBluetooth, 0);
        }

        mConnectedThread = new ConnectedThread();
    }

    private void closeConnection() {
        if (mSocket != null) {
            try {
                mSocket.isConnected();
                mSocket.close();
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        if (mInputStream != null) {
            try {
                mInputStream.close();
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    private void printBondedDevices() {
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {
                mTextView.append(bluetoothDevice.getName() + " : " + bluetoothDevice.getAddress() + "\n\n");
            }
        }
    }
    */

    private void connectToDevice(BluetoothDevice device) throws Exception {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            mSocket = device.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
            throw new Exception("Unable to connect to guitar module");
        }

        if (mSocket.isConnected()) {
            if (!mConnectedThread.isStarted()) {
                mConnectedThread.start();
            }
            else {
                mConnectedThread.resume();
            }
        }
    }

    @Nullable
    private BluetoothDevice getDevice() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("PACT45")) { // Needs to be changed according to module's actual name
                    return device;
                }
            }
        }
        return null;
    }

    public void search() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        else {
            mBluetoothAdapter.startDiscovery();
        }
    }

    public void connect() throws Exception {
        // mButtonSearch.setText("CONNECTING");
        mBluetoothAdapter.cancelDiscovery();
        BluetoothDevice myDevice = getDevice();
        if (myDevice == null) {
            throw new Exception("Guitar bluetooth module not bounded");
        }
        else {
            connectToDevice(myDevice);
        }
    }

    public void disconnect() {
        mConnectedThread.pause();
        closeConnection();
    }

    public void send(int corde, int frette, int doigt) throws Exception {
        /* if (corde < 0 || corde > 255 || frette < 0 || frette > 255 || doigt < 0 || doigt > 255) {
            throw new Exception("Require int from 0 to 255");
        }
        else { */
        ArrayList<Integer> frettes = digits(frette);
        sendByte(Integer.toString(corde) + Integer.toString(frettes.get(1)) + Integer.toString(frettes.get(0)) + Integer.toString(doigt));
        //}
    }

    private void sendByte(String msg) {
        if (mOutputStream != null) {
            try {
                mOutputStream.write(msg.getBytes());
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Integer> digits(int i) {
        ArrayList<Integer> digits = new ArrayList<Integer>();
        if (i == 0) {
            digits.add(0);
            digits.add(0);
            return digits;
        }
        int j = 0;
        while(i > 0) {
            digits.add(i % 10);
            i /= 10;
            j++;
        }
        if (j == 1) {
            digits.add(0);
        }
        return digits;
    }



    public void test() {
        try {
            send(0,0,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void batteryQuery() {
        try {
            send(0,0,2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBrightness (int brightness) { // brightness from 0 to 9
        try {
            send(0,1,brightness);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTheBatteryLow() {
        return false;
    }

    private class ConnectedThread implements Runnable {
        private ExecutorService executor = Executors.newSingleThreadExecutor();
        private Future<?> publisher = null;
        private byte[] mmBuffer; // mmBuffer store for the stream

        @Override
        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (!Thread.currentThread().interrupted()) {
                try {
                    // Read from the InputStream.
                    numBytes = mInputStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.
                    Message readMsg = mHandler.obtainMessage(0, numBytes, -1, mmBuffer);
                    readMsg.sendToTarget();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void start(){
            publisher = executor.submit(this);
        }

        public void pause() {
            publisher.cancel(true);
        }

        public void resume() {
            start();
        }

        public void stop() {
            executor.shutdownNow();
        }

        public boolean isStarted() {
            if (publisher == null) {
                return false;
            }
            return true;
        }
    }
}