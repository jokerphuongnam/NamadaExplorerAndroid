package com.monsjoker.namadaexplorer.uis.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.uis.screens.main.data.MainState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = "Namada Explorer")
                    }
                )
            },
        ) { innerPadding ->
            MainContent(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun MainContent(navController: NavController, modifier: Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
        val states = MainState.entries.toTypedArray()

        List(size = states.size) { index ->
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                ), onClick = {
                    navController.navigate(states[index].route)
                }) {
                Text(
                    text = states[index].title,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        val uriHandler = LocalUriHandler.current
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            ), onClick = {
                uriHandler.openUri("https://docs.namada.info/")
            }) {
            Text(
                text = "Docs",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}