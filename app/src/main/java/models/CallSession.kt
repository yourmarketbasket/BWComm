package models

data class CallSession(
    val remoteDevice: Device,
    val isVideoEnabled: Boolean,
    val isMuted: Boolean,
    val callState: CallState
)

enum class CallState {
    IDLE,
    RINGING,
    ACTIVE,
    ENDED
}
