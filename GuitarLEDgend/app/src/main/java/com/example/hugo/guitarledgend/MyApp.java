package com.example.hugo.guitarledgend;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.hugo.guitarledgend.bluetooth.BluetoothModule;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MyApp extends Application {
    private BluetoothModule mDevice;

    public BluetoothModule getDevice() {
        if (mDevice == null) {
            // Defines a Handler object that's attached to the UI thread
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message inputMessage) {
                    try {
                        byte[] shortenedMessage = Arrays.copyOf((byte[]) inputMessage.obj, inputMessage.arg1);
                        String value = new String(shortenedMessage, "UTF-8");
                        if (value.equals("1")) {
                            Intent intent = new Intent(BluetoothModule.ACTION_BATTERY_LOW);
                            sendBroadcast(intent);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            };

            try {
                mDevice = new BluetoothModule(handler);
            }

            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                mDevice.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDevice;
    }
}
