package view.bottonNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import view.Screens

@Composable
fun NavGraph (
    navHostController: NavController//для переключения между экранами
) {
    //TODO: сделать один список для всех экранов
    //создаем список элементов нижней навигации, для перебирания экранов
    val listItems = listOf(
        BottomItem.Home,
        BottomItem.SmartContract,
        BottomItem.Profile
    )

    NavHost(navController = navHostController as NavHostController, startDestination = BottomItem.Home.route) {
        listItems.forEach { screen ->
            composable(screen.route) {
                when (screen) {
                    BottomItem.Home -> Screens().HomeScreen()
                    BottomItem.SmartContract -> Screens().SmartContract()
                    BottomItem.Profile -> Screens().ProfileScreen()
                }
            }
        }
    }
}