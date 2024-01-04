 /*
    непосредственно иконки и названия для нижней навигации
 */
package view.navigation

sealed class Item(val route: String, val title: String) {

    object Home : Item("home", "Home")
    object Search : Item("search", "Search")
    object Profile : Item("profile", "Profile")
}