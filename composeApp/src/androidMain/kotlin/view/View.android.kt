/*
   В этом файле мы определяем функции для отображения экранов приложения

*/
package view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.checkLoginUser
import view.bottonNavigation.NavGraph

/*
   LoginScreen() - функция для отображения экрана входа в приложение
   TODO: спрятать поле пароля
   TODO: добавить кнопку "Забыли пароль?"
   TODO: добавить кнопку "Регистрация"
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


    Scaffold (
        scaffoldState = scaffoldState,
        content = {
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = androidx.compose.ui.Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                Column (
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row () {
                        // Текстовое поле для ввода email
                        TextField(
                            value = username,
                            label = {
                                Text("Username")
                            },
                            onValueChange = {
                                username = it
                            }
                        )
                    }

                    Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                    // Текстовое поле для ввода пароля
                    Row(){
                        TextField(
                            value = password,
                            label = {
                                Text("Password")
                            },
                            onValueChange = {
                                password = it
                            },
                            visualTransformation = visualTransformation,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            )
                        )
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Icon(icon, contentDescription = "Toggle password visibility")
                        }

                    }
                }



                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

                // Кнопка для входа в приложение
                Button(
                    onClick = {
                        scope.launch {
                            if (checkLoginUser(username, password)){
                                scopeRemember.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Добро пожаловать $username")
                                    onLoginClicked()
                                }
                            }else {
                                scopeRemember.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Неверный логин или пароль")
                                }
                            }
                        }
                    },
                    enabled = username.isNotEmpty() && password.isNotEmpty(), //если поля пустые то кнопка не активна
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                    ) {
                        Text("Войти")
                    }

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

/*
    Функция для обработки нажатия на кнопку "Войти"
    TODO: добавить обработку ошибок
    TODO: добавить обработку
    TODO: вход по логину и паролю
 */
@Composable
fun onLoginClickedT() {
    MainScreen {  }
}