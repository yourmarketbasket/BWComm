package services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import models.FileTransfer
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class FileTransferService {
    private val _fileTransferUpdates = MutableStateFlow<FileTransfer?>(null)
    val fileTransferUpdates: StateFlow<FileTransfer?> = _fileTransferUpdates

    fun sendFile(file: File, remoteDeviceAddress: String) {
        Thread {
            try {
                val socket = Socket(remoteDeviceAddress, 8889)
                val outputStream = socket.getOutputStream()
                val fileInputStream = file.inputStream()
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (fileInputStream.read(buffer).also { bytesRead = it } > 0) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.close()
                fileInputStream.close()
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun receiveFile(destinationFile: File) {
        Thread {
            try {
                val serverSocket = ServerSocket(8889)
                val socket = serverSocket.accept()
                val inputStream = socket.getInputStream()
                val fileOutputStream = FileOutputStream(destinationFile)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } > 0) {
                    fileOutputStream.write(buffer, 0, bytesRead)
                }
                fileOutputStream.close()
                inputStream.close()
                socket.close()
                serverSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}
