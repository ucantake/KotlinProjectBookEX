package view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupRadioButton(dataRadioButton: List<String>, onItemSelected: (String) -> Unit,isRow: Boolean = true) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(dataRadioButton[0]) }

    if (isRow) {
        Row(modifier = Modifier.selectableGroup()) {
            dataRadioButton.forEach { text ->
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onOptionSelected(text)
                        }
                    )
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    }else {
        Column(modifier = Modifier.selectableGroup()) {
            dataRadioButton.forEach { text ->
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onOptionSelected(text)
                        }
                    )
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    }

    // Вызываем обратный вызов при изменении выбранного элемента, для получение выбранного элемента
    DisposableEffect(selectedOption) {
        onItemSelected(selectedOption)
        onDispose { }
    }
}
