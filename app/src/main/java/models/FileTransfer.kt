package models

data class FileTransfer(
    val fileName: String,
    val fileSize: Long,
    val progress: Int,
    val isSending: Boolean
)
