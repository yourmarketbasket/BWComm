package com.example.bwcomm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bwcomm.ui.activities.ChatActivity
import com.example.bwcomm.ui.fragments.DeviceListScreen
import com.example.bwcomm.ui.theme.BWCommTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BWCommTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "device_list") {
                    composable("device_list") {
                        DeviceListScreen(navController = navController)
                    }
                    composable("chat/{deviceId}") { backStackEntry ->
                        val deviceId = backStackEntry.arguments?.getString("deviceId")
                        ChatActivity.launch(this@MainActivity, deviceId)
                    }
                }
            }
        }
    }
}