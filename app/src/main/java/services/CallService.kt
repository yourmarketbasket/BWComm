package services

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import models.CallSession
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class CallService {
    private var audioRecord: AudioRecord? = null
    private var audioTrack: AudioTrack? = null
    private var isCalling = false
    private val sampleRate = 44100
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    fun startCall(session: CallSession) {
        isCalling = true
        startRecording(session.remoteDevice.device.deviceAddress)
        startPlaying()
    }

    fun endCall(session: CallSession) {
        isCalling = false
        audioRecord?.stop()
        audioRecord?.release()
        audioTrack?.stop()
        audioTrack?.release()
    }

    private fun startRecording(remoteAddress: String) {
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, bufferSize)
        audioRecord?.startRecording()
        Thread {
            val buffer = ByteArray(bufferSize)
            val socket = DatagramSocket()
            val address = InetAddress.getByName(remoteAddress)
            while (isCalling) {
                val read = audioRecord!!.read(buffer, 0, buffer.size)
                val packet = DatagramPacket(buffer, read, address, 8890)
                socket.send(packet)
            }
        }.start()
    }

    private fun startPlaying() {
        audioTrack = AudioTrack(android.media.AudioManager.STREAM_VOICE_CALL, sampleRate, AudioFormat.CHANNEL_OUT_MONO, audioFormat, bufferSize, AudioTrack.MODE_STREAM)
        audioTrack?.play()
        Thread {
            try {
                val socket = DatagramSocket(8890)
                val buffer = ByteArray(bufferSize)
                while (isCalling) {
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)
                    audioTrack?.write(packet.data, 0, packet.length)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}
