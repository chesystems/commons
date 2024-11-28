package com.chesystems.uibits

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Blink(
    content: @Composable () -> Unit
) {
    var show by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            while(true) {
                show = true
                delay(500)
                show = false
                delay(500)
            }
        }
    }
    Box {
        var heightFloat by remember { mutableFloatStateOf(0f) }
        Box(modifier = Modifier
            .height(with(LocalDensity.current) { heightFloat.toDp() })
            .alpha(0f)
        )
        AnimatedVisibility(
            modifier = Modifier.graphicsLayer {
                if(heightFloat == 0f)
                    heightFloat = size.height
            },
            visible = show, enter = fadeIn(), exit = fadeOut()
        ) {
            content()
        }
    }
}

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