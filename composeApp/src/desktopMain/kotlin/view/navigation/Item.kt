 /*
    непосредственно иконки и названия для нижней навигации
 */
package view.navigation

sealed class Item(val route: String, val title: String) {

    object Home : Item("home", "Домашняя")
    object SmartContract : Item("smartContract", "Обмен")
    object Profile : Item("profile", "Профиль")
}