package org.example.project

import App
import WindowsName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import view.LoginScreen
import view.MainScreen
import view.RegistrationScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()//для переключения между экранами
            NavHost(
                navController = navController,
                startDestination = "login"//начальный экран
            ) {
                composable("login") {//создание экрана
                    LoginScreen() {
                        if (WindowsName == "Main") {
                            navController.navigate("main")
                        } else if (WindowsName == "Registration") {
                            navController.navigate("registration")
                        } else {
                            navController.navigate("login")
                        }
                    }//вызов функции для отображения экрана входа
                }
                //TODO: подумать над стеком, чтобы при нажатии кнопки "назад" выходить из приложения полностью
                composable("main") {//создание экрана
                    MainScreen(){}//вызов функции для отображения главного экрана приложения
                }
                composable("registration") {//переключение обратно на экран входа, с возможность заново вызвать окно регистрации
                    RegistrationScreen{
                        navController.navigate("login")
                    }
                }


            }

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}