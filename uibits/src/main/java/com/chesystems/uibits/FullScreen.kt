package com.chesystems.uibits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/** Centers content in a full screen box */
@Composable
fun FullScreen(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {}
) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

/** Centers text with optional content below */
@Composable
fun FullScreenText(
    modifier: Modifier = Modifier,
    text: String,
    content: @Composable () -> Unit = {}
) = FullScreen(modifier) {
    Text(text = text, textAlign = TextAlign.Center)
    content()
}

/** Loading spinner with text above */
@Composable
fun LoadingText(text: String) = FullScreenText(text = text) {
    CircularProgressIndicator()
}