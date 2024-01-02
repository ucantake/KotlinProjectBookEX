package util

import webservices.GetHttpApiClient


//проверка на введенный логин и пароль
//TODO добавить проверку пароля
//TODO сделать отправку на сервер
suspend fun checkLoginUser(username: String, password : String): Boolean {
    val httpsClietn = GetHttpApiClient()

    val stri = httpsClietn.authLinkForman(username, password)
    val hashNamePassword = ""+username+password+""
    println(stri)
    if (stri.toInt() == hashNamePassword.hashCode()){
        return true
    }else {
        return false
    }

//    return (textFieldStateEmail == "admin" && textFieldStatePassword == "password")
}
