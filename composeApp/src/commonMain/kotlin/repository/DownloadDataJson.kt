package repository

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import DOWNLOAD_DATA_ALL
import EMAIL
import HASH_DATA_DOWNLOAD
import JSON
import JSON_PROFILE
import JSON_SEARCH_USERS_BOOKS
import JSON_TRANSACTION_BOOKS
import NAMEUSER
import ROLE
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import util.md5
import webservices.GetHttpApiClient


//TODO проверять данные на изменение и если они изменились то обновлять их
//TODO сделать запуск из файла Utils функции checkLoginUser
suspend private fun DownloadJsonData () {
    try {
            val httpsClietn = GetHttpApiClient()
            var json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER)))

            JSON_PROFILE = httpsClietn.getJsonBooks(NAMEUSER)


            EMAIL = json.jsonObject["user"]?.jsonObject?.get("email")?.jsonPrimitive?.contentOrNull.toString()
            ACCOUNT = json.jsonObject["wallet"]?.jsonObject?.get("account")?.jsonPrimitive?.contentOrNull.toString()
            BALANCE = json.jsonObject["balance"]?.jsonObject?.get("balanceEth")?.jsonPrimitive?.contentOrNull.toString()
            ROLE = json.jsonObject["user"]?.jsonObject?.get("role")?.jsonPrimitive?.contentOrNull.toString()

            JSON = json.toString()
            DATADOWNLOADING = true

    }catch (e : Exception) {
        println("EXEPCTION in DownloadjsonData " + e.message)
        EMAIL = ""
        ACCOUNT = ""
        BALANCE = ""
        ROLE = "user"

        JSON = "{}"
    }
}

//функция для синхронизации данных с сервера
fun SynchronizedJsonData (){
    DOWNLOAD_DATA_ALL = false
    println("HASHDATA = $HASH_DATA_DOWNLOAD")

    //первый запуск программы, загрузка всех данных
    if (HASH_DATA_DOWNLOAD == "") {
        GlobalScope.launch {
            DownloadJsonData()
            searchBooksUser(NAMEUSER)
            bookDataSmartContract(NAMEUSER)
            HASH_DATA_DOWNLOAD = md5(JSON+JSON_PROFILE)
        }

    } else {//последующие запуски

        if (HASH_DATA_DOWNLOAD != md5(JSON+JSON_PROFILE)) {//запуск только в случае изменения данных на сервере
            runBlocking {
                async {
                    DownloadJsonData()
                    searchBooksUser(NAMEUSER)
                    bookDataSmartContract(NAMEUSER)
                    HASH_DATA_DOWNLOAD = md5(JSON + JSON_PROFILE)
                }
            }
        }else{//запуск изменения данных для формирования сигнатуры данных
            GlobalScope.launch {
                DownloadJsonData()
            }
        }
    }
    DOWNLOAD_DATA_ALL = true
}

suspend private fun searchBooksUser(name : String) {
    try {
            val httpsClietn = GetHttpApiClient()
            JSON_SEARCH_USERS_BOOKS = httpsClietn.getJsonBooksUsers(name)
    }catch (e : Exception){
        println("exeption in searchBooksUser in Utils.kt = ${e.message}")
    }

}

suspend private fun bookDataSmartContract (name: String) {
    try {
            val httpsClietn = GetHttpApiClient()
            JSON_TRANSACTION_BOOKS = httpsClietn.getBooksDataSmartContract(name)
    }catch (e : Exception){
        println("exeption in bookDataSmartContract in Utils.kt = ${e.message}")
    }
}