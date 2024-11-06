package com.chesystems.uibits

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

/** Rounded surface container for dialog content */
@Composable
fun DialogSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Corner.medium),
    ) { content() }
}

/** Circular notification badge showing count (caps at "+99") */
@Composable
fun AlertBubble(
    alertAmount: Int,
    offsetX: Int,
    offsetY: Int
) {
    val amountToShow = truncateFormat(alertAmount)

    Surface(
        modifier = Modifier
            .size(24.dp)
            .offset(x = offsetX.dp, y = offsetY.dp)
            .zIndex(1f),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = Spacing.dot
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            amountToShow?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}