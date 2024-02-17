package com.monsjoker.namadaexplorer.uis.screens.main

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
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
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val states = MainState.entries.toTypedArray()
        val uriHandler = LocalUriHandler.current

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.Cyan
            ), onClick = {
                uriHandler.openUri("https://namada.net/shielded-expedition")
            }) {
            Text(
                text = "Namada Shielded Expedition NEBB ↗",
                modifier = Modifier
            )
        }

        List(size = states.size) { index ->
            SelectedButton(iconId = states[index].iconId, title = states[index].title) {
                navController.navigate(states[index].route)
            }
        }
    }
}

@Composable
private fun SelectedButton(
    @DrawableRes iconId: Int? = null,
    title: String,
    onClick: () -> Unit = {}
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow,
            contentColor = Color.Black
        ), onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconId != null) {
                Image(
                    painter = painterResource(id = iconId),
                    contentDescription = title,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = title,
                modifier = Modifier.weight(1f)
            )
        }
    }
}