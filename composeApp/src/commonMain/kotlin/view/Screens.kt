package view

import ACCOUNT
import BALANCE
import DATADOWNLOADING
import JSON
import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import webservices.GetHttpApiClient

class Screens {

    var viewProfile = "profile"

    @Composable
    fun HomeScreen() {
        val scaffoldState = rememberScaffoldState()
        Scaffold (modifier = Modifier.padding(6.dp), scaffoldState = scaffoldState,bottomBar = {Box (modifier = Modifier.height(60.dp))}) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(),
                        text = "Здравствуйте  у $NAMEUSER  на $ACCOUNT счете $BALANCE  ETH",
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth().weight(2f),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        modifier = Modifier.fillMaxSize().border(1.dp, MaterialTheme.colors.primary),
                    ) {
                        Text(
                            "Текущие смарт контракты",
                            modifier = Modifier.border(1.dp, MaterialTheme.colors.primary),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Row {
                            Text(
                                " Пользователь ",
                                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).fillMaxHeight(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                " Книга ",
                                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).fillMaxHeight(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                " Цена ",
                                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).fillMaxHeight(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                " Окончание ",
                                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).fillMaxHeight(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                " Комментарий ",
                                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).fillMaxSize(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize().weight(0.5f),
                    contentAlignment = Alignment.Center
                ){

                }
            }
        }
    }

    @Composable
    fun SmartContract() {
        val httpsClietn = GetHttpApiClient()
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        var json : JsonObject

        //TODO один экран для создания смарт контрактов, другой для просмотра своих смарт контрактов
        var switchViewSmartContract by remember { mutableStateOf(true) }

        var progress by remember { mutableStateOf(0.0f) }

        var items = listOf("")
        var selectedValue: String = ""
        val usersValues = listOf("Пользователь 1", "Пользователь 2", "Пользователь 3", "Пользователь 4")
        val booksValues = listOf("Книга 1", "Книга 2", "Книга 3", "Книга 4")

        var price by remember {
            mutableStateOf("0.0")
        }

        var text by remember {
            mutableStateOf("")
        }
        Scaffold (modifier = Modifier.padding(6.dp), scaffoldState = scaffoldState,bottomBar = {Box (modifier = Modifier.height(40.dp))}) {
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
                                Column (
                                    modifier = Modifier.align(Alignment.Center)
                                ) {
                                    Row {
                                        //выпадающее меню
                                        DropdownExample(
                                            onItemSelected = { selectedValue = it },
                                            items = usersValues,
                                            text = "Выберете пользователя"
                                        )
                                        // здесь можно выполнить дополнительные действия при выборе элемента

                                        //выпадающее меню
                                        DropdownExample(
                                            onItemSelected = { selectedValue = it },
                                            items = booksValues,
                                            text = "Выберете книгу"
                                        )
                                    }

                                    Row {
                                        Box {
                                            TextField(
                                                shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                                value = price,
                                                label = {
                                                    Text("Цена")
                                                },
                                                onValueChange = {
                                                    price = it
                                                },
                                                modifier = Modifier
                                            )
                                        }
                                        Box(modifier = Modifier.height(50.dp).padding(16.dp), Alignment.Center) {
                                            Text(
                                                "ETH",
                                                style = TextStyle(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                )
                                            )
                                        }

                                    }

                                    Spacer(modifier = Modifier.padding(16.dp))
                                    TextField(
                                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                        value = text,
                                        label = {
                                            Text("Комментарий")
                                        },
                                        onValueChange = {
                                            text = it
                                        },
                                        modifier = Modifier.fillMaxHeight()
                                    )

                                }


                            }else {
                                Text("Контракт успешно размещен")
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


                                        progress = 0.0f
                                            scaffoldState.snackbarHostState.showSnackbar("Контракт отправлен")
                                        }

                                    }
                                ) {
                                    Text(
                                        text = "Отправить",
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
                                        text = "Назад",
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
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier.fillMaxSize().border(1.dp, MaterialTheme.colors.primary),
                        ) {
                            Text(
                                "Ваши книги",
                                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Row {
                                Text(
                                    " Название ",
                                    modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).weight(1f),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    " Автор ",
                                    modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).weight(1f),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    " ISBN ",
                                    modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).weight(1f),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    " Цена ",
                                    modifier = Modifier.border(1.dp, MaterialTheme.colors.primary).weight(1f),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            }
                        }
                        if (booksQuality != 0) {
                            //TODO сделать вывод списка книг
                        }else {

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
                                    switchViewProfile = "removeBook"
                                }
                            ) {
                                Text(
                                    text = "Удаление",
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
            else if (switchViewProfile == "removeBook") {
                Column() {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {

                    }
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        Text("remove")
                    }
                    Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center) {
                        Button(
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
                                } else if (!checkPrice(price)) {
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
            else if (switchViewProfile == "updateBook") {
                Column()
                {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){

                    }
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){
                        Text("update")
                    }
                    Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center){
                        Button(
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
                    }
                }
            }

        }

    }
    /*
    шаблон страниц
    Column()
            {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){

                }
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){

                }
                Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center){

                }
            }
     */

    //контекстное меню
    //TODO этим отрисовывать полученные данные о книгах и пользователях, причем сначала пользователь, потом книга
    @Composable
    private fun DropdownExample(onItemSelected : (String) -> Unit, items : List<String>, text : String ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("$text : $selectedItem")
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
                    Text("")
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
