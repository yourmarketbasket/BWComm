package services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import models.Device

class ConnectivityService(private val context: Context) {
    private val wifiP2pManager: WifiP2pManager = context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    private val channel: WifiP2pManager.Channel = wifiP2pManager.initialize(context, Looper.getMainLooper(), null)
    private val _discoveredDevices = MutableStateFlow<List<Device>>(emptyList())
    val discoveredDevices: StateFlow<List<Device>> = _discoveredDevices

    private val peerListListener = WifiP2pManager.PeerListListener { peers ->
        _discoveredDevices.value = peers.deviceList.map { Device(it) }
    }

    private val wifiDirectBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    wifiP2pManager.requestPeers(channel, peerListListener)
                }
            }
        }
    }

    init {
        val intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
        context.registerReceiver(wifiDirectBroadcastReceiver, intentFilter)
    }

    fun startDiscovery() {
        wifiP2pManager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                // Discovery initiated
            }

            override fun onFailure(reason: Int) {
                // Discovery failed
            }
        })
    }

    fun connectToDevice(device: WifiP2pDevice) {
        val config = android.net.wifi.p2p.WifiP2pConfig().apply {
            deviceAddress = device.deviceAddress
        }
        wifiP2pManager.connect(channel, config, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                // Connection initiated
            }

            override fun onFailure(reason: Int) {
                // Connection failed
            }
        })
    }

    fun cleanup() {
        context.unregisterReceiver(wifiDirectBroadcastReceiver)
    }
}
