package com.example.bwcomm

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class DeviceListFragment : Fragment() {
    private val viewModel: DeviceListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DeviceListScreen(navController = findNavController())
            }
        }
    }
}

@Composable
fun DeviceListScreen(navController: NavController) {
    val devices = viewModel.devices.collectAsState().value
    LazyColumn {
        items(devices) { device ->
            Button(onClick = { navController.navigate("chat/${device.address}") }) {
                Text(device.name ?: "Unknown Device")
            }
        }
    }
}