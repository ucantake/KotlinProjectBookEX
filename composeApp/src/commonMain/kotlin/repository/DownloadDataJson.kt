package repository

import ACCOUNT
import BALANCE
import EMAIL
import JSON
import NAMEUSER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.JsonData
import webservices.GetHttpApiClient

//TODO при переключении окон использовать эту функцию (или переделать на класс) для работы с данными
fun DownloadJsonData () {

    val job = CoroutineScope(Dispatchers.Default).launch {
        val httpsClietn = GetHttpApiClient()
        var json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER)))

        EMAIL = json.jsonObject["user"]?.jsonObject?.get("email")?.jsonPrimitive?.contentOrNull.toString()
        ACCOUNT = json.jsonObject["wallet"]?.jsonObject?.get("account")?.jsonPrimitive?.contentOrNull.toString()
        BALANCE = json.jsonObject["balance"]?.jsonObject?.get("balanceEth")?.jsonPrimitive?.contentOrNull.toString()

        JSON = json.toString()

        val js = Json.decodeFromString<JsonData>(JSON)
    }
}