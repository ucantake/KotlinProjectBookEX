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
    val passwordCrupt = md5(password)
    println(passwordCrupt)
    try {
        val httpsClietn = GetHttpApiClient()
        val stri = httpsClietn.authLinkForman(username, md5(password))
        val hash = tokenCreate(username+md5(password))
        if (stri == hash) {
            //присваиваются данные константы для дальнейшего использования
            NAMEUSER = username
            PASSWORDUSER = hash //токен пользователя, который генерируется на основе логина и пароля
            WORKMODE = "online"
            return true
        }else {
            WORKMODE = "online"
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
        val stri = httpsClietn.createUser(name, md5(password), email, account, key)
        val hash = tokenCreate(name+md5(password))
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

suspend fun setSmartContract (name : String, selectedValueUser: String, selectedValueBook: String, price: String, comment : String) : Boolean {
    try {
        val httpsClietn = GetHttpApiClient()
        val stri = httpsClietn.setSmartContract(name, selectedValueUser, selectedValueBook, price, comment)
        val hash = tokenCreate(name + PASSWORDUSER)

        if (stri == hash) {
            DATADOWNLOADING = true
            return true
        }else {
            return false
        }
    }catch (e : Exception){
        println("exeption in setSmartContract in Utils.kt = ${e.message}")
        return false
    }
}

suspend fun getBookDataSmartContract (name : String) : String {
    var stri = ""
    try {
        val httpsClietn = GetHttpApiClient()
        val stri = httpsClietn.getBooksDataSmartContract(name)


    }catch (e : Exception){
        println("exeption in getBookDataSmartContract in Utils.kt = ${e.message}")
        return "0"
    }finally {
        return stri
    }
}

fun hexToString(hexString: String): String {
    val result = StringBuilder()

    for (i in 0 until hexString.length step 2) {
        val hex = hexString.substring(i, i + 2)
        val decimal = hex.toInt(16)
        result.append(decimal.toChar())
    }

    return result.toString()
}


fun stringToHex(input: String): String {
    val hexChars = "0123456789ABCDEF"
    val result = StringBuilder()

    for (char in input.toCharArray()) {
        val highNibble = char.toInt() ushr 4 and 0xF
        val lowNibble = char.toInt() and 0xF

        result.append(hexChars[highNibble])
        result.append(hexChars[lowNibble])
    }

    return result.toString()
}

fun md5(input: String): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    val byteArray = md.digest(input.toByteArray())

    // Преобразование байтов в шестнадцатеричную строку
    val result = StringBuilder()
    for (byte in byteArray) {
        result.append(String.format("%02x", byte))
    }

    return result.toString()
}