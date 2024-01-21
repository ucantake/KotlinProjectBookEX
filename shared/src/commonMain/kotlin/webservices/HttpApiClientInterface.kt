package webservices

interface HttpApiClientInterface {
    //отправка данных авторизации
    suspend fun authLinkForman(name: String, password: String): String

    //отправка данных для страницы Profile
    suspend fun getDataProfile(name : String): String

    suspend fun createUser (name : String, password : String, email : String, account : String, key : String) : String

    suspend fun  addBook ( name : String, title : String, author : String, isbn : String, udc : String, bbk : String, price : String) : String

    suspend fun getJsonBooks (name : String) : String

    suspend fun getJsonBooksUsers (name : String) : String

    suspend fun setSmartContract (name: String, selectedValueUser: String, selectedValueBook: String, price: String, comment: String) : String

    suspend fun getBooksDataSmartContract (name: String) : String
}

expect class HttpApiClient() : HttpApiClientInterface


fun GetHttpApiClient(): HttpApiClientInterface = HttpApiClient()