package view.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
fun DropdownExample(onItemSelected : (String) -> Unit, items : List<String>, text : String="" ) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
        ) {

            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (modifier = Modifier.align(Alignment.CenterVertically)) {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    { Text("$text") }
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