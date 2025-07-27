package com.example.bwcomm.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bwcomm.ui.theme.BWCommTheme
import models.CallState
import viewmodels.CallViewModel

class CallActivity : ComponentActivity() {

    private val viewModel: CallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BWCommTheme {
                CallScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CallScreen(viewModel: CallViewModel) {
    val callSession = viewModel.callSession.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (callSession?.callState) {
            CallState.RINGING -> {
                Text("Ringing...")
                Row {
                    Button(onClick = { /* TODO: Accept call */ }) {
                        Text("Accept")
                    }
                    Button(onClick = { viewModel.endCall() }) {
                        Text("Decline")
                    }
                }
            }
            CallState.ACTIVE -> {
                Text("In Call")
                Button(onClick = { viewModel.toggleMute() }) {
                    Text(if (callSession.isMuted) "Unmute" else "Mute")
                }
                Button(onClick = { viewModel.endCall() }) {
                    Text("End Call")
                }
            }
            else -> {
                // Idle or ended state
            }
        }
    }
}