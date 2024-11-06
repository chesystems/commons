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
import androidx.compose.ui.unit.LayoutDirection

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

/** Box with horizontal button padding */
@Composable
fun HorizontalPadding(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(
        start = ButtonDefaults.ContentPadding.calculateStartPadding(LayoutDirection.Ltr),
        end = ButtonDefaults.ContentPadding.calculateEndPadding(LayoutDirection.Rtl),
        top = Spacing.none,
        bottom = Spacing.none
    )) { content() }
}

/** Box with button padding on all sides */
@Composable
fun TotalPadding(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(
        start = ButtonDefaults.ContentPadding.calculateStartPadding(LayoutDirection.Ltr),
        end = ButtonDefaults.ContentPadding.calculateEndPadding(LayoutDirection.Rtl),
        top = Spacing.medium,
        bottom = Spacing.medium
    )) { content() }
}

/** Box with horizontal and bottom button padding */
@Composable
fun TotalPaddingButUpper(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(
        start = ButtonDefaults.ContentPadding.calculateStartPadding(LayoutDirection.Ltr),
        end = ButtonDefaults.ContentPadding.calculateEndPadding(LayoutDirection.Rtl),
        top = Spacing.none,
        bottom = Spacing.medium
    )) { content() }
}