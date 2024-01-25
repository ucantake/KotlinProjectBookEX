package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import view.navigation.Item
import view.state.WindowState

class MainScreen() {
    private val listItems = listOf(
    Item.Home,
    Item.SmartContract,
    Item.Profile
    )

    private var currentItem: Item by mutableStateOf(Item.Home)


    //TODO отрегулировать ширину окна
    //TODO при уменьшении ширины, уменьшается длина
    //кажется проблема в передаче скефолда
    @Composable
    fun drawableMenuLeft(onItemClick: (Item) -> Unit, scaffold : ScaffoldState, state : WindowState) {
        val width = state.getSize().width / 3 //TODO сделать отдельную функцию для вычисления ширины окна
        Scaffold (
            modifier = Modifier
                .width(width.dp), //TODO отрегулировать чтобы было красиво между элементами внутри панели
//                .padding(6.dp),

            scaffoldState = scaffold, //состояние Scaffold

            content = {
                Column(
                    modifier = Modifier
                        .width(width.dp)
                        .padding(6.dp),
                    Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource("icons/books-svgrepo-com.svg"),
                        contentDescription = "Icons",
                        modifier = Modifier
                            .width(width.dp)

                    )
                    TextButton(
                        onClick = {
                            onItemClick(Item.Profile)

                        }
                    ) {
                        Text(
                            "Профиль",
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
                            "Домашняя",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(16.dp)
                        )
                    }
                    TextButton(
                        onClick = {
                            onItemClick(Item.SmartContract)
                        }
                    ) {
                        Text(
                            "Обмен",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(16.dp)
                        )
                    }
                }
            }
        )
    }

    /*
    * основной экран
     */
    @Composable
    fun Main(state: WindowState) {
        val scaffoldState = rememberScaffoldState() //сохранение состояния Scaffold
        val scope = rememberCoroutineScope()
        val js = JsonObject()



        Scaffold(
            modifier = Modifier
                .fillMaxSize(), //TODO добавить полосу прокрутки
            drawerShape = customShape(state.getSize()), //форма выпадающего меню
            drawerContent = { //выпадающее меню Scaffold
                drawableMenuLeft(
                    { item ->
                        currentItem = item
                        scope.launch {
                            scaffoldState.drawerState.close() // Закрыть выдвижное меню после выбора элемента
                        }
                    },
                scaffold = scaffoldState, state = state)
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

    /*
    * окна экрана
     */
    @Composable
    fun MyScreenContent(currentItem: Item) {

        when (currentItem) {
            is Item.Home -> HomeScreen()
            is Item.SmartContract -> SmartContract()
            is Item.Profile -> ProfileScreen()
            else -> {}
        }
    }
}