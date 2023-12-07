/*
   В этом файле мы определяем функции для отображения экранов приложения

*/
package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.example.project.R

 /*
    LoginScreen() - функция для отображения экрана входа в приложение
    TODO: спрятать поле пароля
    TODO: добавить кнопку "Забыли пароль?"
    TODO: добавить кнопку "Регистрация"
    TODO: ?добавить кнопку "Вход через Google"?
    TODO: добавить логотип приложения вверху полей ввода
 */
@Composable
actual fun LoginScreen(){
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(id = R.string.username)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onLoginClicked(username, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}

/*
    MainScreen() - функция для отображения главного экрана приложения

 */
@Composable
actual fun MainScreen() {
    TODO("Not yet implemented")
}

//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen(onLoginClicked = {})
//}

/*
    Функция для обработки нажатия на кнопку "Войти"
    TODO: добавить обработку ошибок
    TODO: добавить обработку
 */
fun onLoginClicked(username: String, password: String) {
    // Выполнить вход пользователя с использованием введенного им логина и пароля
    // Например, отправить данные на сервер для проверки или выполнить проверку локально

    if (username.isNotEmpty() && password.isNotEmpty()) {
        // Допустим, в этом месте мы отправляем запрос на сервер для проверки данных пользователя
        // Если данные верны, можно перейти на следующий экран или выполнить другие действия
        // Иначе показать сообщение об ошибке
    } else {
        // Показать сообщение об ошибке пользователю о пустых полях
    }
}
