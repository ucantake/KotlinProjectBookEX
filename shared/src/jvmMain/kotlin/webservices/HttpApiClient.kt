package webservices

import BASE_LINK_GET
import OFFLINEDATA
import PASSWORDUSER
import SERVER_IP
import SERVER_PORT
import WORKMODE
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.net.ssl.TrustManager

//отправка данных на сервер
actual class HttpApiClient : HttpApiClientInterface {


    /*
    * Создание HTTPS клиента
    * Для отправки данных на сервер
     */
    private val client = HttpClient(CIO) { //создаем HTTPS клиент
        engine {
            https {
                serverName = SERVER_IP
                trustManager = getSslSettings().getTrustManager() as TrustManager?
            }
        }
    }

    /*
    * Функция получения тела ответа на запрос
    * Входные данные :
    *   ссылка - на которую отправляется запрос
    *   клиент - клиент который отправляет запрос (по умолчанию созданный в классе)
    * Выходные данные :
    *   тело ответа на запрос
     */
    private suspend fun getLinkBody (
        link: String,
        client: HttpClient = this.client
    ) : HttpResponse = client.get(link).body()

    /*
    * Функция получения тела в текст ответа на запрос
    * Входные данные :
    *   ссылка - на которую отправляется запрос
    *   клиент - клиент который отправляет запрос (по умолчанию созданный в классе)
    * Выходные данные :
    *   тело ответа на запрос в виде текста
     */
    private suspend fun getLinkBodyAsText(
        link: String,
        client: HttpClient = this.client
    ) : String = client.get(link).bodyAsText()

    /*
    * Функция форматирования базовой ссылки
    * выходные данные передаются в функцию getLinkBodyAsText или getLinkBody как ссылка
    * Входные данные :
    *   берутся из констант
    *   ссылка - на которую отправляется запрос
    * Выходные данные :
    *   ссылка в формате https://ip:port/baseLinkGet/link
     */
    private fun linkFormatterHttp (link : String) : String = "https://$SERVER_IP:$SERVER_PORT/$BASE_LINK_GET/$link"

    /*
    * Функция форматирования ссылки для авторизации
     */
    override suspend fun authLinkForman (
        name : String,
        password : String
    ) : String = getLinkBodyAsText(linkFormatterHttp("name/$name&password/$password"))

    override suspend fun getDataProfile(name: String): String {
        try {
            return getLinkBodyAsText(linkFormatterHttp("name/$name&token/$PASSWORDUSER/profile"))
        }catch (e : Exception) {
            println("EXEPCTION in getDataProfile in HttpApiClient.kt " + e.message)
            //TODO написать действительный json
            return OFFLINEDATA
        }
    }

    override suspend fun createUser(name: String, password: String, email: String, account : String, key : String): String {
        return getLinkBodyAsText(linkFormatterHttp("registration/name/$name/password/$password/email/$email/$account/$key"))
    }

    override suspend fun addBook(
        name: String,
        title: String,
        author: String,
        isbn: String,
        udc: String,
        bbk: String,
        price: String
    ): String {
        return getLinkBodyAsText(linkFormatterHttp("/name/${name}/addBook/title/${title}/author/${author}/isbn/${isbn}/udc/${udc}/bbk/${bbk}/price/${price}"))
    }

}