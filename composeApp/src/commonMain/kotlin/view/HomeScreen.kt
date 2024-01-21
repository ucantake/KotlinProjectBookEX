package view

import ACCOUNT
import BALANCE
import JSON_TRANSACTION_BOOKS
import NAMEUSER
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import model.JsonSmartContract
import model.TransactionsjsonHistory
import repository.SynchronizedJsonData

@Composable
fun HomeScreen() {
    SynchronizedJsonData()
    val jsonTransaction = Json.decodeFromString<TransactionsjsonHistory>(JSON_TRANSACTION_BOOKS).transactions
    val scaffoldState = rememberScaffoldState()
    Scaffold (modifier = Modifier.padding(6.dp), scaffoldState = scaffoldState,bottomBar = { Box (modifier = Modifier.height(60.dp)) }) {
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
                modifier = Modifier.fillMaxWidth().weight(2f).verticalScroll(rememberScrollState()),
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
                    for ((index,transaction) in jsonTransaction.withIndex()) {
                        Row {
                            Text(
                                if (index == 0) " Название \n"+ "${transaction.book_title}" else "${transaction.book_title}",
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                if (index == 0) " Отправитель \n"+ "${transaction.user_sender}" else "${transaction.user_sender}",
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(3.dp),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                if (index == 0) " Получатель \n"+ "${transaction.user_receiver}" else "${transaction.user_receiver}",
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
            Box(
                modifier = Modifier.fillMaxSize().weight(0.5f),
                contentAlignment = Alignment.Center
            ){

            }
        }
    }
}
