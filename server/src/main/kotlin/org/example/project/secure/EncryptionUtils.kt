//package org.example.project.secure
//import SECRET_KEY
//import java.security.Key
//import javax.crypto.Cipher
//import javax.crypto.spec.SecretKeySpec
//import java.util.Base64
//
//object EncryptionUtils {
//
//    private const val ALGORITHM = "AES"
//    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"
//    private const val CHARSET_NAME = "UTF-8"
//
//    fun encrypt(data: String, key: String = SECRET_KEY, algorithm: String = "SHA-1"): String {
//        val cipher = Cipher.getInstance(TRANSFORMATION)
//        cipher.init(Cipher.ENCRYPT_MODE, generateKey(key, algorithm))
//        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
//        return Base64.getEncoder().encodeToString(encryptedBytes)
//    }
//
//    fun decrypt(data: String, key: String = SECRET_KEY, algorithm: String = "SHA-1"): String {
//        val cipher = Cipher.getInstance(TRANSFORMATION)
//        cipher.init(Cipher.DECRYPT_MODE, generateKey(key, algorithm))
//        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data))
//        return String(decryptedBytes, Charsets.UTF_8)
//    }
//
//    private fun generateKey(secretKey: String, algorithm: String): Key {
//        val keyBytes = secretKey.toByteArray(Charsets.UTF_8)
//        val sha = java.security.MessageDigest.getInstance(algorithm)
//        val hashedBytes = sha.digest(keyBytes)
//        val truncatedBytes = hashedBytes.copyOf(16) // AES-128 key size
//        return SecretKeySpec(truncatedBytes, algorithm)
//    }
//}
