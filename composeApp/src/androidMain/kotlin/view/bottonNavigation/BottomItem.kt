 /*
    непосредственно иконки и названия для нижней навигации
 */
package view.bottonNavigation

import org.example.project.R

sealed class BottomItem(val route: String, val title: String, val icon: Int) {
    object Home : BottomItem("home", "Home", R.drawable.baseline_home_24)
    object SmartContract : BottomItem("smartContract", "Smart Contract", R.drawable.sharp_local_atm_24)
    object Profile : BottomItem("profile", "Profile", R.drawable.baseline_account_box_24)

}