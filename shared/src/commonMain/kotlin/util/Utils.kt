package util

import DATADOWNLOADING
import LOCALACCESSDATA
import LOCALACCESS
import NAMEUSER
import PASSWORDUSER
import WORKMODE
import webservices.GetHttpApiClient


//проверка на введенный логин и пароль
/*
* проверяется введенный логин и пароль с отправкой данных на сервер
* возвращает true если пользователь ввел правильный логин и пароль
 */
//TODO добавить проверку пароля

suspend fun checkLoginUser(username: String, password : String): Boolean {

    try {
        val httpsClietn = GetHttpApiClient()
        val stri = httpsClietn.authLinkForman(username, password)
        val hash = tokenCreate(username+password)
        println(stri + " " + hash)
        if (stri == hash) {
            //присваиваются данные константы для дальнейшего использования
            NAMEUSER = username
            PASSWORDUSER = hash //токен пользователя, который генерируется на основе логина и пароля
            WORKMODE = "online"
            return true
        }else {
            return false
        }
    }catch (e : Exception) {
        println("EXEPCTION in checkLoginUser in Utils.kt " + e.message)
        WORKMODE = "offline"
        if (username == LOCALACCESSDATA && password == LOCALACCESSDATA && LOCALACCESS == true) { // локальный доступ для тестирования
            NAMEUSER = username
            PASSWORDUSER = tokenCreate(username+password)

            return true
        }else return false
    }
}

//генерируется токен (строка) на основе строки (логин и пароль вместе)
fun tokenCreate (string : String) : String {
    val hashNamePassword = ""+string+"" //TODO придумать более сложную генерацию токена
    return hashNamePassword.hashCode().toString()
}

/*
* функция создания пользователей, возвращает true если пользователь создан
* в качестве обработки ошибок возвращает false
 */
suspend fun createUser (name : String, password : String, email : String, account : String, key : String) : Boolean {
    try {
        val httpsClietn = GetHttpApiClient()
        val stri = httpsClietn.createUser(name, password, email, account, key)
        val hash = tokenCreate(name).toString()
        println("hash = $hash stri= $stri")
        if (stri == hash) {
            DATADOWNLOADING = true
            return true
        }else {
            return false
        }
    }catch (e : Exception){
        println("exeption in createUser in Utils.kt = ${e.message}")
        return false
    }
}

suspend fun addBook ( name : String, title : String, author : String, isbn : String, udc : String, bbk : String, price : String) : Boolean {
    try {
        val httpsClietn = GetHttpApiClient()
        val stri = httpsClietn.addBook(name, title, author, isbn, udc, bbk, price)
        val hash = tokenCreate(name + title)
        println("hash = $hash stri= $stri")
        if (stri == hash) {
            DATADOWNLOADING = true
            return true
        }else {
            return false
        }
    }catch (e : Exception){
        println("exeption in addBook in Utils.kt = ${e.message}")
        return false
    }

}