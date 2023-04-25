package com.university.brailleflip.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;


public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private  final String TAG="Service side";
    public ConnectedMsgThread connectedThread;
    private Handler handler;
    public static final int SERVER_CONNECT = 6;
    public AcceptThread(BluetoothAdapter bluetoothAdapter, Handler handler) {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        this.handler=handler;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            UUID uuid = UUID.fromString("46400000-8cc0-11bd-b43e-10d46e4ef14d");
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("server", uuid);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        Log.e(TAG, "Activate");
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.

                Log.e(TAG, "get socker");

                Message message = handler.obtainMessage();
                message.what=SERVER_CONNECT;
                handler.sendMessage(message);
               manageMyConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket socket) {

        connectedThread = new ConnectedMsgThread(socket,handler);
        connectedThread.start();
        ThreadManager.getInstance().setServiceConnected(connectedThread);
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
        //    Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}
