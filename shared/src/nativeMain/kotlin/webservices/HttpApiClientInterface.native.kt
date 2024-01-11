package webservices

actual class HttpApiClient actual constructor() : HttpApiClientInterface {
    override suspend fun authLinkForman(name: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getDataProfile(name: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(name: String, password: String, email: String, account : String, key : String): String {
        TODO("Not yet implemented")
    }
}