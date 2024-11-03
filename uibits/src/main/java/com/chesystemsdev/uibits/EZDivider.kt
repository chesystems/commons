package com.chesystemsdev.uibits

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/** Renders a divider that can be vertical or have text in the middle */
@Composable
fun EZDivider(
    vertical: Boolean = false,
    text: String? = null
) {
    val color = MaterialTheme.colorScheme.primary
    
    HorizontalPadding {
        when {
            vertical -> VerticalDivider(thickness = Spacing.dot, color = color)
            text != null -> Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(
                    modifier = Modifier.weight(.85f),
                    thickness = Spacing.dot,
                    color = color
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = Spacing.small)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(.15f),
                    thickness = Spacing.dot,
                    color = color
                )
            }
            else -> HorizontalDivider(thickness = Spacing.dot, color = color)
        }
    }
}