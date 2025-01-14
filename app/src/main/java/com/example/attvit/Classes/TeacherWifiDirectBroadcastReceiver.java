package com.example.attvit.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.attvit.WPSTeacherActivity;

public class TeacherWifiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WPSTeacherActivity wpsTeacherActivity;

    public TeacherWifiDirectBroadcastReceiver(WifiP2pManager manager1, WifiP2pManager.Channel channel1, WPSTeacherActivity wpsTeacherActivity1) {
        this.manager = manager1;
        this.channel = channel1;
        this.wpsTeacherActivity = wpsTeacherActivity1;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "Wifi is ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wifi is OFF", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (manager != null)
                manager.requestPeers(channel, wpsTeacherActivity.peerListListener);
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (manager == null)
                return;
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected())
                manager.requestConnectionInfo(channel, wpsTeacherActivity.connectionInfoListener);
            else {
                Log.d("Wrong Position", "Detected!");
                wpsTeacherActivity.tConnectionStatus.setText("Device disconnected");
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
