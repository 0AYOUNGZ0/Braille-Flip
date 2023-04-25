package com.university.brailleflip;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.university.brailleflip.adapter.MyBlueToothAdapter;
import com.university.brailleflip.bean.BlueToothDevicesInfo;
import com.university.brailleflip.thread.AcceptThread;
import com.university.brailleflip.thread.ConnectThread;
import com.university.brailleflip.thread.ConnectedMsgThread;
import com.university.brailleflip.thread.ThreadManager;
import com.university.brailleflip.utils.ProgressDialogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

        private static final int REQUEST_ENABLE_BT = 100;
        private BluetoothAdapter defaultAdapter;
        private RecyclerView mRecyclerView;
        private MyBlueToothAdapter myBlueToothAdapter;
        private ConnectThread connectThread;
        private MainHandler mainHandler;
        private TextView msg;
        private AcceptThread acceptThread;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mainHandler = new MainHandler(this);

            initView();

            requestPermission();


        }

        /*Dynamic application permission operation*/
        private boolean isPermissionRequested = false;

        public void requestPermission() {
            if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {
                isPermissionRequested = true;
                ArrayList<String> permissionsList = new ArrayList<>();
                String[] permissions = {//Add the permissions you want to use here.
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                };

                for (String perm : permissions) {
                    if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                        permissionsList.add(perm);
                        // Entering here means that you don't have permission.
                    }
                }

                if (!permissionsList.isEmpty()) {
                    String[] strings = new String[permissionsList.size()];
                    requestPermissions(permissionsList.toArray(strings), 0);
                }
                initBlueTooth();
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            initBlueTooth();
        }

        private void initBlueTooth() {
            //1.The application communicates with Bluetooth devices through BluetoothAdapter
            defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                Toast.makeText(this, "The device does not support Bluetooth", Toast.LENGTH_SHORT).show();
                return;
            }
            //2.Make sure that the device activates Bluetooth.
            if (!defaultAdapter.isEnabled()) {
                //If it does not start, jump to the Bluetooth settings interface.
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                return;
            } else {
                startDevices();
            }

            //With this code, you can manually open Bluetooth without switching to the settings page. You can open Bluetooth in the pop-up window on this page, which can be detected by other devices.
            Intent discoverableIntent =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);


            initServer();

        }

        //Set this device as a server and allow it to be linked.
        private void initServer() {
            acceptThread = new AcceptThread(defaultAdapter,mainHandler);
            acceptThread.start();
        }

        private void initView() {
            findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,SendActivity.class));
                }
            });
       findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,ShowActivity.class));
                }
            });



            EditText mSend = findViewById(R.id.et_send);
            Button mBtnSend = findViewById(R.id.btn_send);
            msg = findViewById(R.id.msg);

            mBtnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    byte[] bytes = mSend.getText().toString().getBytes();
                    if (connectThread!=null) {//Client sends data to server
                        connectThread.connectedThread.write(bytes);
                    }else {//The server sends data to the client
                        acceptThread.connectedThread.write(bytes);
                    }
                }
            });



            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            myBlueToothAdapter = new MyBlueToothAdapter();
            mRecyclerView.setAdapter(myBlueToothAdapter);
            myBlueToothAdapter.setOnConnectClick(new MyBlueToothAdapter.OnConnectClick() {
                @Override
                public void connectClick(BlueToothDevicesInfo blueToothDevicesInfo) {
                    //Display dialog box
                    ProgressDialogUtil.showProgressDialog(MainActivity.this);
                    //Hide dialog box



                    //Click the link button
                    if (connectThread == null) {
                        connectThread = new ConnectThread(blueToothDevicesInfo, defaultAdapter,mainHandler);
                        connectThread.start();



                    }
                }
            });
        }

        private HashSet<BluetoothDevice> devices = new HashSet<>();
        private void startDevices() {
            //Query the local paired device
            // To initiate a connection to the Bluetooth device, you only need to get the MAC address from the associated BluetoothDevice object, which can be retrieved by calling getAddress().
            Set<BluetoothDevice> pairedDevices = defaultAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address


                    if (!TextUtils.isEmpty(deviceName)&& devices.add(device)) {
                        BlueToothDevicesInfo blueToothDevicesInfo = new BlueToothDevicesInfo();
                        blueToothDevicesInfo.setAddress(deviceHardwareAddress);
                        blueToothDevicesInfo.setName(deviceName);
                        blueToothDevicesInfo.setPair(true);
                        blueToothDevicesInfo.setDevice(device);

                        myBlueToothAdapter.addItem(blueToothDevicesInfo);
                    }
                }
            }

            //3.Find remote bluetooth devices
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);
            boolean b = defaultAdapter.startDiscovery();

        }

        private final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Discovery has found a device. Get the BluetoothDevice
                    // Get a pairable Bluetooth device
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    if (!TextUtils.isEmpty(deviceName)&& devices.add(device)) {
                        BlueToothDevicesInfo blueToothDevicesInfo = new BlueToothDevicesInfo();
                        blueToothDevicesInfo.setAddress(deviceHardwareAddress);
                        blueToothDevicesInfo.setName(deviceName);
                        blueToothDevicesInfo.setPair(false);
                        blueToothDevicesInfo.setDevice(device);
                        myBlueToothAdapter.addItem(blueToothDevicesInfo);
                    }
                }
            }
        };

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_ENABLE_BT) {
                if (resultCode == RESULT_OK) {//User activates bluetooth
                    startDevices();
                } else {//The user did not start Bluetooth.
                    Toast.makeText(this, "Bluetooth is not turned on.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            unregisterReceiver(receiver);
        }




/**
 * Used to receive messages
 */
public static class MainHandler extends Handler {


    private final WeakReference<MainActivity> weakReference;

    public MainHandler(MainActivity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ConnectedMsgThread
                    .MESSAGE_WRITE://send data
                Log.e("Bluetooth",msg.obj.toString());
                break;
            case ConnectedMsgThread.MESSAGE_READ: //Received the data
                Log.e("Bluetooth","Received");
                int numBytes = msg.arg1;
                byte[] data = (byte[]) msg.obj;
                String s = new String(data, 0, numBytes);
                //  Toast.makeText(weakReference.get(), "Received"+s, Toast.LENGTH_SHORT).show();
                weakReference.get().msg.setText(s+"");
                ThreadManager.CoordinateData coordinateData = ThreadManager.getInstance().getCoordinateData();
                if (coordinateData!=null){
                    coordinateData.data(s);
                }


                break;
            case ConnectThread.CONNECT_FINISH: //Connection established
                Log.e("Bluetooth","Connection established");
                ProgressDialogUtil.dismiss();
                Toast.makeText(weakReference.get(), "Connection established", Toast.LENGTH_SHORT).show();
                ThreadManager.connected=true;

                break;
            case ConnectThread.CONNECT_FAULT: //Connection Failed
                Log.e("Bluetooth","Connection Failed");
                Toast.makeText(weakReference.get(), "Connection Failed", Toast.LENGTH_SHORT).show();
                ProgressDialogUtil.dismiss();
                ThreadManager.connected=false;
                break;
            case AcceptThread.SERVER_CONNECT: //Connection Failed
                Log.e("Bluetooth","The server has been linked ");
                ProgressDialogUtil.dismiss();
                Toast.makeText(weakReference.get(), "Connected", Toast.LENGTH_SHORT).show();
                ThreadManager.connected=true;
                break;
        }

    }

}
}