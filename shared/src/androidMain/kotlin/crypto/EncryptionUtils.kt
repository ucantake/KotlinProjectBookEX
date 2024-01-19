package crypto

import SECRET_KEY
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

object EncryptionUtils {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"
    private const val CHARSET_NAME = "UTF-8"

    fun encrypt(data: String, key: String = SECRET_KEY): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(key))
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(data: String, key: String = SECRET_KEY): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, generateKey(key))
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data))
        return String(decryptedBytes, Charsets.UTF_8)
    }

    private fun generateKey(secretKey: String): Key {
        val keyBytes = secretKey.toByteArray(Charsets.UTF_8)
        val sha = java.security.MessageDigest.getInstance("SHA-1")
        val hashedBytes = sha.digest(keyBytes)
        val truncatedBytes = hashedBytes.copyOf(16) // AES-128 key size
        return SecretKeySpec(truncatedBytes, ALGORITHM)
    }
}
