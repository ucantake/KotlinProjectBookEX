package view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import util.checkLoginUser
import view.state.WindowState

@Composable
fun Login(state: WindowState){
    val scaffoldState = rememberScaffoldState()// для отображения левой панели
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    //для отображения нажатия кнопки Login
    var enabled by remember {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {

                // Текстовое поле для ввода email
                TextField(
                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                    value = username,
                    label = {
                        Text("Username")
                    },

                    onValueChange = {
                        username = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(16.dp))

                // Текстовое поле для ввода пароля
                TextField(
                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                    value = password,
                    label = {
                        Text("Password")
                    },
                    onValueChange = {
                        password = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(16.dp))
                // Кнопка входа
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                    shape = RoundedCornerShape(size = 20.dp),//закругление углов
                    elevation = ButtonDefaults.elevation(//отображение тени
                        defaultElevation = 20.dp
                    ),
                    enabled = username.isNotEmpty() && password.isNotEmpty(), //если поля пустые то кнопка не активна
                    onClick = {
                        enabled = false
                        scope.launch {
                            //проверка имени и пароля
                            if (checkLoginUser(username, password)) {//корректный ввод, отображение нового окна (авторизация)
                                scaffoldState.snackbarHostState.showSnackbar("Добро пожаловать $username")
                                state.title = "Main"
                                state.openNewWindow()
                            }
                            else {
                                scaffoldState.snackbarHostState.showSnackbar("Не верный логин или пароль")//отображение ошибки
                                enabled = true

                            }
                            AppState.isLoggedIn = true

                        }

                    }
                ) {
                    Text("Войти",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black)
                }
            }
        },
        //нижний элемент Scaffold
        bottomBar = {
            bottomAppBar(state)
        },
        //верхний элемент Scaffold
        topBar = {
            topAppBar(state, scaffoldState)
        }
    )
}

//уменьшение размера вылетающей левой панели через контуры фигуры
//TODO разоабраться с размерами выпадающего окна (половина размера экрана в ширину)
fun customShape(size : Size) =  object : Shape {
    val width = size.width * 20
    val height = size.height * 2.5f
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f,0f,height /* ширина */, width /* длина */))
    }
}


data class TextFieldFocusState(
    val usernameFocusRequester: FocusRequester = FocusRequester(),
    val passwordFocusRequester: FocusRequester = FocusRequester(),
    val usernameIsFocused: Boolean = false
)