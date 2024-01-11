import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import view.LoginScreen
import view.MainScreen
import view.RegistrationScreen
import view.state.ApplicationWindowsState
import view.state.WindowState

/*
TODO сделать передачу состояния окна:
  указывать размеры для каждого окна,
  минимальный размер,
  возможность развернуть MainScreen на полный экран,
  иконку окна
  для каждого окна, свой размер
*/
fun main() = application {
    WindowsName = "Login"
    val applicationWindowsState = remember { ApplicationWindowsState() }

    for (window in applicationWindowsState.windows) {
        key(window) {
            MyWindow(window)
        }
    }
}

@Composable
private fun ApplicationScope.MyWindow(
    state: WindowState
) = Window(
    onCloseRequest = state::close,
    title = state.title,
    icon = painterResource("icons/books-svgrepo-com.svg"),
    undecorated = true,

) {
    //вот тут переключается окно, перезаписью
    if (state.title == "Login") {
        LoginScreen (state)
    } else if (state.title == "Main") {
        MainScreen(state)
    }else if (state.title == "Registration") {
        RegistrationScreen(state)
    }
}