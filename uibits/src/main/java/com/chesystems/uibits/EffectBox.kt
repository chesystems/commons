package com.chesystems.uibits

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
/** Clickable box supporting regular and long clicks */
fun Clickable(
    modifier: Modifier = Modifier,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.then(
            if (onLongClick != null) Modifier.combinedClickable(onLongClick = onLongClick, onClick = onClick)
            else Modifier.clickable(onClick = onClick)
        ),
        contentAlignment = Alignment.Center
    ) { content() }
}

@Composable
fun EZPad(
    start: Boolean = false,
    end: Boolean = false,
    top: Boolean = false,
    bottom: Boolean = false,
    startPad: Dp = ButtonDefaults.ContentPadding.calculateStartPadding(LayoutDirection.Ltr),
    endPad: Dp = ButtonDefaults.ContentPadding.calculateEndPadding(LayoutDirection.Rtl),
    topPad: Dp = Spacing.medium,
    bottomPad: Dp = Spacing.medium,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.padding(
        start = if(start) startPad else 0.dp,
        end = if(end) endPad else 0.dp,
        top = if(top) topPad else 0.dp,
        bottom = if(bottom) bottomPad else 0.dp
    )) { content() }
}