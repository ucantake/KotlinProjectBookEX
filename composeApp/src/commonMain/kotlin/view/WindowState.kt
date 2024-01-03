package view

import androidx.compose.ui.geometry.Size


//окно приложения
class WindowState (
    var title: String,
    val openNewWindow: () -> Unit,
    val exit: () -> Unit,
    private val close: (WindowState) -> Unit,
    private val name : String,
    private val size: Size
) {
    fun close() = close(this)

    //TODO сделать проверку на неправильное имя окна - отобразить диалогове окно с ошибкой "неправильное имя окна"
    fun name() {
        if (name == "Login") {
            println("Login")
        } else {
            println("Main")
        }
    }

    fun getSize() = size
}
