/*
   В этом файле мы определяем функции для отображения экранов приложения

*/
package view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import org.example.project.R
import view.bottonNavigation.NavGraph
import webservices.HttpApiClient

/*
   LoginScreen() - функция для отображения экрана входа в приложение
   TODO: спрятать поле пароля
   TODO: добавить кнопку "Забыли пароль?"
   TODO: добавить кнопку "Регистрация"
   TODO: ?добавить кнопку "Вход через Google"?
   TODO: добавить логотип приложения вверху полей ввода
*/
@Composable
fun LoginScreen(onLoginClicked: () -> Unit){
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = CoroutineScope(Dispatchers.IO)

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
                onClick = {
                    test()
                    onLoginClicked()
                },//место возникновения ошибки, место перехода на новый экран
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
    // Выполнить вход пользователя с использованием введенного им логина и пароля
    // Например, отправить данные на сервер для проверки или выполнить проверку локально

    MainScreen {  }
//    if (username.isNotEmpty() && password.isNotEmpty()) {
//        // Допустим, в этом месте мы отправляем запрос на сервер для проверки данных пользователя
//        // Если данные верны, можно перейти на следующий экран или выполнить другие действия
//        // Иначе показать сообщение об ошибке
//        MainScreen(context)
//    } else {
//        // Показать сообщение об ошибке пользователю о пустых полях
//    }
}


suspend fun checlLogib(name : String = "des", password : String = "al"  ): String {
    val client = HttpApiClient()
    return client.authLinkForman()
}

fun test () : String {
    val scope = CoroutineScope(Dispatchers.IO)
    val stri = "true"
    try {
        val def = scope.async{
            val httpsClietn = HttpApiClient()

            val stri = httpsClietn.authLinkForman("textFieldStateEmail", "textFieldStatePassword")
            println(stri)

            return@async stri
        }
        //контрукция которая на сильно закрывает поток, как только он закончит выполнение
        while (def.isActive){
            if (def.isActive) Thread.sleep(1)
            else def.cancel()
        }
    }catch (e : Exception){
        println("E "  + e)
    }

    return stri
}