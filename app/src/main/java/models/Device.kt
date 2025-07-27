package models

import android.net.wifi.p2p.WifiP2pDevice

data class Device(
    val device: WifiP2pDevice,
    val isConnected: Boolean = false
)
