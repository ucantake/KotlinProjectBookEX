package repository

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import EMAIL
import JSON
import JSON_PROFILE
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
            println("JSON_PROFILE = ${JSON_PROFILE}")

            EMAIL = json.jsonObject["user"]?.jsonObject?.get("email")?.jsonPrimitive?.contentOrNull.toString()
            ACCOUNT = json.jsonObject["wallet"]?.jsonObject?.get("account")?.jsonPrimitive?.contentOrNull.toString()
            BALANCE = json.jsonObject["balance"]?.jsonObject?.get("balanceEth")?.jsonPrimitive?.contentOrNull.toString()
            ROLE = json.jsonObject["user"]?.jsonObject?.get("role")?.jsonPrimitive?.contentOrNull.toString()

            JSON = json.toString()

            DATADOWNLOADING = true
            println(JSON)
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
    CoroutineScope(Dispatchers.IO).async { DownloadJsonData() }
}

//TODO доделать получение списка пользователей с сервера
fun downloadUsers() : List<String>{
    val httpsClietn = GetHttpApiClient()


    val data = CoroutineScope(Dispatchers.Default).async {

    }

    return listOf("")

}

//TODO доделать получение списка книг с сервера с указанием конкретного пользователя
fun downloadBooks() : List<String>{
    val httpsClietn = GetHttpApiClient()

    val data = CoroutineScope(Dispatchers.Default).async {

    }

    return listOf("")

}