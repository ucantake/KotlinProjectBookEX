package repository

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import EMAIL
import JSON
import NAMEUSER
import ROLE
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.JsonData
import webservices.GetHttpApiClient


//TODO проверять данные на изменение и если они изменились то обновлять их
//TODO сделать запуск из файла Utils функции checkLoginUser
fun DownloadJsonData () {

    val data = CoroutineScope(Dispatchers.Default).async {
        val httpsClietn = GetHttpApiClient()
        var json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER)))

        EMAIL = json.jsonObject["user"]?.jsonObject?.get("email")?.jsonPrimitive?.contentOrNull.toString()
        ACCOUNT = json.jsonObject["wallet"]?.jsonObject?.get("account")?.jsonPrimitive?.contentOrNull.toString()
        BALANCE = json.jsonObject["balance"]?.jsonObject?.get("balanceEth")?.jsonPrimitive?.contentOrNull.toString()
        ROLE = json.jsonObject["user"]?.jsonObject?.get("role")?.jsonPrimitive?.contentOrNull.toString()

        JSON = json.toString()

        DATADOWNLOADING = true
        println(JSON)
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