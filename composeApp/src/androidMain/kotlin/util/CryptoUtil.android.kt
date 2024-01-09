package util

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual interface CryptoUtil {
    actual fun hash(data: ByteArray): ByteArray
    actual fun verify(data: ByteArray, signature: ByteArray): Boolean
    actual fun generateKeyPair(): KeyPair
    actual fun sign(data: ByteArray, privateKey: ByteArray): ByteArray
    actual fun encrypt(data: ByteArray, publicKey: ByteArray): ByteArray
    actual fun decrypt(data: ByteArray, privateKey: ByteArray): ByteArray

}
actual interface KeyPair {
//    actual val publicKey: ByteArray
//    actual val privateKey: ByteArray
    actual fun getPublicKey(): ByteArray
    actual fun getPrivateKey(): ByteArray
    actual fun getSeedPhrase(): String

}

actual class Crypt actual constructor() {
    actual fun encrypt(data: ByteArray, publicKey: ByteArray): ByteArray {
        TODO("Not yet implemented")
    }

    actual fun decrypt(data: ByteArray, privateKey: ByteArray): ByteArray {
        TODO("Not yet implemented")
    }

}

