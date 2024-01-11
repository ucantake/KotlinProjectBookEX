package webservices

interface HttpApiClientInterface {
    //отправка данных авторизации
    suspend fun authLinkForman(name: String = "def", password: String = "ault"): String

    //отправка данных для страницы Profile
    suspend fun getDataProfile(name : String): String

    suspend fun createUser (name : String, password : String, email : String, account : String, key : String) : String
}

expect class HttpApiClient() : HttpApiClientInterface


fun GetHttpApiClient(): HttpApiClientInterface = HttpApiClient()