package com.example.bwcomm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import models.Device
import viewmodels.DeviceDiscoveryViewModel

class DeviceListFragment : Fragment() {
    private val viewModel: DeviceDiscoveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // setContent is not available here
        }
    }
}

@Composable
fun DeviceListScreen(navController: NavController, viewModel: DeviceDiscoveryViewModel) {
    val devices = viewModel.devices.collectAsState().value
    Column {
        Button(onClick = { viewModel.startDiscovery() }) {
            Text("Discover Devices")
        }
        LazyColumn {
            items(devices) { device ->
                DeviceListItem(device = device) {
                    viewModel.connectToDevice(device)
                    navController.navigate("chat/${device.device.deviceAddress}")
                }
            }
        }
    }
}

@Composable
fun DeviceListItem(device: Device, onDeviceClick: (Device) -> Unit) {
    Button(onClick = { onDeviceClick(device) }) {
        Text(text = device.device.deviceName ?: "Unknown Device")
    }
}