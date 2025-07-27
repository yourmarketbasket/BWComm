// CallActivity.kt
package com.example.bwcomm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.bwcomm.ui.theme.BWCommTheme

class CallActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BWCommTheme {
                Text("Call Feature: Coming Soon")
            }
        }
    }
}