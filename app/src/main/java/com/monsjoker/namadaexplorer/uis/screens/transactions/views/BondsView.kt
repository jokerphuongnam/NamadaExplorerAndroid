@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.transactions.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.monsjoker.namadaexplorer.R
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Bond
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLineFullWidth
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.MiddleEllipsisText
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun BondsView(pagingItems: LazyPagingItems<Bond>, modifier: Modifier = Modifier) {
    PagingStateView(
        modifier = modifier,
        pagingItems = pagingItems,
        contentPadding = PaddingValues(vertical = 16.dp),
        emptyText = "Transfer is empty",
        loading = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (index in 0..9) {
                    BondShimmerView()
                }
            }
        }
    ) { index, bond ->
        BondView(index = index + 1, bond = bond)
    }
}

@Composable
private fun BondView(index: Int, bond: Bond) {
    val sheetState = rememberModalBottomSheetState()
    var isShowBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = {
            isShowBottomSheet = true
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                MiddleEllipsisText(
                    text = bond.txID.drop(2),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_money),
                        contentDescription = "Money",
                        modifier = Modifier.height(24.dp)
                    )

                    Text(
                        text = "${bond.amount.toDouble().formattedWithCommas()} NAAN",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (isShowBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isShowBottomSheet = false
            }
        ) {
            BondDetailsBottomSheetView(
                bond = bond,
            )
        }
    }
}

@Composable
private fun BondDetailsBottomSheetView(bond: Bond, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = bond.txID.drop(2).uppercase(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                label = "Amount",
                value = "${bond.amount.toDouble().formattedWithCommas()} NAAN"
            )
            Text(
                label = "Source",
                value = bond.source.uppercase()
            )

            Text(
                label = "Validator",
                value = bond.validator.uppercase()
            )
        }
    }
}

@Composable
private fun BondShimmerView() {
    Card(
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ComponentRectangleLineFullWidth()
                Row(verticalAlignment = Alignment.Bottom) {
                    ComponentRectangleLine()
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}