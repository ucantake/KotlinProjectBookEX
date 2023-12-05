
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun MyApp() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome to My App!", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
//                    val context = LocalContext.current
//                    Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Click me")
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp()
}



fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        PreviewMyApp()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}