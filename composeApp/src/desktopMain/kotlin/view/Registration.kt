package view

import WindowsName
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repository.DownloadJsonData
import util.checkLoginUser
import view.state.WindowState


@Composable
fun Registration (state: WindowState){
    val scaffoldState = rememberScaffoldState()// для отображения левой панели

    var email by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var password2 by remember {
        mutableStateOf("")
    }

    //для отображения пароля
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    //для потоков
    val scope = rememberCoroutineScope()

    //для меню загрузки
    var progress by remember { mutableStateOf(0.0f) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                Row() {
                    // Текстовое поле для ввода email
                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = username,
                        label = {
                            Text("Имя пользователя")
                        },

                        onValueChange = {
                            username = it
                        },

                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Текстовое поле для ввода пароля
                Row (
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = password,
                        visualTransformation = visualTransformation,
                        label = {
                            Text("Пароль")
                        },
                        onValueChange = {
                            password = it
                        },

                        modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth(0.9f)


                    )
                    IconButton(
                        modifier = Modifier.fillMaxWidth(1f),
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        val icon = if (passwordVisible) Icons.Filled.Edit else Icons.Filled.Lock
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))


                // Текстовое поле для ввода пароля
                Row (
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = password2,
                        visualTransformation = visualTransformation,
                        label = {
                            Text("Пароль")
                        },
                        onValueChange = {
                            password2 = it
                        },

                        modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth(0.9f)


                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка регистрации
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                    shape = RoundedCornerShape(size = 20.dp),//закругление углов
                    elevation = ButtonDefaults.elevation(//отображение тени
                        defaultElevation = 20.dp
                    ),
                    onClick = {
                        state.title = "Login"
                        WindowsName = "Login"
                        state.openNewWindow()
                    }
                ) {
                    Text("Зарегистрироваться",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black)
                }

                //отображение меню загрузки
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
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