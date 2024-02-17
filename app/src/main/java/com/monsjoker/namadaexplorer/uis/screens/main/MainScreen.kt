@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.main

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.R
import com.monsjoker.namadaexplorer.uis.screens.blocks.BlocksScreen
import com.monsjoker.namadaexplorer.uis.screens.governance.GovernanceScreen
import com.monsjoker.namadaexplorer.uis.screens.home.HomeScreen
import com.monsjoker.namadaexplorer.uis.screens.main.data.MainState
import com.monsjoker.namadaexplorer.uis.screens.parameters.ParametersScreen
import com.monsjoker.namadaexplorer.uis.screens.transactions.TransactionsScreen
import com.monsjoker.namadaexplorer.uis.screens.validators.ValidatorsScreen
import com.monsjoker.namadaexplorer.utils.visibility
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableInteractionSource")
@Composable
fun MainScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentState by rememberSaveable {
        mutableStateOf(MainState.HOME)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier,
            drawerContent = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(
                        modifier = if (drawerState.isOpen) Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                scope.launch {
                                    if (drawerState.isOpen) {
                                        drawerState.close()
                                    }
                                }
                            } else Modifier,
                    )
                    Surface(
                        modifier = Modifier
                            .requiredWidth(320.dp)
                            .fillMaxHeight(),
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topEnd = 16.dp,
                            bottomEnd = 16.dp
                        ),
                    ) {
                        MainContent(
                            modifier = Modifier
                        ) { mainState ->
                            scope.launch {
                                currentState = mainState
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                text = currentState.title,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Box(
                        modifier = Modifier.visibility(currentState == MainState.HOME)
                    ) {
                        HomeScreen(navController = navController)
                    }
                    Box(
                        modifier = Modifier.visibility(currentState == MainState.VALIDATORS)
                    ) {
                        ValidatorsScreen(navController = navController)
                    }
                    Box(
                        modifier = Modifier.visibility(currentState == MainState.BLOCKS)
                    ) {
                        BlocksScreen(navController = navController)
                    }
                    Box(
                        modifier = Modifier.visibility(currentState == MainState.TRANSACTIONS)
                    ) {
                        TransactionsScreen(navController = navController)
                    }
                    Box(
                        modifier = Modifier.visibility(currentState == MainState.GOVERNANCE)
                    ) {
                        GovernanceScreen(navController = navController)
                    }
                    Box(
                        modifier = Modifier.visibility(currentState == MainState.PARAMETERS)
                    ) {
                        ParametersScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier, itemClick: ((MainState) -> Unit)? = null) {
    val states = MainState.entries.toTypedArray()
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_namada_explorer),
                            contentDescription = "Namada explorer",
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "Namada explorer",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 32.dp),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
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
            }

            items(items = states) { state ->
                SelectedButton(iconId = state.iconId, title = state.title) {
                    itemClick?.invoke(state)
                }
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