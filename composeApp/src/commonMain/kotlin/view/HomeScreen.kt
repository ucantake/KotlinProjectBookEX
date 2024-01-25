package view

import ACCOUNT
import BALANCE
import JSON_TRANSACTION_BOOKS
import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.TransactionsjsonHistory
import repository.SynchronizedJsonData
import util.twoFactorTransaction
import view.utils.ConfirmCancelButtonView

@Composable
fun HomeScreen() {
    SynchronizedJsonData()
    val jsonTransaction = Json.decodeFromString<TransactionsjsonHistory>(JSON_TRANSACTION_BOOKS).transactions
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val _dataSuccess = mutableStateOf(false)
    Scaffold (modifier = Modifier.padding(6.dp), scaffoldState = scaffoldState,bottomBar = { Box (modifier = Modifier.height(60.dp)) }) {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly){
            Box(
                modifier = Modifier.fillMaxWidth().height(20.dp).weight(0.1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    text = "Здравствуйте  у $NAMEUSER на $ACCOUNT счете $BALANCE  ETH",
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "История транзакций",
                        modifier = Modifier,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Row {
                        Text(
                            " Название \n",
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Text(
                            " Отправитель \n",
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Text(
                            " Получатель \n",
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Text(
                            " Комментарий \n",
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Text(
                            " Подтвреждение \n",
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                    for ((index,transaction) in jsonTransaction.withIndex()) {
                        Row {
                            Text(
                                transaction.book_title,
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                transaction.user_sender,
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                transaction.user_receiver,
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                transaction.comment,
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Box(modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(9.dp)) {
                                if (_dataSuccess.value==true) {
                                    Text(
                                        "Записано",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    )
                                }
                                else if (transaction.successful == false) {

                                    ConfirmCancelButtonView(
                                        transaction.book_title,
                                        onConfirm = { successful, bookTitle ->
                                            //TODO рефактор dataSuccess
                                            if (successful) {
                                                scope.launch {
                                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                                        "Уверены?",
                                                        actionLabel = "Да",
                                                        duration = SnackbarDuration.Long
                                                    )
                                                    when (result) {
                                                        SnackbarResult.ActionPerformed -> { // Пользователь нажал "Да"
                                                            val dataSuccess = twoFactorTransaction(
                                                                transaction.user_receiver,
                                                                transaction.book_title,
                                                                successful
                                                            )
                                                            if (dataSuccess) {
                                                                _dataSuccess.value = true
                                                                scaffoldState.snackbarHostState.showSnackbar("Транзакция подтверждена")
                                                            } else {
                                                                scaffoldState.snackbarHostState.showSnackbar("Ошибка ответа сервера")
                                                            }
                                                        }

                                                        SnackbarResult.Dismissed -> { /*уведомление пропало, пользователь не нажал*/
                                                        }
                                                    }
                                                }
                                            } else {
                                                scope.launch {
                                                    val dataSuccess = twoFactorTransaction(
                                                        transaction.user_receiver,
                                                        transaction.book_title,
                                                        successful
                                                    )
                                                    if (dataSuccess) {
                                                        _dataSuccess.value = true
                                                        scaffoldState.snackbarHostState.showSnackbar("Транзакция отменена")
                                                    } else {
                                                        scaffoldState.snackbarHostState.showSnackbar("Ошибка ответа сервера")
                                                    }
                                                }
                                            }
                                        }
                                    )


                                } else {
                                    //TODO динамическая работа с кнопками
                                    // если пользователь нажал подтверждение и транзакция подтвердилась, убрать кнопки и написать записано
                                    Text(
                                        "Записано",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
