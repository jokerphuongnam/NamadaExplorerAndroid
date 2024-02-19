package com.monsjoker.namadaexplorer.uis.screens.blocks.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import com.monsjoker.namadaexplorer.uis.shared_view.CardInfo
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
            .background(MaterialTheme.colorScheme.secondaryContainer) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = block.blockID.drop(2).uppercase(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 32.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardInfo(
                        title = "Height",
                        value = block.headerHeight.formattedWithCommas(),
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(
                        title = "Time",
                        value = block.headerTime.date?.stringDate ?: block.headerTime,
                        modifier = Modifier.weight(1f)
                    )
                }

                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = modifier,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
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
        }
    }
}