package util

import DATADOWNLOADING
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

    //TODO переделать блок для офлайн доступа предыдущего входа пользователя
    if (username == "ASD" && password == "DSA") {
        NAMEUSER = username
        return true
    }

    val httpsClietn = GetHttpApiClient()

    val stri = httpsClietn.authLinkForman(username, password)
    val hash = tokenCreate(username+password)
    println(stri + " " + hash)
    if (stri == hash) {
        //присваиваются данные константы для дальнейшего использования
        NAMEUSER = username
        PASSWORDUSER = hash //токен пользователя, который генерируется на основе логина и пароля

        return true
    }else {
        return false
    }

//    return (textFieldStateEmail == "admin" && textFieldStatePassword == "password")
}

//генерируется токен (строка) на основе строки (логин и пароль вместе)
fun tokenCreate (string : String) : String {
    val hashNamePassword = ""+string+"" //TODO придумать более сложную генерацию токена
    return hashNamePassword.hashCode().toString()
}

suspend fun createUser (name : String, password : String, email : String, account : String, key : String) : Boolean {
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
}

suspend fun addBook ( name : String, title : String, author : String, isbn : String, udc : String, bbk : String, price : String) : Boolean {
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
}