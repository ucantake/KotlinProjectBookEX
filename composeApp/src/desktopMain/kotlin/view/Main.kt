package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import view.navigation.Item
import view.state.WindowState

class MainScreen() {
    private val listItems = listOf(
    Item.Home,
    Item.Search,
    Item.Profile
    )

    private var currentItem: Item by mutableStateOf(Item.Home)


    @Composable
    fun drawableMenuLeft(onItemClick: (Item) -> Unit) {
        val scaffoldState = rememberScaffoldState()

        Image(
            painter = painterResource("icons/books-svgrepo-com.svg"),
            contentDescription = "Icons",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        )
        TextButton(
            onClick = {
                onItemClick(Item.Profile)

            }
        ) {
            Text(
                "Profile",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(16.dp)
            )
        }
        TextButton(
            onClick = {
                onItemClick(Item.Home)
            }
        ) {
            Text(
                "Home",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(16.dp)
            )
        }
        TextButton(
            onClick = {
                onItemClick(Item.Search)
            }
        ) {
            Text(
                "Search",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(16.dp)
            )
        }
    }

    @Composable
    fun Main(state: WindowState) {
        val scaffoldState = rememberScaffoldState() //сохранение состояния Scaffold
        val scope = rememberCoroutineScope()
        val screens = Screens()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            drawerShape = customShape(state.getSize()), //форма выпадающего меню
            drawerContent = { //выпадающее меню Scaffold
                drawableMenuLeft { item ->
                    currentItem = item
                    scope.launch {
                        scaffoldState.drawerState.close() // Закрыть выдвижное меню после выбора элемента
                    }
                }
            },
            scaffoldState = scaffoldState, //состояние Scaffold
            content = {
                Row(Modifier.fillMaxWidth()) {
                    var searchedText by remember { mutableStateOf("") }

                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        MyScreenContent(currentItem)
                    }
                }

            },
            //нижний элемент Scaffold
            bottomBar = {
                bottomAppBar(state)
            },
            //верхний элемент Scaffold
            topBar = {
                topAppBar(state, scaffoldState)
            }
        )
    }

    @Composable
    fun MyScreenContent(currentItem: Item) {
        val screens = Screens()

        when (currentItem) {
            is Item.Home -> screens.HomeScreen()
            is Item.Search -> screens.SearchScreen()
            is Item.Profile -> screens.ProfileScreen()
        }
    }
}