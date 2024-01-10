package view.bottonNavigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation (
    navController: NavController//для переключения между экранами
) {
    //TODO сделать один список для всех экранов
    //создаем список элементов нижней навигации, для перебирания экранов
    val listItems = listOf(
        BottomItem.Home,
        BottomItem.SmartContract,
        BottomItem.Profile
    )
    androidx.compose.material.BottomNavigation(//создает пустую панель вокруг кнопок внизу
        backgroundColor = androidx.compose.ui.graphics.Color.White

    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()//получение текущего экрана
        val currentRoute = navBackStackEntry?.destination?.route//получение текущего открытого маршрута (экрана)
        listItems.forEach { item ->//создание с помощью цикла кнопок нижней навигации
            BottomNavigationItem(//каждый раз создаем новую кнопку
                selected = currentRoute == item.route,
                onClick = { //при нажатии на кнопку
                            navController.navigate(item.route) {//переход на другой экран

                            }
                          },
                icon = { //иконка
                    androidx.compose.material.Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { //название
                    androidx.compose.material.Text(text = item.title, fontSize = 9.sp)
                },
                selectedContentColor = androidx.compose.ui.graphics.Color.Black, //цвет выбранного элемента
                unselectedContentColor = androidx.compose.ui.graphics.Color.Gray //цвет не выбранного элемента
            )
        }
    }

}