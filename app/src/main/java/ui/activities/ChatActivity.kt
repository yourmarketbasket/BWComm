// ChatActivity.kt
package com.example.bwcomm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bwcomm.ui.theme.BWCommTheme
import viewmodels.ChatViewModel

class ChatActivity : ComponentActivity() {

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BWCommTheme {
                ChatScreen(viewModel = viewModel)
            }
        }
    }

    companion object {
        private const val EXTRA_DEVICE_ID = "extra_device_id"

        fun launch(context: Context, deviceId: String?) {
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra(EXTRA_DEVICE_ID, deviceId)
            }
            context.startActivity(intent)
        }
    }
}

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageItem(message = message)
            }
        }
        Row(modifier = Modifier.padding(8.dp)) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                viewModel.sendMessage(inputText)
                inputText = ""
            }) {
                Text("Send")
            }
        }
    }
}