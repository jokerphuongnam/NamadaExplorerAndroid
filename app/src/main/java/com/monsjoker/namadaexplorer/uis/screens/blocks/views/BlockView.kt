@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.blocks.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.R
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.shared_view.MiddleEllipsisText
import com.monsjoker.namadaexplorer.utils.date
import com.monsjoker.namadaexplorer.utils.formattedWithCommas
import com.monsjoker.namadaexplorer.utils.timeAgoString
import java.util.Date

@Composable
fun BlockView(
    index: Int,
    navController: NavController,
    now: Date,
    block: Block,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isShowBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .background(Color.Yellow)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .clickable {
                isShowBottomSheet = true
            } then modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                MiddleEllipsisText(
                    text = block.blockID.drop(2).uppercase(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = block.headerHeight.formattedWithCommas())

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Clock",
                        modifier = Modifier.size(16.dp)
                    )
                    val timeAgoString =
                        block.headerTime.date?.timeAgoString(now) ?: block.headerTime
                    Text(
                        text = timeAgoString,
                        modifier = Modifier,
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
            BlockBottomSheetView(
                navController = navController,
                block = block
            ) {
                isShowBottomSheet = false
            }
        }
    }
}