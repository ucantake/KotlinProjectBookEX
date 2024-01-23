package view

import DATADOWNLOADING
import WindowsName
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repository.DownloadJsonData
import util.checkLoginUser
import util.createUser
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

    var account by remember {
        mutableStateOf("")
    }
    var key by remember {
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
                    // Текстовое поле для ввода имени пользователя
                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = username,
                        label = {
                            Text("Имя пользователя")
                        },

                        onValueChange = {
                            username = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                Row() {
                    // Текстовое поле для ввода имени email
                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = email,
                        label = {
                            Text("Электронная почта")
                        },

                        onValueChange = {
                            email = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }
                Spacer(modifier = Modifier.height(1.dp))


                Row() {
                    // Текстовое поле для ввода аккаунта
                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = account,
                        label = {
                            Text("Адрес ETH аккаунта")
                        },

                        onValueChange = {
                            account = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }
                Spacer(modifier = Modifier.height(1.dp))

                Row() {
                    // Текстовое поле для ввода имени email
                    TextField(
                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                        value = key,
                        label = {
                            Text("Приватный ключ от аккаунта ETH")
                        },

                        onValueChange = {
                            key = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }
                Spacer(modifier = Modifier.height(1.dp))

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
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
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
                Spacer(modifier = Modifier.height(1.dp))


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
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
                        modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth(0.9f)


                    )
                }
                Spacer(modifier = Modifier.height(1.dp))

                // Кнопка регистрации
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                    shape = RoundedCornerShape(size = 20.dp),//закругление углов
                    elevation = ButtonDefaults.elevation(//отображение тени
                        defaultElevation = 20.dp
                    ),
                    onClick = {
                        if (password != password2) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Пароли не совпадают")
                            }
                        } else if (password == "" || password2 == "" || username == "" || email == "" || account == "" || key == "") {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Заполните все поля")
                            }
                        } else if (account.length != 42) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Неверный адрес аккаунта")
                            }
                        } else if (key.length != 66) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Неверный приватный ключ")
                            }
                        } else {
                            DATADOWNLOADING = false
                            scope.launch {
                                while (true) {
                                    val data = createUser(username, password, email, account, key)
                                    while (progress < 1f) {
                                        progress += 0.1f
                                        delay(1000L)
                                        if (data) break
                                    }
                                    println("DATA = $data")

                                    if (!data) progress = 0f
                                    if (data == false) {
                                        scaffoldState.snackbarHostState.showSnackbar("Ошибка регистрации")
                                        break
                                    }else if (data == true) {
                                        scaffoldState.snackbarHostState.showSnackbar("Пользователь успешно зарегистрирован")
                                        DATADOWNLOADING = true

                                        state.title = "Login"
                                        WindowsName = "Login"
                                        state.openNewWindow()

                                        break
                                    }
                                }
                            }
                        }
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