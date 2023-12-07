 /*
    непосредственно иконки и названия для нижней навигации
 */
package view.bottonNavigation

import org.example.project.R

sealed class BottomItem(val route: String, val title: String, val icon: Int) {
    object Home : BottomItem("home", "Home", R.drawable.baseline_home_24)
    object Search : BottomItem("search", "Search", R.drawable.baseline_saved_search_24)
    object Profile : BottomItem("profile", "Profile", R.drawable.baseline_account_box_24)

}