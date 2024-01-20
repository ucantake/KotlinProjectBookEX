package view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repository.SynchronizedJsonData
import view.utils.DropdownExample

@Composable
fun SmartContract() {
    SynchronizedJsonData()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

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
    Scaffold (modifier = Modifier.padding(6.dp), scaffoldState = scaffoldState,bottomBar = { Box (modifier = Modifier.height(40.dp)) }) {
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
                if (switchViewSmartContract) {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Row {
                            //выпадающее меню
                            DropdownExample(
                                onItemSelected = { selectedValue = it },
                                items = usersValues,
                                text = "Выберете пользователя"
                            )
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


                } else {
                    Text("Контракт успешно размещен")
                }

            }

            //кнопка для получения данных
            Box(
                modifier = Modifier.fillMaxWidth().weight(0.5f),
                contentAlignment = Alignment.Center
            ) {

                if (switchViewSmartContract) {

                    Column(modifier = Modifier.padding(6.dp)) {

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
                    Text(selectedValue)

                } else {
                    Column(modifier = Modifier.padding(6.dp)) {
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

