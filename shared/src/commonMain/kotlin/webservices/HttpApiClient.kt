package webservices

import SERVER_IP
import BASE_LINK_GET
import SERVER_PORT
// error
//import SslSettingsCommon
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

//error
//private val sslSettingsCommon : SslSettingsCommon = SslSettings()
class HttpApiClient  {

    val ip = SERVER_IP

    //соединение с сервером
    //TODO возможности HTTPS
    private fun connect (): HttpClient {
//        runBlocking {
            val client = HttpClient(CIO) {
                engine {
                    https {
                        serverName = ip
//                        error
//                        trustManager = SslSettings.getTrustManager()
                    }
                }
            }

//        }
        return client
    }

    //отправка данных методом get
    private suspend fun getLink(
        link: String,
        client: HttpClient = connect(),
    ) : String {
        val response = runBlocking {
            client.get(link)
        }

        return response.bodyAsText()

    }
    //формирование ссылки для отправки данных
    private fun linkFormatterHttp (link : String) : String = "http://$ip:$SERVER_PORT/$BASE_LINK_GET/$link"

    //публичная функция для отправки данных на сервер
    //для авторизации пользователя
    suspend fun authLinkForman (name : String = "def", password : String = "ault") : String = getLink(linkFormatterHttp("name/$name&password/$password"))


}