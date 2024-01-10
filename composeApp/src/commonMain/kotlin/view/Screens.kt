package view

import ACCOUNT
import BALANCE
import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
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

        //TODO один экран для создания смарт контрактов, другой для просмотра своих смарт контрактов
        var switchViewSmartContract by remember { mutableStateOf(true) }

        var progress by remember { mutableStateOf(0.0f) }

        var items = listOf("")
        var selectedValue: String = ""


        Scaffold (modifier = Modifier.padding(6.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,

                )
                {
                    //отображение данных
                    Box(
                        modifier = Modifier.background(Color.Red).fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center){
                            if (switchViewSmartContract) {

                                //выпадающее меню
                                DropdownExample { selectedItem ->
                                    selectedValue = selectedItem
                                    // здесь можно выполнить дополнительные действия при выборе элемента
                                }

                            }else {
                                Text("Это текст, который может измениться")
                            }
                    }

                    //кнопка для получения данных
                    Box(
                        modifier = Modifier.background(Color.Green).fillMaxWidth().weight(0.5f),
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
                                        }

                                        progress = 0.0f
                                        switchViewSmartContract = false

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
                                        text = "Get Data",
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

    //контекстное меню
    //TODO этим отрисовывать полученные данные о книгах и пользователях, причем сначала пользователь, потом книга
    @Composable
    fun DropdownExample(onItemSelected: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        var selectedItem by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Selected item: $selectedItem")
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
                    Text("Select an item")
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
}

