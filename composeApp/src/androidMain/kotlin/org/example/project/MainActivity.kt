package org.example.project

import App
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
                    LoginScreen{
                        navController.navigate("main")//переход на другой экран

                    }//вызов функции для отображения экрана входа в приложение
                }
                //TODO: подумать над стеком, чтобы при нажатии кнопки "назад" выходить из приложения полностью
                composable("main") {//создание экрана
                    MainScreen(){
                            //вариант закрытия стека
//                        navController.navigate("home")//переход на другой экран
//                        {
//                            popUpTo("home")//удаление из стека экранов
//                            {
//                                inclusive = true//удаление всех экранов
//
//                            }
//                        }
                    }//вызов функции для отображения главного экрана приложения

                }
                //для варианта с закрытием стека экранов
//                composable("home") {//создание экрана
//                    val screens = Screens()
//                    screens.HomeScreen()//вызов функции для отображения главного экрана приложения
//                }

            }

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}