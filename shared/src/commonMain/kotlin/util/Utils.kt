package util

import NAMEUSER
import PASSWORDUSER
import webservices.GetHttpApiClient


//проверка на введенный логин и пароль
/*
* проверяется введенный логин и пароль с отправкой данных на сервер
* возвращает true если пользователь ввел правильный логин и пароль
 */
//TODO добавить проверку пароля

suspend fun checkLoginUser(username: String, password : String): Boolean {
    val httpsClietn = GetHttpApiClient()

    val stri = httpsClietn.authLinkForman(username, password)
    val hash = tokenCreate(username, password)
    if (stri.toInt() == hash) {
        //присваиваются данные константы для дальнейшего использования
        NAMEUSER = username
        PASSWORDUSER = hash.toString() //токен пользователя, который генерируется на основе логина и пароля
        return true
    }else {
        return false
    }

//    return (textFieldStateEmail == "admin" && textFieldStatePassword == "password")
}

//генерируется токен (число) на основе логина и пароля
fun tokenCreate (name : String, password : String) : Int {
    val hashNamePassword = ""+name+password+"" //TODO придумать более сложную генерацию токена
    return hashNamePassword.hashCode()
}

//генерируется токен (строка) на основе строки (логин и пароль вместе)
fun tokenCreate (string : String) : String {
    val hashNamePassword = ""+string+"" //TODO придумать более сложную генерацию токена
    return hashNamePassword.hashCode().toString()
}
