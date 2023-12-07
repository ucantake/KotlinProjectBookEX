package view

import androidx.compose.runtime.Composable


@Composable
expect fun LoginScreen(onLoginClicked: () -> Unit)
@Composable
expect fun MainScreen(function: () -> Unit)
