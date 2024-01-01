package webservices

import PRIVATE_PASSWORD
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

actual object SslSettings : SslSettingsInterface {

    private fun getKeyStore(): Any? {
        val File = File("").absolutePath
        Log.i("SslSettings", "getKeyStore: File $File")
        val keyStoreFile = FileInputStream("\\data\\data\\org.example.project\\files\\keystore.jks")//читаем файл но не та функция
        val keyStorePassword = PRIVATE_PASSWORD.toCharArray()//переводим пароль в массив символов
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(keyStoreFile, keyStorePassword)
        return keyStore
    }
    override fun getTrustManagerFactory(): TrustManagerFactory? {
        return getSslSettings().getTrustManager() as TrustManagerFactory?
    }

    override fun getSSLContext(): SSLContext {
        return getSslSettings().getSSLContext() as SSLContext
    }

    override fun getTrustManager(): Any? {
        return try {
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            trustManagerFactory.trustManagers.firstOrNull { it is X509TrustManager } as? X509TrustManager
        } catch (e: Exception) {
            null
        }
    }
}