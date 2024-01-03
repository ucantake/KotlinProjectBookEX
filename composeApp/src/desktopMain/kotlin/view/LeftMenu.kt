package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun drawableMenuLeft() {
    var searchedText by remember { mutableStateOf("") }

    Image(
        painter = painterResource("icons/books-svgrepo-com.svg"),
        contentDescription = "Icons",
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
    OutlinedTextField(//TODO перестал работать ввод текста
        modifier = Modifier.fillMaxWidth().height(70.dp),
        singleLine = true,
        placeholder = {
            Text("Search")
        },
        label = {
            Text("Search")
        },
        value = searchedText,
        onValueChange = {
            searchedText = it
        },
        trailingIcon = {
            IconButton(
                onClick = {

                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search button",
                    tint = Color.Black
                )
            }
        }
    )
    TextButton(
        onClick = {

        }
    ) {
        Text(
            "Headlines",
            fontWeight = FontWeight.W900,
            color = Color.Black,
            modifier = Modifier
        )
    }
}