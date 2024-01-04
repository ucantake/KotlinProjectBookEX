package view

import NAMEUSER
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

class Screens {

    @Composable
    fun HomeScreen() {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "HomeScreen",
            textAlign = TextAlign.Center
        )

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
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "ProfileScreen in $NAMEUSER",
            textAlign = TextAlign.Center
        )
    }
}