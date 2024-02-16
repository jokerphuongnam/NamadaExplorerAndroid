package com.monsjoker.namadaexplorer.uis.screens.blocks.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.shared_view.MiddleEllipsisText
import com.monsjoker.namadaexplorer.utils.date
import com.monsjoker.namadaexplorer.utils.formattedWithCommas
import com.monsjoker.namadaexplorer.utils.timeAgoString
import java.util.Date

@Composable
fun BlockView(index: Int, now: Date, block: Block, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .background(Color.Yellow)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) then modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = index.formattedWithCommas(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column(modifier = Modifier.weight(1f)) {
                MiddleEllipsisText(
                    text = block.blockID.drop(2),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                MiddleEllipsisText(
                    text = block.headerProposerAddress,
                    textAlign = TextAlign.Center,
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = block.headerHeight.formattedWithCommas())

                    Spacer(modifier = Modifier.weight(1f))

                    val timeAgoString = block.headerTime.date?.timeAgoString(now) ?: block.headerTime
                    Text(
                        text = timeAgoString,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}