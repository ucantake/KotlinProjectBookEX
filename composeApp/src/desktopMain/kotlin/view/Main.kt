package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import view.state.WindowState

@Composable
fun Main(state: WindowState) {
    val scaffoldState = rememberScaffoldState() //сохранение состояния Scaffold
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        drawerShape = customShape(state.getSize()), //форма выпадающего меню
        drawerContent = { //выпадающее меню Scaffold
            drawableMenuLeft()
        },
        scaffoldState = scaffoldState, //состояние Scaffold
        content = {
            Row(Modifier.fillMaxWidth()) {
                var searchedText by remember { mutableStateOf("") }

                //левое окно
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

//                        //отображение левого меню
//                        drawableMenuLeft(
//                            searchedText,
//                            onValueChange = { searchedText = it }
//                        )

                    }
                }

                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {

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