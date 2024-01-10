package view

import ACCOUNT
import BALANCE
import EMAIL
import JSON
import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import model.JsonData
import model.SmartContract
import repository.SynchronizedJsonData
import webservices.GetHttpApiClient

class Screens {



    @Composable
    fun HomeScreen() {
        Box (
          modifier = Modifier
              .fillMaxSize()
              .padding(6.dp)
              .background(color = Color.Yellow )
        ){
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "HomeScreen",
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun SmartContract() {
        val httpsClietn = GetHttpApiClient()
        val scope = rememberCoroutineScope()
        var json : JsonObject
        var showText by remember { mutableStateOf(true) }


        Scaffold {
            if (showText) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround
                )
                {
                    //отображение данных
                    Box(modifier = Modifier.background(Color.Red).fillMaxWidth().weight(1f)) {
                        Row() {

                                Text("Это текст, который может измениться")

                        }

                    }
                    Box(
                        modifier = Modifier.background(Color.Green).fillMaxWidth().weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
                        {
                            Button(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(6.dp),
                                onClick = {
                                    showText = false

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
                        }
                    }


                }
            }else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround
                )
                {
                    //отображение данных
                    Box(modifier = Modifier.background(Color.Red).fillMaxWidth().weight(1f)) {
                        Row() {

                            Text("ntrcn")

                        }

                    }
                    Box(
                        modifier = Modifier.background(Color.Green).fillMaxWidth().weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
                        {
                            Button(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(6.dp),
                                onClick = {
                                    showText = true

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
                        }
                    }


                }
            }
        }

    }

    @Composable
    fun ProfileScreen() {
        val httpsClietn = GetHttpApiClient()
        val scope = rememberCoroutineScope()
        var json : JsonObject
        var role = ""
        var name = ""
        var account = ""
        var balance = ""

        Scaffold {

            SynchronizedJsonData()

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "ProfileScreen in Role = $role \n name = $NAMEUSER \n wallet = ${ACCOUNT.substring(0,10)} \n balance = $BALANCE",
                textAlign = TextAlign.Center
            )

        }

    }
}

