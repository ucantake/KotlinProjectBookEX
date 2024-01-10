 /*
    непосредственно иконки и названия для нижней навигации
 */
package view.navigation

sealed class Item(val route: String, val title: String) {

    object Home : Item("home", "Home")
    object SmartContract : Item("smartContract", "Smart Contracts")
    object Profile : Item("profile", "Profile")
}