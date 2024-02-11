package com.monsjoker.namadaexplorer.uis.screens.blocks.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.shared_view.BottomSheetSelectedView

@Composable
fun BlockBottomSheetView(
    navController: NavController,
    block: Block,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Validator Address:")
            BottomSheetSelectedView(
                text = block.headerProposerAddress,
                modifier = Modifier.clickable {
                    navBackStackEntry?.savedStateHandle?.set(
                        "validator_address",
                        block.headerProposerAddress
                    )
                    navController.navigate("validator")
                }
            )
        }
    }
}