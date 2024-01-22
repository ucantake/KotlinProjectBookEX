package view

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import JSON
import JSON_PROFILE
import NAMEUSER
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.BooksResponse
import model.JsonData
import repository.SynchronizedJsonData
import util.addBook
import view.utils.CheckPrice

@Composable
fun ProfileScreen()     {
    SynchronizedJsonData()

    var switchViewProfile by remember { mutableStateOf("profile") }

    val json = Json.decodeFromString<JsonData>(JSON)
    val jsonProfile = Json.decodeFromString<BooksResponse>(JSON_PROFILE)
    val books = jsonProfile.books.books
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
        bottomBar = { Box (modifier = Modifier.height(40.dp)) }
    ) {
        if (switchViewProfile == "profile"){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {

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
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .border(1.dp, MaterialTheme.colors.primary),
                    contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Ваши книги",
                            modifier = Modifier,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        for((index,book) in books.withIndex()) {
                            Row {
                                Text(
                                    if (index ==0) " Название \n"+ "${book.title}" else "${book.title}",
                                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    if (index ==0) " Автор \n"+ "${book.author}" else "${book.author}",
                                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    if (index ==0) " Цена \n"+ "${book.price}" else "${book.price}",
                                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            }
                        }

                    }
                }
                Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Управление книгами")
                            Row() {
                                TextButton(
                                    modifier = Modifier
                                        .padding(6.dp),
                                    onClick = {
                                        switchViewProfile = "removeBook"
                                    }
                                ) {
                                    Text(
                                        text = "Удаление",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
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
                                        text = "Добавить",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }
                    }
                }


            }
        } else if (switchViewProfile == "Book") {
            var bookView by remember { mutableStateOf("viewBook") }
            //TODO страница с показом книг и кнопками для выбора остальных экранов
            Column()
            {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){

                }
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){

                }
                Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center){
                    Row (){
                        TextButton(
                            modifier = Modifier
                                .padding(6.dp),
                            onClick = {
                                switchViewProfile = "profile"
                            }
                        ) {
                            Text(
                                text = "Профиль",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }

                        TextButton(
                            modifier = Modifier
                                .padding(6.dp),
                            onClick = {
                                switchViewProfile = "addBook"
                            }
                        ) {
                            Text(
                                text = "Добавить",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .padding(6.dp),
                            onClick = {
                                switchViewProfile = "updateBook"
                            }
                        ) {
                            Text(
                                text = "Обновить",
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

        }
        else if (switchViewProfile == "addBook") {
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
                            Text("УДК")
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
                Row () {
                    Button(
                        modifier = Modifier
                            .padding(6.dp),
                        onClick = {
                            switchViewProfile = "profile"
                        }
                    ) {
                        Text(
                            text = "Профиль"
                        )
                    }
                    Button(
                        modifier = Modifier
                            .padding(6.dp),
                        onClick = {
                            if (title == "" || author == "" || price == "") {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Заполните все поля")
                                }
                            } else if (isbn == "" || ubc == "" || bbk == "") {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Заполните поля ISBN, УБК, ББК")
                                }
                            } else if (!CheckPrice(price)) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Введена не корректная цена")
                                }
                            } else {
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
                }
                Spacer(modifier = Modifier.height(5.dp))
                CircularProgressIndicator(
                    progress.value
                )

            }


        }

    }

}