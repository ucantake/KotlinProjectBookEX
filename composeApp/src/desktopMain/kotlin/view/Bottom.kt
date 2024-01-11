package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import view.state.WindowState

//нижняя панель, в которую приходит состояние всего окна
//
//нужен contentButtonBar для отображение всякого содержимого
@Composable
fun bottomAppBar(state: WindowState) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        cutoutShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        contentButtonBar(state)
    }
}

//содержимое нижней панели
@Composable
fun contentButtonBar (state: WindowState)  {
    Column {
        Text(
            text = "Мультилатформенное приложения для обмена книг",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

    }

}