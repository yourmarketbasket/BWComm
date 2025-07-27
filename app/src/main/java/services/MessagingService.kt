package services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import models.Message
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class MessagingService {
    private val _receivedMessages = MutableStateFlow<Message?>(null)
    val receivedMessages: StateFlow<Message?> = _receivedMessages

    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null

    init {
        startServer()
    }

    private fun startServer() {
        Thread {
            try {
                serverSocket = ServerSocket(8888)
                while (true) {
                    val socket = serverSocket!!.accept()
                    handleClient(socket)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun handleClient(socket: Socket) {
        clientSocket = socket
        Thread {
            try {
                val input = socket.getInputStream().bufferedReader()
                while (true) {
                    val messageContent = input.readLine() ?: break
                    val message = Message(
                        senderId = "other", // Replace with actual device ID
                        content = messageContent,
                        timestamp = System.currentTimeMillis(),
                        isMe = false
                    )
                    _receivedMessages.value = message
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendMessage(message: Message) {
        Thread {
            try {
                clientSocket?.let {
                    val output = it.getOutputStream()
                    output.write((message.content + "\n").toByteArray())
                    output.flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}
