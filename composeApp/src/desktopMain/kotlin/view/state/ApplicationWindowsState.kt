package view.state

import WindowsName
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Size

class ApplicationWindowsState() {
    val windows = mutableStateListOf<WindowState>()


    init {
        openLoginWindow()
    }


    //закрытие окна с последующим открытием нового
    //TODO переделать запуск окна не от константы
    fun openNewWindow() {
        if (WindowsName == "Main") openMainWindow()
        else if (WindowsName == "Registration") openRegistrationWindow()
        else if (WindowsName == "Login") openLoginWindow()

    }

    private fun openMainWindow() {
        windows.clear()
        windows += WindowState(
            "Main",
            ::openNewWindow,
            ::exit,
            windows::remove,
            "Main",
            size = Size(1000f, 300f)
        )

    }
    private fun openRegistrationWindow() {
        windows.clear()
        windows +=WindowState(
            "Registration",
            ::openNewWindow,
            ::exit,
            windows::remove,
            "Registration",
            size = Size(300f, 150f)
        )
    }

    private fun openLoginWindow() {
        windows.clear()
        windows += WindowState(
            "Login",
            ::openNewWindow,
            ::exit,
            windows::remove,
            "Login",
            size = Size(30f, 150f)
        )
    }


    fun exit() {
        windows.clear()
    }

    private fun WindowState(
        title: String
    ) = WindowState(
        title,
        openNewWindow = ::openNewWindow,
        exit = ::exit,
        windows::remove,
        name = title,
        size = Size(300f, 150f)
    )

}