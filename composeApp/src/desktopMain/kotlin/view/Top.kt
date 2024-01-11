package view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import view.state.WindowState

//содержимое верхней панели
@Composable
fun contenTopBar (state: WindowState, scaffoldState: ScaffoldState)  {
    val scope = rememberCoroutineScope()
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("Книжный обменник v1.0")
            }
        } ) {
            Icon(Icons.Default.Info, contentDescription = "Help")
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = {
            state.close()
        }) {
            Icon(Icons.Default.Close, contentDescription = "Close")
        }
    }
}


//верхняя панель, в которую приходит состояние всего окна
//
//нужен contentTopBar для отображение всякого содержимого
@Composable
fun topAppBar(state: WindowState, scaffoldState: ScaffoldState,) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text("Книжный обменник") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch{
                    scaffoldState.drawerState.open(

                    )
                }
            }) {
                if (state.title == "Main")
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            contenTopBar(state, scaffoldState)
        },
        backgroundColor = Color.White, // Change as needed
        elevation = 4.dp // Change elevation as needed
    )
}

