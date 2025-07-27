package viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.FileTransfer
import services.FileTransferService

class FileTransferViewModel : ViewModel() {
    private val fileTransferService = FileTransferService()
    private val _fileTransfers = MutableStateFlow<List<FileTransfer>>(emptyList())
    val fileTransfers: StateFlow<List<FileTransfer>> = _fileTransfers

    init {
        observeFileTransfers()
    }

    private fun observeFileTransfers() {
        viewModelScope.launch {
            fileTransferService.fileTransferUpdates.collect { fileTransfer ->
                fileTransfer?.let {
                    val updatedList = _fileTransfers.value.filterNot { it.fileName == fileTransfer.fileName }
                    _fileTransfers.value = updatedList + fileTransfer
                }
            }
        }
    }

    fun sendFile(fileUri: Uri) {
        // Implementation for sending a file
    }
}
