package com.example.bwcomm.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts

class FilePickerFragment : Fragment() {

    private var onFileSelected: ((Uri) -> Unit)? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                onFileSelected?.invoke(uri)
            }
        }
    }

    fun pickFile(onFileSelected: (Uri) -> Unit) {
        this.onFileSelected = onFileSelected
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        filePickerLauncher.launch(intent)
    }
}
