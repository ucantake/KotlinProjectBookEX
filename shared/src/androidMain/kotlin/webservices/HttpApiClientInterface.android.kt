package webservices

import BASE_LINK
import JsonDataObjects.BookData
import JsonDataObjects.CreateSmartContract
import NAMEUSER
import PASSWORDUSER
import SERVER_IP
import SERVER_PORT
import com.google.gson.Gson
import crypto.EncryptionUtils
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*

actual class HttpApiClient actual constructor() : HttpApiClientInterface {
    val client = HttpClient(CIO) {
        engine {
//            https {
//                serverName = SERVER_IP
//                trustManager = getSslSettings().getTrustManager() as TrustManager?
//            }
        }
    }
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

    override suspend fun authLinkForman(name: String, password: String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK/name/$name&password/$password").bodyAsText()
    }

    override suspend fun getDataProfile(name: String): String {
        try {
            return getLinkBodyAsTextCrypt("http://$SERVER_IP:80/$BASE_LINK/name/$name&token/$PASSWORDUSER/profile")
        }catch (e : Exception) {
            println("EXEPCTION in getDataProfile in Android in HttpApiClient.kt " + e.message)
            return "{}"
        }
    }

    override suspend fun createUser(name: String, password: String, email: String, account : String, key : String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK/registration/name/$name/password/$password/email/$email/$account/$key").bodyAsText()
    }

    @OptIn(InternalAPI::class)
    override suspend fun addBook(
        name: String,
        title: String,
        author: String,
        isbn: String,
        udc: String,
        bbk: String,
        price: String,
        genre: String,
        datePublished : String
    ): String {
        val gson = Gson()

        // Создаем объект с вашими данными
        val bookData = BookData(
            name = name,
            title = title,
            author = author,
            isbn = isbn,
            udc = udc,
            bbk = bbk,
            price = price,
            datePublished = datePublished,
            genre = genre
        )

        // Сериализуем объект в JSON
        val jsonData = gson.toJson(bookData)

        // Отправляем POST запрос с JSON в теле
        return client.post("http://$SERVER_IP:80/$BASE_LINK/name/${NAMEUSER}/addBook") {
            contentType(ContentType.Application.Json)
            body = jsonData
        }.bodyAsText()
    }

    override suspend fun getJsonBooks(name: String): String {
        return getLinkBodyAsTextCrypt("http://$SERVER_IP:80/$BASE_LINK/name/${name}/getBooks")
    }

    override suspend fun getJsonBooksUsers(name: String): String {
        return getLinkBodyAsTextCrypt("http://$SERVER_IP:80/$BASE_LINK/name/$name/search")
    }

    @OptIn(InternalAPI::class)
    override suspend fun createSmartContract(
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

        return client.post("http://$SERVER_IP:80/$BASE_LINK/createSmartContract/name/$name/password/$PASSWORDUSER") {
            contentType(ContentType.Application.Json)
            body = jsonData
        }.bodyAsText()
    }

    override suspend fun getTransactionsBooksDataSmartContract(name: String): String {
        return getLinkBodyAsTextCrypt("http://$SERVER_IP:80/$BASE_LINK/name/$name/getTransactions")
    }

    override suspend fun twoFactorTransaction(name: String, selectedValueBook: String, state: Boolean): String {
        TODO("Not yet implemented")
    }
}