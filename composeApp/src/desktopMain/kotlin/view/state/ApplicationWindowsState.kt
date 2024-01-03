package view.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Size

class ApplicationWindowsState() {
    val windows = mutableStateListOf<WindowState>()


    init {
        windows += WindowState(
            "Login",
            ::openNewWindow,
            ::exit,
            windows::remove,
            "Login",
            size = Size(30f, 150f)
        )
    }


    //закрытие окна с последующим открытием нового
    fun openNewWindow() {
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