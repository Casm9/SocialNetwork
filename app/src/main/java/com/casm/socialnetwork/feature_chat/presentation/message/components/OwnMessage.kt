package com.casm.socialnetwork.feature_chat.presentation.message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceLarge
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium

@Composable
fun OwnMessage(
    message: String,
    formattedTime: String,
    modifier: Modifier = Modifier,
    triangleWidth: Dp = 30.dp,
    color: Color = Color.DarkGray,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    triangleHeight: Dp = 30.dp,
) {
    val cornerRadius = MaterialTheme.shapes.medium.bottomEnd
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = formattedTime,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.align(Alignment.Bottom)
        )
        Spacer(modifier = Modifier.width(SpaceLarge))
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = color,
                    shape = MaterialTheme.shapes.medium,
                )
                .padding(SpaceMedium)
                .drawBehind {
                    val cornerRadiusPx = cornerRadius.toPx(
                        shapeSize = size,
                        density = Density(density)
                    )
                    val path = Path().apply {
                        moveTo(
                            size.width,
                            size.height - cornerRadiusPx
                        )
                        lineTo(size.width, size.height + triangleHeight.toPx())
                        lineTo(
                            size.width - triangleWidth.toPx(), size.height - cornerRadiusPx
                        )
                        close()
                    }
                    drawPath(
                        path = path,
                        color = color
                    )
                }
        ) {
            Text(
                text = message,
                color = textColor
            )
        }
    }
}