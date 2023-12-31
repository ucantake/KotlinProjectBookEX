package webservices

interface HttpApiClientInterface {
    suspend fun authLinkForman(name: String = "def", password: String = "ault"): String
}

expect class HttpApiClient() : HttpApiClientInterface


fun GetHttpApiClient(): HttpApiClientInterface = HttpApiClient()