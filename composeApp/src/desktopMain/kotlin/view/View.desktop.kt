package view


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import view.state.WindowState

object AppState {
    var isLoggedIn by mutableStateOf(false)
}



@Composable
fun MainScreen(state: WindowState) {
    Main(state)
}

@Composable
fun LoginScreen(state: WindowState) {
    Login(state)
}

@Composable
fun MainScreenDesktop(state: WindowState) {
    return MainScreen(state)
}





