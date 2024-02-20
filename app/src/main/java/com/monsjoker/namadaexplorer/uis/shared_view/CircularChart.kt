package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.utils.calculateCumulativeRatios
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun CircularChart(
    value: Float,
    color: Color = Color.Yellow,
    backgroundCircleColor: Color = Color.LightGray.copy(alpha = 0.3f),
    size: Dp = 280.dp,
    thickness: Dp = 16.dp,
    gapBetweenCircles: Dp = 42.dp
) {
    val sweepAngle = 360 * value / 100

    Canvas(
        modifier = Modifier
            .size(size)
    ) {
        var arcRadius = size.toPx()
        arcRadius -= gapBetweenCircles.toPx()

        drawCircle(
            color = backgroundCircleColor,
            radius = arcRadius / 2,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
            size = Size(arcRadius, arcRadius),
            topLeft = Offset(
                x = (size.toPx() - arcRadius) / 2,
                y = (size.toPx() - arcRadius) / 2
            )
        )
    }
}

class ChartConfig(
    val value: Number,
    val color: Color,
    val legend: String,
    val unit: String? = null
)

@Composable
fun CircularChart(
    configs: List<ChartConfig>,
    size: Dp = 280.dp,
    thickness: Dp = 16.dp,
    gapBetweenCircles: Dp = 42.dp
) {
    val values = configs.map { it.value }.calculateCumulativeRatios()
    val sweepAngles = values.map { ratio ->
        360 * ratio
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
        ) {
            var arcRadius = size.toPx()

            arcRadius -= gapBetweenCircles.toPx()
            for (i in configs.indices.reversed()) {
                drawArc(
                    color = configs[i].color,
                    startAngle = -90f,
                    sweepAngle = sweepAngles[i],
                    useCenter = false,
                    style = Stroke(
                        width = thickness.toPx(),
                        cap = StrokeCap.Butt
                    ),
                    size = Size(arcRadius, arcRadius),
                    topLeft = Offset(
                        x = (size.toPx() - arcRadius) / 2,
                        y = (size.toPx() - arcRadius) / 2
                    )
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (config in configs) {
                if (config.value.toLong() != 0L) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(config.color, shape = CircleShape)
                                    .size(16.dp)
                            )

                            Text(
                                text = config.legend,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                            )
                        }

                        if (config.unit == null) {
                            Text(
                                text = config.value.formattedWithCommas(),
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Text(
                                text = buildAnnotatedString {
                                    append(config.value.formattedWithCommas())
                                    append(" ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(config.unit)
                                    }
                                },
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}