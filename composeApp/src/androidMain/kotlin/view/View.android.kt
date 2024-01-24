/*
   В этом файле мы определяем функции для отображения экранов приложения

*/
package view

import DATADOWNLOADING
import DOWNLOAD_DATA_ALL
import NAMEUSER
import WORKMODE
import WindowsName
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repository.SynchronizedJsonData
import util.checkLoginUser
import util.createUser
import view.bottonNavigation.NavGraph


private var screen = "Login"
/*
   LoginScreen() - функция для отображения экрана входа в приложение
   TODO: добавить кнопку "Забыли пароль?"
   TODO: ?добавить кнопку "Вход через Google"?
   TODO: добавить логотип приложения вверху полей ввода
*/
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(onLoginClicked: () -> Unit){
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = CoroutineScope(Dispatchers.IO)
    val scaffoldState = rememberScaffoldState()
    val scopeRemember = rememberCoroutineScope()

    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()


    //для меню загрузки
    var progress by remember { mutableStateOf(0.0f) }
    Scaffold (
        scaffoldState = scaffoldState,
        content = {
            Column( //для всего окна
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp)
            ) {
                Column ( //для строк ввода
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(1f),
                    ) {
                        // Текстовое поле для ввода email
                        TextField(
                            value = username,
                            label = {
                                Text("Имя пользователя")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            maxLines = 1,
                            onValueChange = {
                                username = it
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // Текстовое поле для ввода пароля
                    Row(){
                        TextField(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            value = password,
                            label = {
                                Text("Пароль")
                            },
                            onValueChange = {
                                password = it
                            },
                            maxLines = 1,
                            visualTransformation = visualTransformation,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Password
                            )
                        )
                        IconButton(
                            modifier = Modifier.fillMaxWidth(1f),
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Icon(icon, contentDescription = "Toggle password visibility")
                        }

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка для входа в приложение
                Button(
                    onClick = {
                        scope.launch {
                            val check = checkLoginUser(username, password)
                            if (check){
                                SynchronizedJsonData()
                                if (WORKMODE == "offline") scaffoldState.snackbarHostState.showSnackbar("Работа в оффлайн режиме")
                                while (!DATADOWNLOADING) {
                                    if (DATADOWNLOADING) break
                                    else {
                                        while (progress < 1f) {
                                            progress += 0.1f
                                            delay(1000L)
                                            println("DATADOWNLOADING = $DATADOWNLOADING + DOWNLOAD_DATA_ALL = $DOWNLOAD_DATA_ALL")
                                            if (DOWNLOAD_DATA_ALL && DATADOWNLOADING) break
                                            if (DATADOWNLOADING && !DOWNLOAD_DATA_ALL) {
                                                SynchronizedJsonData()
                                                scaffoldState.snackbarHostState.showSnackbar("Синхронизация данных")
                                                if (progress == 1f) progress = 0f
                                            }

                                        }
                                        if (!DATADOWNLOADING) progress = 0f
                                    }
                                }

                                scopeRemember.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Добро пожаловать $username")
                                    SynchronizedJsonData()
                                    WindowsName = "Main"
                                    onLoginClicked()
                                }
                            }else {
                                scopeRemember.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Неверный логин или пароль")
                                    if (WORKMODE == "offline") scaffoldState.snackbarHostState.showSnackbar("Работа в оффлайн режиме")
                                }
                            }
                        }
                    },
                    enabled = username.isNotEmpty() && password.isNotEmpty(), //если поля пустые то кнопка не активна
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                    ) {
                        Text("Войти")
                    }

                // Кнопка для регистрации
                Button(
                    onClick = {
                        WindowsName = "Registration"
                        onLoginClicked()
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                ) {
                    Text("Зарегистрироваться")
                }

                //отображение меню загрузки
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

            }
        }
    )
}

/*
    MainScreen() - функция для отображения главного экрана приложения

 */
@Composable
fun MainScreen(function: () -> Unit) {
    val navController = androidx.navigation.compose.rememberNavController()
    Scaffold (
        bottomBar = {
            view.bottonNavigation.BottomNavigation(navController = navController)
        }
    )
    {
        PaddingValues(  //добавляет отступы
            bottom = it.calculateBottomPadding()
        )
        NavGraph(navHostController = navController)
    }

}

//TODO написать функцию для регистрации
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(onLoginClicked: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var account by remember { mutableStateOf("") }
    var key by remember { mutableStateOf("") }
    val scope = CoroutineScope(Dispatchers.IO)
    val scaffoldState = rememberScaffoldState()
    val scopeRemember = rememberCoroutineScope()

    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    var progress by remember { mutableStateOf(0.0f) }

    Scaffold (
        scaffoldState = scaffoldState,
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Текстовое поле для ввода имени пользователя
                TextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = username,
                    label = {
                        Text("Имя пользователя")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    maxLines = 1,
                    onValueChange = {
                        username = it
                    }
                )

                // Текстовое поле для ввода email
                TextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = email,
                    label = {
                        Text("Электронная почта")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    maxLines = 1,
                    onValueChange = {
                        email = it
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Текстовое поле для ввода аккаунта
                TextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = account,
                    label = {
                        Text("Адрес ETH аккаунта")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    maxLines = 1,
                    onValueChange = {
                        account = it
                    }

                )

                // Текстовое поле для ввода имени email
                TextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = key,
                    label = {
                        Text("Приватный ключ от аккаунта ETH")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    maxLines = 1,
                    onValueChange = {
                        key = it
                    }

                )

                Spacer(modifier = Modifier.height(16.dp))
                // Текстовое поле для ввода пароля
                Row(
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        value = password,
                        label = {
                            Text("Пароль")
                        },
                        maxLines = 1,
                        onValueChange = {
                            password = it
                        },
                        visualTransformation = visualTransformation,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        )
                    )
                    IconButton(
                        modifier = Modifier.fillMaxWidth(1f),
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    // Текстовое поле для ввода пароля
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        value = password2,
                        label = {
                            Text("Пароль")
                        },
                        onValueChange = {
                            password2 = it
                        },
                        maxLines = 1,
                        visualTransformation = visualTransformation,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        )
                    )
                }

                Button(
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
                                while (!DATADOWNLOADING) {
                                    val data = createUser(username, password, email, account, key)
                                    while (progress < 1f) {
                                        progress += 0.1f
                                        delay(1000L)
                                        if (DATADOWNLOADING) break
                                    }
                                    println("DATA = $data")

                                    if (!data) progress = 0f
                                    if (data == false) {
                                        scaffoldState.snackbarHostState.showSnackbar("Ошибка регистрации")
                                        break
                                    } else if (data == true) {
                                        scaffoldState.snackbarHostState.showSnackbar("Пользователь успешно зарегистрирован")
                                        DATADOWNLOADING = true
                                        WindowsName = "Login"
                                        onLoginClicked()
                                    }
                                }
                            }
                        }

                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                ) {
                    Text("Зарегистрироваться")
                }

                //отображение меню загрузки
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    )
}