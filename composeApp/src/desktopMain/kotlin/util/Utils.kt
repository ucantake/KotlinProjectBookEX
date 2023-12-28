package util

import kotlinx.coroutines.runBlocking
import webservices.HttpApiClient


//проверка на введенный логин и пароль
//TODO добавить проверку пароля
//TODO сделать отправку на сервер
suspend fun checkLoginUser(textFieldStateEmail: String, textFieldStatePassword : String): Boolean {
    val httpsClietn = HttpApiClient()

    val stri = httpsClietn.authLinkForman(textFieldStateEmail, textFieldStatePassword)
    println(stri)
    if (stri == "true"){
        return true
    }else {
        return false
    }

//    return (textFieldStateEmail == "admin" && textFieldStatePassword == "password")
}
