 /*
    непосредственно иконки и названия для нижней навигации
 */
package view.navigation

sealed class BottomItem(val route: String, val title: String) {
    object Home : BottomItem("home", "Home")
    object Search : BottomItem("search", "Search")
    object Profile : BottomItem("profile", "Profile")

}