package view

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import JSON
import NAMEUSER
import ROLE
import USER_NAME
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import model.JsonData
import repository.SynchronizedJsonData
import util.addBook
import util.createUser
import webservices.GetHttpApiClient

class Screens {

    var viewProfile = "profile"

    @Composable
    fun HomeScreen() {
        Box (
          modifier = Modifier
              .fillMaxSize()
              .padding(6.dp)
        ){
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "Здравствуйте  у $NAMEUSER  на $ACCOUNT счете $BALANCE  ETH",
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun SmartContract() {
        val httpsClietn = GetHttpApiClient()
        val scope = rememberCoroutineScope()
        var json : JsonObject

        //TODO один экран для создания смарт контрактов, другой для просмотра своих смарт контрактов
        var switchViewSmartContract by remember { mutableStateOf(true) }

        var progress by remember { mutableStateOf(0.0f) }

        var items = listOf("")
        var selectedValue: String = ""


        Scaffold (modifier = Modifier.padding(6.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,

                )
                {
                    //отображение данных
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center){
                            if (switchViewSmartContract) {

                                //выпадающее меню
                                DropdownExample { selectedItem ->
                                    selectedValue = selectedItem
                                    // здесь можно выполнить дополнительные действия при выборе элемента
                                }

                            }else {
                                Text("Это текст, который может измениться")
                            }
                    }

                    //кнопка для получения данных
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {

                        if (switchViewSmartContract) {

                            Column (modifier = Modifier.padding(6.dp)) {
                                CircularProgressIndicator(
                                    progress = progress,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Button(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(6.dp),
                                    onClick = {
                                        scope.launch {
                                            while (progress < 1f) {
                                                progress += 0.1f
                                                delay(1000L)
                                            }
                                        }

                                        progress = 0.0f
                                        switchViewSmartContract = false

                                    }
                                ) {
                                    Text(
                                        text = "Get Data2",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }


                        }else {
                            Column (modifier = Modifier.padding(6.dp)) {
                                CircularProgressIndicator(
                                    progress = progress,
                                    modifier = Modifier.align(Alignment.CenterHorizontally))
                                Button(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(6.dp),
                                    onClick = {
                                        scope.launch {
                                            while (progress < 1f) {
                                                progress += 0.1f
                                                delay(1000L)
                                            }

                                        }
                                        progress = 0.0f
                                        switchViewSmartContract = true

                                    }
                                ) {
                                    Text(
                                        text = "Get Data",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                        }
                    }


                }
            }
        }



    @Composable
    fun ProfileScreen()     {

        var switchViewProfile by remember { mutableStateOf("profile") }

        val json = Json.decodeFromString<JsonData>(JSON)
        val booksQuality = json.books.quantity.toInt()

        var title by remember {
            mutableStateOf("")
        }
        var author by remember {
            mutableStateOf("")
        }
        //TODO переводить price в ETH
        var price by remember {
            mutableStateOf("")
        }
        var isbn by remember {
            mutableStateOf("")
        }
        var ubc by remember {
            mutableStateOf("")
        }
        var bbk by remember {
            mutableStateOf("")
        }

        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val progress = remember { mutableStateOf(0.0f) }


        Scaffold (
            modifier = Modifier.padding(6.dp),
            scaffoldState = scaffoldState,
            bottomBar = {Box (modifier = Modifier.height(40.dp))}
            ) {
            if (switchViewProfile == "profile"){
                SynchronizedJsonData()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .border(1.dp, MaterialTheme.colors.primary),
                            text = "Профиль = $NAMEUSER  кошелек = ${ACCOUNT.substring(0, 10)}  баланс = $BALANCE ETH\n" +
                                    "Количество книг $booksQuality",
                            textAlign = TextAlign.Start,
                            textDecoration = TextDecoration.Underline,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        if (booksQuality != 0) {
                            //TODO сделать вывод списка книг
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center) {
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                            TextButton(
                                modifier = Modifier
                                    .padding(6.dp),
                                onClick = {
                                    title = ""
                                    author = ""
                                    price = ""
                                    isbn = ""
                                    ubc = ""
                                    bbk = ""
                                    switchViewProfile = "addBook"
                                }
                            ) {
                                Text(
                                    text = "Книги",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }


                }
            } else if (switchViewProfile == "addBook") {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
                        ) {
                            Row() {
                                TextField(
                                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                    value = title,
                                    label = {
                                        Text("Название книги")
                                    },
                                    onValueChange = {
                                        title = it
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row() {
                                TextField(
                                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                    value = author,
                                    label = {
                                        Text("Автор книги")
                                    },
                                    onValueChange = {
                                        author = it
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextField(
                                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                    value = isbn,
                                    label = {
                                        Text("ISBN")
                                    },
                                    onValueChange = {
                                        isbn = it
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                TextField(
                                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                    value = ubc,
                                    label = {
                                        Text("УБК")
                                    },
                                    onValueChange = {
                                        ubc = it
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                TextField(
                                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                    value = bbk,
                                    label = {
                                        Text("ББК")
                                    },
                                    onValueChange = {
                                        bbk = it
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row() {
                                TextField(
                                    shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                    value = price,
                                    label = {
                                        Text("Цена книги в ETH")
                                    },
                                    onValueChange = {
                                        price = it
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Button(
                                onClick = {
                                    if (title == "" || author == "" || price == "") {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Заполните все поля")
                                        }
                                    }else if (isbn == "" || ubc == "" || bbk == "") {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Заполните поля ISBN, УБК, ББК")
                                        }
                                    }else if (!checkPrice(price)) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Введена не корректная цена")
                                        }
                                    }else {
                                        scope.launch {
                                            DATADOWNLOADING = false
                                            while (!DATADOWNLOADING) {
                                                val data = addBook(NAMEUSER, title, author, isbn, ubc, bbk, price)
                                                while (progress.value < 1f) {
                                                    progress.value += 0.1f
                                                    delay(1000L)
                                                    if (DATADOWNLOADING) break
                                                }
                                                println("DATA = $data")

                                                if (!data) progress.value = 0f
                                                if (data == false) {
                                                    scaffoldState.snackbarHostState.showSnackbar("Ошибка")
                                                    break
                                                } else if (data == true) {
                                                    scaffoldState.snackbarHostState.showSnackbar("Книга добавлена")
                                                    switchViewProfile = "profile"
                                                }
                                            }
                                        }

                                    }
                                },
                            ) {
                                Text("Добавить книгу")
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            CircularProgressIndicator(
                                progress.value
                            )

                        }
                    }
                )
            }


        }

    }

    //контекстное меню
    //TODO этим отрисовывать полученные данные о книгах и пользователях, причем сначала пользователь, потом книга
    @Composable
    private fun DropdownExample(onItemSelected: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        var selectedItem by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Selected item: $selectedItem")
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .wrapContentSize(Alignment.TopStart)
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select an item")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = item
                                onItemSelected(item) // вызов callback при выборе элемента
                                expanded = false
                            }
                        ) {
                            Text(item)
                        }
                    }
                }
            }
        }
    }

    private fun checkPrice (price : String) :Boolean {
        try {
            val num = price.toLong()
        }catch (e : Exception){
            return false
        }
        return true

    }
}
