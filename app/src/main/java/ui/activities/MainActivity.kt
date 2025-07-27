// MainActivity.kt
package com.example.bwcomm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bwcomm.ui.theme.BWCommTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BWCommTheme {
                DeviceListScreen() // Defined in DeviceListFragment.kt
            }
        }
    }
}