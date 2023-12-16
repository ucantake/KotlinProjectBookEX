package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Main(state: WindowState){
    val scaffoldState = rememberScaffoldState() //сохранение состояния Scaffold
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        drawerContent = { //выпадающее меню Scaffold
            Text("ASD")

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

                        //отображение левого меню
                        drawableMenuLeft(
                            searchedText,
                            onValueChange = { searchedText = it }
                        )

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

@Composable
fun drawableMenuLeft(searchedText: String, onValueChange: (String) -> Unit){
    Image(
        painter = painterResource("icons/books-svgrepo-com.svg"),
        contentDescription = "Icons",
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
    OutlinedTextField(//TODO перестал работать ввод текста
        modifier = Modifier.fillMaxWidth().height(70.dp),
        singleLine = true,
        placeholder = {
            Text("Search")
        },
        label = {
            Text("Search")
        },
        value = searchedText,
        onValueChange = {
            searchedText
        },
        trailingIcon = {
            IconButton(
                onClick = {

                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search button",
                    tint = Color.Black
                )
            }
        }
    )
    TextButton(
        onClick = {

        }
    ) {
        Text(
            "Headlines",
            fontWeight = FontWeight.W900,
            color = Color.Black,
            modifier = Modifier
        )
    }
}