package view

import NAMEUSER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
        //TODO добавить кнопку выхода на экране входа (выход)
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "ProfileScreen in $NAMEUSER",
            textAlign = TextAlign.Center
        )
    }
}