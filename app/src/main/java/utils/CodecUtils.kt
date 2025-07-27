package utils

import android.media.MediaCodec
import android.media.MediaFormat
import java.nio.ByteBuffer

object CodecUtils {

    fun encode(codec: MediaCodec, inputBuffer: ByteBuffer, presentationTimeUs: Long) {
        val inputBufferIndex = codec.dequeueInputBuffer(10000)
        if (inputBufferIndex >= 0) {
            val buffer = codec.getInputBuffer(inputBufferIndex)
            buffer?.put(inputBuffer)
            codec.queueInputBuffer(inputBufferIndex, 0, inputBuffer.remaining(), presentationTimeUs, 0)
        }
    }

    fun decode(codec: MediaCodec, outputBuffer: MediaCodec.BufferInfo): ByteBuffer? {
        val outputBufferIndex = codec.dequeueOutputBuffer(outputBuffer, 10000)
        if (outputBufferIndex >= 0) {
            return codec.getOutputBuffer(outputBufferIndex)
        }
        return null
    }
}
