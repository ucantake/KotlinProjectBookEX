package view

import EMAIL
import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
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
    fun SearchScreen() {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "SearchScreen",
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun ProfileScreen() {
        val httpsClietn = GetHttpApiClient()
        val scope = rememberCoroutineScope()
        var json : JsonObject
        var role = ""

        Scaffold {
                runBlocking {
                    var json = Json.parseToJsonElement((httpsClietn.getDataProfile(NAMEUSER)))
                    println(json)
                    EMAIL = json.jsonObject.get("email").toString()
                    role = json.jsonObject.get("role").toString()
                }
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "ProfileScreen in Role = $role" ,
                textAlign = TextAlign.Center
            )

        }

//        val stri = httpsClietn.getDataProfile($NAMEUSER)
    }
}