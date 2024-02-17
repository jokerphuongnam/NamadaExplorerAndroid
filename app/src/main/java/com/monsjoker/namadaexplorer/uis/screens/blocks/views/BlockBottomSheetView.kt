package com.monsjoker.namadaexplorer.uis.screens.blocks.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.shared_view.BottomSheetSelectedView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.date
import com.monsjoker.namadaexplorer.utils.formattedWithCommas
import com.monsjoker.namadaexplorer.utils.stringDate

@Composable
fun BlockBottomSheetView(
    navController: NavController,
    block: Block,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = block.blockID.drop(2).uppercase(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(label = "Height", value = block.headerHeight.formattedWithCommas())

            Text(label = "Time", value = block.headerTime.date?.stringDate ?: block.headerTime)

            BottomSheetSelectedView(
                title = "Validator Address",
                text = block.headerProposerAddress,
                modifier = Modifier
            ) {
                onClick?.invoke()
                navBackStackEntry?.savedStateHandle?.set(
                    "validator_address",
                    block.headerProposerAddress
                )
                navController.navigate("validator")
            }
        }
    }
}