package com.university.brailleflip.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.university.brailleflip.bean.BlueToothDevicesInfo;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread{
    private static final String TAG = "Client side";
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter bluetoothAdapter;
    public ConnectedMsgThread connectedThread;
    private Handler handler;

    public static final int CONNECT_FINISH = 4;
    public static final int CONNECT_FAULT = 5;

    public ConnectThread(BlueToothDevicesInfo device, BluetoothAdapter bluetoothAdapter, Handler handler) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        this.handler=handler;
        this.bluetoothAdapter = bluetoothAdapter;
        BluetoothSocket tmp = null;
        mmDevice = device.getDevice();

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            UUID uuid = UUID.fromString("46400000-8cc0-11bd-b43e-10d46e4ef14d");
            tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        Log.e(TAG, "Client initiates the connection");
        // Cancel discovery because it otherwise slows down the connection.
        bluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
            Log.e(TAG, "Bluetooth link completed");
            Message message = handler.obtainMessage();
            message.what=CONNECT_FINISH;
            handler.sendMessage(message);
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            Log.e(TAG, "Bluetooth link failed",connectException);
            Message message = handler.obtainMessage();
            message.what=CONNECT_FAULT;
            handler.sendMessage(message);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        manageMyConnectedSocket(mmSocket);
    }

    private void manageMyConnectedSocket(BluetoothSocket socket) {
        Log.e(TAG, "Get the communication link");
        connectedThread = new ConnectedMsgThread(socket,handler);
        connectedThread.start();
        ThreadManager.getInstance().setClientConnected(connectedThread);
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        //    Log.e(TAG, "Could not close the client socket", e);
        }
    }
}
