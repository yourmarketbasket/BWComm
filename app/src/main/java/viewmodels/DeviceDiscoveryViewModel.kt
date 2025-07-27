package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.Device
import services.ConnectivityService

class DeviceDiscoveryViewModel(application: Application) : AndroidViewModel(application) {

    private val connectivityService = ConnectivityService(application)
    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices

    init {
        observeDevices()
        startDiscovery()
    }

    private fun observeDevices() {
        viewModelScope.launch {
            connectivityService.discoveredDevices.collect {
                _devices.value = it
            }
        }
    }

    fun startDiscovery() {
        connectivityService.startDiscovery()
    }

    fun connectToDevice(device: Device) {
        connectivityService.connectToDevice(device.device)
    }
}
