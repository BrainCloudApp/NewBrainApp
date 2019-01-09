package com.lmq.ui.buletooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;


import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;
import com.lmq.tool.PermisstionCheck;
import com.lmq.ui.HealthInfo_Base_Edit_Activity;
import com.lmq.ui.Settings_Advice_Activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;

public class BluetoothActivity extends BaseActivity{
    @Override
    protected int setContentView(){
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

        setTitle("数据采集");
        getPermission();
        initReceiver();
        initbluetooth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryReciver();
    }

    @BindView(R.id.choseddevice)TextView choseddevice;
    @BindView(R.id.datamessage)TextView datamessage;
    @BindView(R.id.devices)TextView devices;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothReceiver myreceiver;

    private ArrayList<BluetoothDevice> bluetoothDevices=new ArrayList<>();
    private static final String NAME = "BT_DEMO";
    private final int BUFFER_SIZE = 1024;
    private static final UUID BT_UUID = UUID.fromString("02001101-0001-1000-8080-00805F9BA9BA");
    ConnectThread connectThread;
    public void initbluetooth(){
        if (!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();
        }
            bluetoothAdapter.startDiscovery();
        datamessage.setText("正在搜索");
    }
    public void initReceiver(){
        myreceiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");


        registerReceiver(myreceiver, filter);
    }
    public void destoryReciver(){
        unregisterReceiver(myreceiver);
    }
    public void showTxtMes(final String mes){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                datamessage.setText(mes);
            }
        });
    }
    public void refreshDevices(){
         String result="";
        for (int i=0;i<bluetoothDevices.size();i++){
            result+=bluetoothDevices.get(i).getName();
            if(i<bluetoothDevices.size()-1);
            result+="/n";
        }
        final  String showmes=result;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                devices.setText(showmes);
            }
        });
    }
    public void addDevices(final String name){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                devices.setText(devices.getText().toString()+"\n"+name);
            }
        });
    }

    private boolean haspermission=false;
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            haspermission=   PermisstionCheck.checkAndRequestPermission_Bluetooth(BluetoothActivity.this) ;
        }else{
            haspermission=true;
        }


    }

    class BluetoothReceiver extends BroadcastReceiver {

        String pin = "1234";  //此处为你要连接的蓝牙设备的初始密钥，一般为1234或0000
        public BluetoothReceiver() {

        }

        //广播接收器，当远程蓝牙设备被发现时，回调函数onReceiver()会被执行
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction(); //得到action
                Log.e("action1=", action);
                BluetoothDevice btDevice = null;  //创建一个蓝牙device对象
                // 从Intent中获取设备对象
                btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {  //发现设备
                    String devicename=btDevice.getName();
                    if (devicename==null)
                        devicename="null";
                    Log.e("发现设备:", "[" +  devicename+ "]" + ":" + btDevice.getAddress());
                    if (!bluetoothDevices.contains(btDevice)) {
                        bluetoothDevices.add(btDevice);
                    }
                    addDevices(devicename);

                    if (devicename.contains("HC-05"))//HC-05设备如果有多个，第一个搜到的那个会被尝试。
                    {
                        if (btDevice.getBondState() == BluetoothDevice.BOND_NONE) {

                            Log.e("lmq", "attemp to bond:" + "[" + devicename + "]");
                            try {
                                //通过工具类ClsUtils,调用createBond方法
                                ClsUtils.createBond(btDevice.getClass(), btDevice);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } else
                        Log.e("error", "Is faild");
                } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                    //停止搜索
                    showTxtMes("搜索完成");
                } else if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) //再次得到的action，会等于PAIRING_REQUEST
                {
                    Log.e("action2=", action);
                    if (btDevice.getName().contains("HC-05")) {
                        Log.e("here", "OKOKOK");

                        try {

                            //1.确认配对
                            ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
                            //2.终止有序广播
                            Log.i("order...", "isOrderedBroadcast:" + isOrderedBroadcast() + ",isInitialStickyBroadcast:" + isInitialStickyBroadcast());
                            abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
                            //3.调用setPin方法进行配对...
                            boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, pin);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else
                        Log.e("提示信息", "这个设备不是目标蓝牙设备");

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 连接蓝牙设备
     */
    private void connectDevice(BluetoothDevice device) {



        try {
            //创建Socket
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(BT_UUID);
            //启动连接线程
            connectThread = new ConnectThread(socket, true);
            connectThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 连接线程
     */
    private class ConnectThread extends Thread {

        private BluetoothSocket socket;
        private boolean activeConnect;
        InputStream inputStream;
        OutputStream outputStream;

        private ConnectThread(BluetoothSocket socket, boolean connect) {
            this.socket = socket;
            this.activeConnect = connect;
        }

        @Override
        public void run() {
            try {
                //如果是自动连接 则调用连接方法
                if (activeConnect) {
                    socket.connect();
                }

                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes;
                while (true) {
                    //读取数据
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        final byte[] data = new byte[bytes];
                        System.arraycopy(buffer, 0, data, 0, bytes);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        /**
         * 发送数据
         *
         * @param msg
         */
        public void sendMsg(final String msg) {

            byte[] bytes = msg.getBytes();
            if (outputStream != null) {
                try {
                    //发送数据
                    outputStream.write(bytes);

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    /**
     * 监听线程
     */
    private class ListenerThread extends Thread {

        private BluetoothServerSocket serverSocket;
        private BluetoothSocket socket;

        @Override
        public void run() {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, BT_UUID);
                while (true) {
                    //线程阻塞，等待别的设备连接
                    socket = serverSocket.accept();

                    connectThread = new ConnectThread(socket, false);
                    connectThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
