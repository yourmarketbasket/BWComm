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

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BWCommTheme {
                ChatScreen(viewModel = ChatViewModel())
            }
        }
    }
}

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    var inputText by remember { mutableStateOf("") }

//    Column {
//        LazyColumn(Modifier.weight(1f)) {
//            items(messages) { message ->
//                Text(message.content)
//            }
//        }
//        TextField(
//            value = inputText,
//            onValueChange = { inputText = it },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Button(onClick = { viewModel.sendMessage(inputText); inputText = "" }) {
//            Text("Send")
//        }
//    }
}