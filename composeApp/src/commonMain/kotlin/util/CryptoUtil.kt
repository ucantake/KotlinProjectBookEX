package util

expect interface CryptoUtil {
    fun hash(data: ByteArray): ByteArray
    fun verify(data: ByteArray, signature: ByteArray): Boolean
    fun generateKeyPair(): KeyPair
    fun sign(data: ByteArray, privateKey: ByteArray): ByteArray
    fun encrypt(data: ByteArray, publicKey: ByteArray): ByteArray
    fun decrypt(data: ByteArray, privateKey: ByteArray): ByteArray

}

expect interface KeyPair {
    val publicKey: ByteArray
    val privateKey: ByteArray
    fun getPublicKey(): ByteArray
    fun getPrivateKey(): ByteArray
    fun getSeedPhrase(): String

}
