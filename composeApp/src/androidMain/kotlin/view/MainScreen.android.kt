package view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import view.bottonNavigation.BottomNavigation
import view.bottonNavigation.NavGraph

/*
    MainScreen() - функция для отображения главного экрана приложения

 */
@Composable
fun MainScreen(function: () -> Unit) {
    val navController = androidx.navigation.compose.rememberNavController()
    Scaffold (
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    )
    {
        PaddingValues(  //добавляет отступы
            bottom = it.calculateBottomPadding()
        )
        NavGraph(navHostController = navController)
    }

}