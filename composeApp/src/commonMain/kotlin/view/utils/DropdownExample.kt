package view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropdownExample(onItemSelected : (String) -> Unit, items : List<String>, text : String ) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .width(200.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("$text : $selectedItem", modifier = Modifier.height(50.dp).align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        )
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .padding(16.dp)
        ) {

            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                Column (modifier = Modifier.align(Alignment.CenterVertically)) {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    { Text("$text") }

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    { Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null) }
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
            ) {

                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = item
                            onItemSelected(item)
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