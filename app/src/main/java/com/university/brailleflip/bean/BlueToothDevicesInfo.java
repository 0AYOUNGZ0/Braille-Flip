package com.university.brailleflip.bean;

import android.bluetooth.BluetoothDevice;

public class BlueToothDevicesInfo {
    private String name;
    private String address;
    private boolean isPair;
    private BluetoothDevice device;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPair() {
        return isPair;
    }

    public void setPair(boolean pair) {
        isPair = pair;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }
}
