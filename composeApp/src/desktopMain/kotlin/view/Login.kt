package view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import util.checkLoginUser

@Composable
fun Login(state: WindowState){
    val scaffoldState = rememberScaffoldState()
    var textFieldStateEmail by remember {
        mutableStateOf("")
    }
    var textFieldStatePassword by remember {
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
        drawerContent={
            Text("Авторизируйтесь для отображения пунктов меню", fontSize = 28.sp)
        },
        drawerShape = customShape(),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                // Текстовое поле для ввода email
                TextField(
                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                    value = textFieldStateEmail,
                    label = {
                        Text("Username")
                    },
                    onValueChange = {
                        textFieldStateEmail = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Текстовое поле для ввода пароля
                TextField(
                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                    value = textFieldStatePassword,
                    label = {
                        Text("Password")
                    },
                    onValueChange = {
                        textFieldStatePassword = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Кнопка входа
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                    shape = RoundedCornerShape(size = 20.dp),//закругление углов
                    elevation = ButtonDefaults.elevation(//отображение тени
                        defaultElevation = 20.dp
                    ),
//                    enabled = enabled,//отображение нажатия
                    onClick = {
                        enabled = false
                        scope.launch {
                            //проверка имени и пароля
                            if (checkLoginUser(textFieldStateEmail, textFieldStatePassword)) {//корректный ввод, отображение нового окна (авторизация)
                                scaffoldState.snackbarHostState.showSnackbar("Добро пожаловать $textFieldStateEmail")
                                state.title = "Main"
                                state.openNewWindow()
                            }
                            else {
                                scaffoldState.snackbarHostState.showSnackbar("не верный логин или пароль")//отображение ошибки
                                enabled = true

                            }
                            AppState.isLoggedIn = true

                        }

                    }
                ) {
                    Text("Login",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,)
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
fun customShape() =  object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f,0f,15f /* ширина */, 1000f /* длина */))
    }
}