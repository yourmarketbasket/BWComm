package models

data class Message(
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val isMe: Boolean
)
