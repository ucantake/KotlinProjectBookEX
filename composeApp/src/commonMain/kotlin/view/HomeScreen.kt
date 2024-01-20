package view

import ACCOUNT
import BALANCE
import NAMEUSER
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
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
