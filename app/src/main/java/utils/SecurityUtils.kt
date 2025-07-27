package utils

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SecurityUtils {

    private const val ALGORITHM = "AES"

    fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    fun encrypt(data: ByteArray, key: SecretKey): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(data)
    }

    fun decrypt(data: ByteArray, key: SecretKey): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(data)
    }
}
