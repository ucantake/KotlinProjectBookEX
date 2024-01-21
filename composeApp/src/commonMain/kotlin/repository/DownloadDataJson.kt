package repository

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import DOWNLOAD_DATA_ALL
import EMAIL
import JSON
import JSON_PROFILE
import JSON_SEARCH_USERS_BOOKS
import JSON_TRANSACTION_BOOKS
import LOCALACCESS
import LOCALACCESSDATA
import NAMEUSER
import OFFLINEDATA
import PASSWORDUSER
import ROLE
import WORKMODE
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import model.BooksResponse
import model.JsonData
import util.tokenCreate
import webservices.GetHttpApiClient


//TODO проверять данные на изменение и если они изменились то обновлять их
//TODO сделать запуск из файла Utils функции checkLoginUser
fun DownloadJsonData () {
    try {
        if (NAMEUSER == LOCALACCESSDATA && LOCALACCESS == true) { // локальный доступ для тестирования
            EMAIL = ""
            ACCOUNT = "0x0000000000"
            BALANCE = ""
            ROLE = "user"
            WORKMODE = "offline"
            JSON = OFFLINEDATA
            DATADOWNLOADING = true

        }
        CoroutineScope(Dispatchers.Default).launch {
            val httpsClietn = GetHttpApiClient()
            var json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER)))

//            JSON_PROFILE = Json.decodeFromString<BooksResponse>(httpsClietn.getJsonBooks(NAMEUSER)).toString()
            JSON_PROFILE = httpsClietn.getJsonBooks(NAMEUSER)


            EMAIL = json.jsonObject["user"]?.jsonObject?.get("email")?.jsonPrimitive?.contentOrNull.toString()
            ACCOUNT = json.jsonObject["wallet"]?.jsonObject?.get("account")?.jsonPrimitive?.contentOrNull.toString()
            BALANCE = json.jsonObject["balance"]?.jsonObject?.get("balanceEth")?.jsonPrimitive?.contentOrNull.toString()
            ROLE = json.jsonObject["user"]?.jsonObject?.get("role")?.jsonPrimitive?.contentOrNull.toString()

            JSON = json.toString()

            DATADOWNLOADING = true

        }
    }catch (e : Exception) {
        println("EXEPCTION in DownloadjsonData " + e.message)
        EMAIL = ""
        ACCOUNT = ""
        BALANCE = ""
        ROLE = "user"
        println("name = $NAMEUSER password = $PASSWORDUSER localaccess = $LOCALACCESS")
        if (NAMEUSER == LOCALACCESSDATA && LOCALACCESS == true) { // локальный доступ для тестирования
            DATADOWNLOADING = true
        } else DATADOWNLOADING = false

        JSON = "{}"
    }
}

//функция для синхронизации данных с сервера
fun SynchronizedJsonData (){

    CoroutineScope(Dispatchers.IO).async {
        DownloadJsonData()
        searchBooksUser(NAMEUSER)
        bookDataSmartContract(NAMEUSER)
    }
    DOWNLOAD_DATA_ALL = true
}

fun searchBooksUser(name : String) {
    DATADOWNLOADING = false
    try {
        CoroutineScope(Dispatchers.IO).launch {

            val httpsClietn = GetHttpApiClient()
            JSON_SEARCH_USERS_BOOKS = httpsClietn.getJsonBooksUsers(name)
            DATADOWNLOADING = true

        }
    }catch (e : Exception){
        println("exeption in searchBooksUser in Utils.kt = ${e.message}")
    }

}

fun bookDataSmartContract (name: String) {
    DATADOWNLOADING = false
    try {
        CoroutineScope(Dispatchers.IO).launch {
            val httpsClietn = GetHttpApiClient()
            JSON_TRANSACTION_BOOKS = httpsClietn.getBooksDataSmartContract(name)
            println("JSON_TRANSACTION_BOOKS = ${JSON_TRANSACTION_BOOKS}")
            DATADOWNLOADING = true
        }
    }catch (e : Exception){
        println("exeption in bookDataSmartContract in Utils.kt = ${e.message}")
    }
}