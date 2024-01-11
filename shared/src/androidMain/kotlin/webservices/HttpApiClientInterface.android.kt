package webservices

import BASE_LINK_GET
import PASSWORDUSER
import SERVER_IP
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

actual class HttpApiClient actual constructor() : HttpApiClientInterface {
    val client = HttpClient(CIO) {
        engine {
//            https {
//                serverName = SERVER_IP
//                trustManager = getSslSettings().getTrustManager() as TrustManager?
//            }
        }
    }

    override suspend fun authLinkForman(name: String, password: String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK_GET/name/$name&password/$password").bodyAsText()
    }

    override suspend fun getDataProfile(name: String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK_GET/name/$name&token/$PASSWORDUSER/profile").bodyAsText()
    }

    override suspend fun createUser(name: String, password: String, email: String, account : String, key : String): String {
        return client.get("http://$SERVER_IP:80/$BASE_LINK_GET/registration/name/$name/password/$password/email/$email/$account/$key").bodyAsText()
    }
}