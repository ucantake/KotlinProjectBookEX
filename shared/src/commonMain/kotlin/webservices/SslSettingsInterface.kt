package webservices


/*
* интерфейс для получения настроек SSL
* функции являются вызовами одноименных функций с платформы JVM но с специфичным возвращаемым типом данных*/
interface SslSettingsInterface {
    fun getTrustManagerFactory(): Any?

    fun getSSLContext(): Any?

    fun getTrustManager(): Any?
}

expect object SslSettings : SslSettingsInterface //SSL для JVM (в осталных модулях просто заглушки)

fun getSslSettings(): SslSettingsInterface = SslSettings //для вызова SSL из других модулей