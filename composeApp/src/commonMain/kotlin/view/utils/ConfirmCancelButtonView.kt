package view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Отображение данных для дву факторной подтверждения транзакции
@Composable
fun ConfirmCancelButtonView(
    bookTitle: String,
    onConfirm: (Boolean,String) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {  onConfirm(true, bookTitle)},
            modifier = Modifier.size(20.dp)
        ) {
            // Иконка подтверждения
            Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm")
        }

        IconButton(
            onClick = { onConfirm(false, bookTitle) },
            modifier = Modifier.size(20.dp)
        ) {
            // Иконка отмены
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Cancel")
        }
    }
}
