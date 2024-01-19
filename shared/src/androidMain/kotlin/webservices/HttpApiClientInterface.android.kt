package webservices

import BASE_LINK_GET
import NAMEUSER
import PASSWORDUSER
import SERVER_IP
import com.google.gson.Gson
import crypto.EncryptionUtils
import io.ktor.client.*
import io.ktor.client.call.*
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

    override suspend fun authLinkForman(name: String, password: String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK_GET/name/$name&password/$password").bodyAsText()
    }

    override suspend fun getDataProfile(name: String): String {
        return getLinkBodyAsTextCrypt("http://$SERVER_IP:80/$BASE_LINK_GET/name/$name&token/$PASSWORDUSER/profile")
    }

    override suspend fun createUser(name: String, password: String, email: String, account : String, key : String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK_GET/registration/name/$name/password/$password/email/$email/$account/$key").bodyAsText()
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
        val gson = Gson()

        // Создаем объект с вашими данными
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
        val jsonData = gson.toJson(bookData)

        // Отправляем POST запрос с JSON в теле
        return client.post("http://$SERVER_IP:80/$BASE_LINK_GET/name/${NAMEUSER}/addBook") {
            contentType(ContentType.Application.Json)
            body = jsonData
        }.bodyAsText()
    }

    override suspend fun getJsonBooks(name: String): String {
        return getLinkBodyAsTextCrypt("http://$SERVER_IP:80/$BASE_LINK_GET/name/${name}/getBooks")
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