import webservices.SslSettingsInterface
import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

//эталонный класс для получения настроек SSL, не используется в проекте
object SslSettings : SslSettingsInterface {
    private fun getKeyStore(): KeyStore {
        val keyStoreFile = FileInputStream("keystore.jks")//читаем файт
        val keyStorePassword = "foobar".toCharArray()//переводим пароль в массив символов
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