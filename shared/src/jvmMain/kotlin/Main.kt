import kotlinx.coroutines.*
import webservices.HttpApiClient
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun main() {
    val inputText = "abcdefghigklmnopqrstuvwxyz0123456789"
    val algorithm = "AES/CBC/PKCS5Padding"
    val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
    val iv = IvParameterSpec(ByteArray(16))

    println(encrypt(algorithm, inputText, key, iv))

    println(decrypt(algorithm, encrypt(algorithm, inputText, key, iv), key, iv))
}

suspend fun get(){
    try {
        val httpsClietn = HttpApiClient()

        val stri = httpsClietn.authLinkForman("textFieldStateEmail", "textFieldStatePassword")
        println("str = " + stri)
    } catch (e : Exception) {
        println("EXEPTION $e")
    }

}

suspend fun test () {

    for (i in 0..5){
        delay(400L)
        println(i)
    }
    return
}