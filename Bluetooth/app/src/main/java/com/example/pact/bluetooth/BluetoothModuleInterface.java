package com.example.pact.bluetooth;

public interface BluetoothModuleInterface {
    // Search for public bluetooth devices
    void search();

    // Connect to guitar module
    void connect() throws Exception;

    // Disconnect from guitar module
    void disconnect();

    // Send data
    void send(int corde, int frette, int doigt) throws Exception;

    // Test that LEDs are working
    void test();

    // Request battery state
    void batteryQuery();

    // Set brightness of LEDs with values from 0 to 255
    void setBrightness(int brightness);

    // Get battery state
    boolean isTheBatteryLow();
}