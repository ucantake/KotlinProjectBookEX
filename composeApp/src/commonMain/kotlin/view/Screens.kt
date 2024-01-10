package view

import ACCOUNT
import BALANCE
import EMAIL
import JSON
import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import model.JsonData
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

        Scaffold {


            TextButton(
                onClick = {
                    runBlocking {
                        json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER))) as JsonObject
                        println(json)
                    }
                }
            ) {
                Text(
                    "Create smart contract",
                    textAlign = TextAlign.Center
                )
            }


            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "Smart Contracts",
                textAlign = TextAlign.Center
            )
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
                runBlocking {
                    //TODO сделать загрузку основных данных в момент открытия приложения
                    val js = Json.decodeFromString<JsonData>(JSON)



                }
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "ProfileScreen in Role = $role \n name = $NAMEUSER \n wallet = ${ACCOUNT.substring(0,10)} \n balance = $BALANCE",
                textAlign = TextAlign.Center
            )

        }

//        val stri = httpsClietn.getDataProfile($NAMEUSER)
    }
}

