package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.Message
import services.MessagingService

class ChatViewModel : ViewModel() {
    private val messagingService = MessagingService()
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        observeMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            messagingService.receivedMessages.collect { message ->
                _messages.value = _messages.value + message
            }
        }
    }

    fun sendMessage(content: String) {
        val message = Message(
            senderId = "me", // Replace with actual device ID
            content = content,
            timestamp = System.currentTimeMillis(),
            isMe = true
        )
        messagingService.sendMessage(message)
        _messages.value = _messages.value + message
    }
}
