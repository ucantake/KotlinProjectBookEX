package view

import DATADOWNLOADING
import JSON_SEARCH_USERS_BOOKS
import NAMEUSER
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import model.JsonSmartContract
import repository.SynchronizedJsonData
import repository.searchBooksUser
import util.setSmartContract
import view.utils.DropdownExample

@Composable
fun SmartContract() {
    SynchronizedJsonData()
    var loading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    var selectedValueUser: String = ""
    var selectedValueBook: String = ""
    var itemsJsonUsers by remember { mutableStateOf(mutableStateListOf<String>()) }
    var itemsJsonBooks by remember { mutableStateOf(mutableStateListOf<String>()) }
    val data = LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            //TODO сделать проверку на наличие данных
            val json = Json.decodeFromString<JsonSmartContract>(JSON_SEARCH_USERS_BOOKS)

            for (i in json.books) {
                itemsJsonBooks += i.title
            }
            for (i in json.users) {
                itemsJsonUsers += i.name
            }
        }
        loading = false
    }

    var price by remember {
        mutableStateOf("0.0")
    }

    var text by remember {
        mutableStateOf("")
    }
    if (loading) {
        // Экран загрузки
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            modifier = Modifier.padding(6.dp),
            scaffoldState = scaffoldState,
            bottomBar = { Box(modifier = Modifier.height(40.dp)) },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    )
                {
                    //отображение данных
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally), Arrangement.spacedBy(0.dp)
                            ) {
                                //эти две кнопки должы разделять между собой пространство равномерно, одинаково на каждый Box, подстраивающееся под разрешение экрана
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    //выпадающее меню
                                    DropdownExample(
                                        onItemSelected = { selectedValueUser = it },
                                        items = itemsJsonUsers,
                                        text = "Выберете пользователя"
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    //выпадающее меню
                                    DropdownExample(
                                        onItemSelected = { selectedValueBook = it },
                                        items = itemsJsonBooks,
                                        text = "Выберете книгу"
                                    )
                                }
                            }
                            Column (modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(0.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(0.dp)) {

                                    Box (modifier = Modifier.fillMaxWidth().weight(1f), Alignment.Center){
                                        TextField(
                                            shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                            value = price,
                                            label = {
                                                Text("Цена")
                                            },
                                            onValueChange = {
                                                price = it
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                    Box(modifier = Modifier.fillMaxWidth().weight(0.1f).height(60.dp), Alignment.Center) {
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
                                Column (modifier = Modifier.fillMaxWidth().weight(1f), Arrangement.Center) {
                                    TextField(
                                        shape = RoundedCornerShape(size = 20.dp),//скругление углов
                                        value = text,
                                        label = {
                                            Text("Комментарий")
                                        },
                                        onValueChange = {
                                            text = it
                                        },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                        }


                    }

                    //кнопка для получения данных
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {

                            Button(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(6.dp),
                                onClick = {
                                    scope.launch {
                                        if (price == "" || price == null || text == "" || text == null || selectedValueBook == "" || selectedValueBook == null || selectedValueUser == "" || selectedValueUser == null ){
                                            scaffoldState.snackbarHostState.showSnackbar("Заполните все поля")

                                        }else if (price.toDoubleOrNull() == null){
                                            val numericPrice: Double? = price.toDoubleOrNull()
                                            if (numericPrice == null) {
                                                scaffoldState.snackbarHostState.showSnackbar("Цена должна быть числом")
                                            }
                                        }
                                        else if (price.toDouble() < 0.0){
                                            scaffoldState.snackbarHostState.showSnackbar("Цена не может быть отрицательной")
                                        }else if (price.toDouble() == 0.0) {
                                            scaffoldState.snackbarHostState.showSnackbar("Цена не может быть нулевой")
                                        }

                                        else {
                                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Уверены?",
                                                actionLabel = "Да",
                                                duration = SnackbarDuration.Indefinite
                                            )

                                            when (result) {
                                                SnackbarResult.ActionPerformed -> {
                                                    val dataSend = setSmartContract(
                                                        NAMEUSER,
                                                        selectedValueUser,
                                                        selectedValueBook,
                                                        price,
                                                        text
                                                    )
                                                    if (dataSend){
                                                        scaffoldState.snackbarHostState.showSnackbar(
                                                            "Данные отправлены "
                                                        )
                                                    }else {
                                                        scaffoldState.snackbarHostState.showSnackbar(
                                                            "Ошибка отправки данных"
                                                        )

                                                    }

                                                }
                                                SnackbarResult.Dismissed -> {
                                                }
                                            }
                                        }
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
                    }


                }
            }
        )
    }
}