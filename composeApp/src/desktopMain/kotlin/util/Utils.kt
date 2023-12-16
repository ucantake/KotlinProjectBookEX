package util

//проверка на введенный логин и пароль
//TODO добавить проверку пароля
//TODO сделать отправку на сервер
fun checkLoginUser(textFieldStateEmail: String): Boolean {
    return (textFieldStateEmail == "admin")
}