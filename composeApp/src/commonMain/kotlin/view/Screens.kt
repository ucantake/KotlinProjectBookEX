package view

import ACCOUNT
import BALANCE
import EMAIL
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
                    var json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER)))
//                    println(json)
                    // Получение значений из вложенных объектов
                    val userName = json.jsonObject["user"]?.jsonObject?.get("name")?.jsonPrimitive?.contentOrNull
                    val userEmail = json.jsonObject["user"]?.jsonObject?.get("email")?.jsonPrimitive?.contentOrNull
                    val userRole = json.jsonObject["user"]?.jsonObject?.get("role")?.jsonPrimitive?.contentOrNull

                    val walletAccount = json.jsonObject["wallet"]?.jsonObject?.get("account")?.jsonPrimitive?.contentOrNull
                    val walletKey = json.jsonObject["wallet"]?.jsonObject?.get("key")?.jsonPrimitive?.contentOrNull

                    val balanceEth = json.jsonObject["balance"]?.jsonObject?.get("balanceEth")?.jsonPrimitive?.contentOrNull

                    role = userRole.toString()
                    name = userName.toString()
                    account = walletAccount.toString()
                    balance = balanceEth.toString()

                    EMAIL = userEmail.toString()
                    ACCOUNT = walletAccount.toString()
                    BALANCE = balanceEth.toString()


                }
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "ProfileScreen in Role = $role \n name = $name \n wallet = $account \n balance = $balance",
                textAlign = TextAlign.Center
            )

        }

//        val stri = httpsClietn.getDataProfile($NAMEUSER)
    }
}

