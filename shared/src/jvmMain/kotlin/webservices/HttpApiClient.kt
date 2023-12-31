package webservices

import BASE_LINK_GET
import SERVER_IP
import SERVER_PORT
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.net.ssl.TrustManager

//отправка данных на сервер
actual class HttpApiClient : HttpApiClientInterface {

    private val ip = SERVER_IP
    private val client = HttpClient(CIO) { //создаем HTTPS клиент
        engine {
            https {
                serverName = ip
                trustManager = getSslSettings().getTrustManager() as TrustManager?
            }
        }
    }

    //отправка данных методом get
    private suspend fun getLink(
        link: String,
        client: HttpClient = this.client,
    ) : String {
        val response = client.get(link).bodyAsText()

        return response

    }
    //формирование ссылки для отправки данных
    private fun linkFormatterHttp (link : String) : String = "https://$ip:$SERVER_PORT/$BASE_LINK_GET/$link"

    //публичная функция для отправки данных на сервер
    //для авторизации пользователя
    override suspend fun authLinkForman (name : String, password : String) : String = getLink(linkFormatterHttp("name/$name&password/$password"))


}