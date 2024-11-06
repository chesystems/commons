package com.chesystems.uibits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

/** Displays a customizable app icon with optional shape, size and highlight effects. */
@Composable
fun ShapedAppIcon(
    modifier: Modifier = Modifier,
    modifier2: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp? = null,
    highlighted: Boolean,
    bgColor: Color
) {
    Surface(
        shape = shape,
        modifier = modifier.then(size?.let { Modifier.size(it) } ?: Modifier.fillMaxSize()),
        border = if(highlighted) BorderStroke(Spacing.dot, MaterialTheme.colorScheme.primary.copy(alpha = 0.067f)) else null,
        color = bgColor
    ) {
        Icon(
            painter = painterResource(-1),
            contentDescription = "App Icon",
            modifier = modifier2.graphicsLayer(scaleX = 1.5f, scaleY = 1.5f),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}