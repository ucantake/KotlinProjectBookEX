package webservices

import javax.net.ssl.TrustManagerFactory

actual object SslSettings : SslSettingsInterface {
    override fun getTrustManagerFactory(): TrustManagerFactory? {
        TODO("Not yet implemented")
    }

    override fun getSSLContext(): Any? {
        TODO("Not yet implemented")
    }

    override fun getTrustManager(): Any? {
        TODO("Not yet implemented")
    }
}