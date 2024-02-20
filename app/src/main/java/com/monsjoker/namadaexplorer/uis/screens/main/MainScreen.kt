@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalToolbarApi::class)

package com.monsjoker.namadaexplorer.uis.screens.main

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
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
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeState
import com.monsjoker.namadaexplorer.uis.screens.main.data.MainState
import com.monsjoker.namadaexplorer.uis.screens.parameters.ParametersScreen
import com.monsjoker.namadaexplorer.uis.screens.transactions.TransactionsScreen
import com.monsjoker.namadaexplorer.uis.screens.validators.ValidatorsScreen
import com.monsjoker.namadaexplorer.uis.shared_view.SelectedButton
import com.monsjoker.namadaexplorer.utils.visibility
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableInteractionSource")
@Composable
fun MainScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentState by rememberSaveable {
        mutableStateOf(MainState.HOME)
    }
    val mainLoadedState by rememberSaveable {
        mutableStateOf(
            HashSet<MainState>().also {
                it.add(MainState.HOME)
            }
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier,
            drawerContent = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
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
                        NavigationDrawContent(
                            currentState = currentState,
                            modifier = Modifier,
                            closeClick = {
                                scope.launch {
                                    if (drawerState.isOpen) {
                                        drawerState.close()
                                    }
                                }
                            }
                        ) { mainState ->
                            scope.launch {
                                currentState = mainState
                                mainLoadedState.add(mainState)
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                }
                            }
                        }
                    }
                }
            }
        ) {
            MainContent(
                navController = navController,
                mainLoadedState = mainLoadedState,
                drawerState = drawerState,
                currentState = currentState
            )
        }
    }
}

@Composable
private fun NavigationDrawContent(
    currentState: MainState,
    modifier: Modifier,
    closeClick: () -> Unit,
    itemClick: ((MainState) -> Unit)? = null,
) {
    val states = MainState.entries.toTypedArray()
    val uriHandler = LocalUriHandler.current

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 8.dp
    ) {
        Scaffold(
            topBar = {
                LargeTopAppBar(
                    modifier = modifier
                        .shadow(8.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
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
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = closeClick
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "Namada explorer",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = states) { state ->
                    SelectedButton(
                        isSelected = state == currentState,
                        iconId = state.iconId,
                        title = state.title
                    ) {
                        itemClick?.invoke(state)
                    }
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    navController: NavController,
    mainLoadedState: HashSet<MainState>,
    drawerState: DrawerState,
    currentState: MainState
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val scope = rememberCoroutineScope()
    CollapsingToolbarScaffold(
        state = state,
        enabled = true,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        modifier = Modifier
            .fillMaxSize(),
        toolbar = {
            Card(
                Modifier
                    .height(52.dp),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    IconButton(
                        onClick = {
                            if (drawerState.isClosed) {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }

                    Text(
                        text = currentState.title,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier) {
            if (mainLoadedState.contains(MainState.HOME)) {
                Box(
                    modifier = Modifier.visibility(currentState == MainState.HOME)
                ) {
                    HomeScreen(navController = navController) { homeState ->
                        if (homeState == HomeState.DETAILS) {
                            scope.launch {
                                state.toolbarState.expand()
                            }
                        }
                    }
                }
            }
            if (mainLoadedState.contains(MainState.VALIDATORS)) {
                Box(
                    modifier = Modifier.visibility(currentState == MainState.VALIDATORS)
                ) {
                    ValidatorsScreen(navController = navController)
                }
            }
            if (mainLoadedState.contains(MainState.BLOCKS)) {
                Box(
                    modifier = Modifier.visibility(currentState == MainState.BLOCKS)
                ) {
                    BlocksScreen(navController = navController)
                }
            }
            if (mainLoadedState.contains(MainState.TRANSACTIONS)) {
                Box(
                    modifier = Modifier.visibility(currentState == MainState.TRANSACTIONS)
                ) {
                    TransactionsScreen(navController = navController)
                }
            }
            if (mainLoadedState.contains(MainState.GOVERNANCE)) {
                Box(
                    modifier = Modifier.visibility(currentState == MainState.GOVERNANCE)
                ) {
                    GovernanceScreen(navController = navController)
                }
            }
            if (mainLoadedState.contains(MainState.PARAMETERS)) {
                Box(
                    modifier = Modifier.visibility(currentState == MainState.PARAMETERS)
                ) {
                    ParametersScreen(navController = navController)
                }
            }
        }
    }
}