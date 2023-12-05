package util

actual interface CryptoUtil {
    actual fun hash(data: ByteArray): ByteArray
    actual fun verify(data: ByteArray, signature: ByteArray): Boolean
    actual fun generateKeyPair(): KeyPair
    actual fun sign(data: ByteArray, privateKey: ByteArray): ByteArray
    actual fun encrypt(data: ByteArray, publicKey: ByteArray): ByteArray
    actual fun decrypt(data: ByteArray, privateKey: ByteArray): ByteArray

}
actual interface KeyPair {
    actual val publicKey: ByteArray
    actual val privateKey: ByteArray
    actual fun getPublicKey(): ByteArray
    actual fun getPrivateKey(): ByteArray
    actual fun getSeedPhrase(): String

}