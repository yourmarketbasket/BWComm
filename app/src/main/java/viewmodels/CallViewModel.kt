package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.CallSession
import models.CallState
import models.Device
import services.CallService

class CallViewModel : ViewModel() {
    private val callService = CallService()
    private val _callSession = MutableStateFlow<CallSession?>(null)
    val callSession: StateFlow<CallSession?> = _callSession

    fun startCall(remoteDevice: Device, isVideoEnabled: Boolean) {
        val session = CallSession(
            remoteDevice = remoteDevice,
            isVideoEnabled = isVideoEnabled,
            isMuted = false,
            callState = CallState.RINGING
        )
        _callSession.value = session
        callService.startCall(session)
    }

    fun endCall() {
        _callSession.value?.let {
            callService.endCall(it)
            _callSession.value = it.copy(callState = CallState.ENDED)
        }
    }

    fun toggleMute() {
        _callSession.value?.let {
            val newMuteState = !it.isMuted
            _callSession.value = it.copy(isMuted = newMuteState)
            // Inform the service about the mute state change
        }
    }
}
