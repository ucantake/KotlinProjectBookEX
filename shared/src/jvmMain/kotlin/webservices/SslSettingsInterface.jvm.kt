package webservices

import PRIVATE_PASSWORD
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

actual object SslSettings : SslSettingsInterface {
    private fun getKeyStore(): KeyStore {
        val File = File("").absolutePath
        val keyStoreFile = FileInputStream("$File\\resources\\keystore.jks")//читаем файт
        val keyStorePassword = PRIVATE_PASSWORD.toCharArray()//переводим пароль в массив символов
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(keyStoreFile, keyStorePassword)
        return keyStore
    }

    private fun TrustManagerFactory(): TrustManagerFactory? {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(getKeyStore())
        return trustManagerFactory
    }

    private fun SslContext(): SSLContext? {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, TrustManagerFactory()?.trustManagers, null)
        return sslContext
    }

    private fun TrustManager(): X509TrustManager {
        return TrustManagerFactory()?.trustManagers?.first { it is X509TrustManager } as X509TrustManager
    }

    /*
    * используется для получения TrustManagerFactory от разных блоков
    *
    */

    override fun getTrustManagerFactory(): TrustManagerFactory? {
        return TrustManagerFactory()

    }

    override fun getSSLContext(): Any? {
        return SslContext()
    }

    override fun getTrustManager(): Any? {
        return TrustManager()
    }
}