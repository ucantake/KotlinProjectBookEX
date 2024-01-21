package webservices

import BASE_LINK
import NAMEUSER
import OFFLINEDATA
import PASSWORDUSER
import SERVER_IP
import SERVER_PORT
import com.google.gson.Gson
import crypto.EncryptionUtils
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
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
        // Установка кодировки UTF-8 для запросов
        defaultRequest {
            charset(Charsets.UTF_8.toString())
        }

        // Установка кодировки UTF-8 для ответов
        HttpResponseValidator {
            charset(Charsets.UTF_8.toString())
        }
        Charsets {
            Charsets.UTF_8
        }

    }

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

    private suspend fun getLinkBodyAsTextCrypt (
        link : String,
        client: HttpClient = this.client
    ) : String {
        val clientGet = client.get(link).bodyAsText()
        val data = EncryptionUtils.decrypt(clientGet)
        return data
    }

    /*
    * Функция форматирования базовой ссылки
    * выходные данные передаются в функцию getLinkBodyAsText или getLinkBody как ссылка
    * Входные данные :
    *   берутся из констант
    *   ссылка - на которую отправляется запрос
    * Выходные данные :
    *   ссылка в формате https://ip:port/baseLinkGet/link
     */
    private fun linkFormatterHttp (link : String) : String = "https://$SERVER_IP:$SERVER_PORT/$BASE_LINK/$link"

    /*
    * Функция форматирования ссылки для авторизации
     */
    override suspend fun authLinkForman (
        name : String,
        password : String
    ) : String = getLinkBodyAsText(linkFormatterHttp("name/$name&password/$password"))

    override suspend fun getDataProfile(name: String): String {
        try {
            return getLinkBodyAsTextCrypt(linkFormatterHttp("name/$name&token/$PASSWORDUSER/profile")).toString()
        }catch (e : Exception) {
            println("EXEPCTION in getDataProfile in HttpApiClient.kt " + e.message)
            //TODO написать действительный json
            return OFFLINEDATA
        }
    }

    override suspend fun createUser(name: String, password: String, email: String, account : String, key : String): String {
        return getLinkBodyAsText(linkFormatterHttp("registration/name/$name/password/$password/email/$email/$account/$key"))
    }

    @OptIn(InternalAPI::class)
    override suspend fun addBook(
        name: String,
        title: String,
        author: String,
        isbn: String,
        udc: String,
        bbk: String,
        price: String
    ): String {

        val bookData = BookData(
            name = name,
            title = title,
            author = author,
            isbn = isbn,
            udc = udc,
            bbk = bbk,
            price = price
        )

        // Сериализуем объект в JSON
        val jsonData = Gson().toJson(bookData)

        // Отправляем POST запрос с JSON в теле
        return client.post(linkFormatterHttp("name/${NAMEUSER}/addBook")) {
            contentType(ContentType.Application.Json)
            body = jsonData
        }.bodyAsText()
    }

    override suspend fun getJsonBooks(name: String): String {
        return  getLinkBodyAsTextCrypt(linkFormatterHttp("name/$name/getBooks"))
    }

    override suspend fun getJsonBooksUsers(name: String): String {
        return getLinkBodyAsTextCrypt(linkFormatterHttp("name/$name/search"))
    }

    @OptIn(InternalAPI::class)
    override suspend fun setSmartContract(
        name: String,
        selectedValueUser: String,
        selectedValueBook: String,
        price: String,
        comment: String
    ): String {
        val data = CreateSmartContract(
            userSender = name,
            userResiver = selectedValueUser,
            bookTitle = selectedValueBook,
            price = price,
            comment = comment
        )

        val jsonData = Gson().toJson(data)

        return client.post(linkFormatterHttp("createSmartContract/name/$name/password/$PASSWORDUSER")) {
            contentType(ContentType.Application.Json)
            body = jsonData
        }.bodyAsText()
    }

    override suspend fun getBooksDataSmartContract(name: String): String {
        return getLinkBodyAsTextCrypt(linkFormatterHttp("name/$name/getTransactions"))
    }

}

data class BookData(
    val name: String,
    val title: String,
    val author: String,
    val isbn: String,
    val udc: String,
    val bbk: String,
    val price: String
)
data class CreateSmartContract (
    val userSender: String,
    val userResiver: String,
    val bookTitle: String,
    val price: String,
    val comment: String
)