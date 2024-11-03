package com.chesystemsdev.uibits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/** Icon button with alert badge overlay */
@Composable
fun EZIconButton(
    icon: ImageVector,
    enabled: Boolean = true,
    alertAmount: Int,
    onClick: () -> Unit
) = Box {
    EZIconButton(icon = icon, enabled = enabled, onClick = onClick)
    if (alertAmount > 0) EZIcon(alertAmount = alertAmount)
}

/** Basic icon button wrapper */
@Composable
fun EZIconButton(
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit
) = IconButton(
    onClick = onClick,
    enabled = enabled
) { 
    EZIcon(icon = icon)
}

/** Extended FAB with alert badge in top-right */
@Composable
fun EZExFab(
    icon: ImageVector,
    alertAmount: Int,
    onClick: () -> Unit
) = Box(contentAlignment = Alignment.TopEnd) {
    EZExFab(icon = icon, onClick = onClick)
    if (alertAmount > 0) EZIcon(alertAmount = alertAmount, offsetX = 7)
}

/** Extended FAB with optional text label */
@Composable
fun EZExFab(
    text: String? = null,
    icon: ImageVector,
    onClick: () -> Unit
) = FloatingActionButton(
    onClick = onClick,
    elevation = FloatingActionButtonDefaults.loweredElevation()
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = Spacing.big),
        horizontalArrangement = Arrangement.spacedBy(Spacing.big)
    ) {
        text?.let { Text(text = it) }
        EZIcon(icon = icon)
    }
}

/** Text FAB with enabled/disabled states and colors */
@Composable
fun EZTextFab(
    text: String,
    disabledText: String = text,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val (containerColor, contentColor) = if(enabled) {
        MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.onError
    }

    FloatingActionButton(
        onClick = { if(enabled) onClick() },
        elevation = FloatingActionButtonDefaults.loweredElevation(0.dp),
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        Text(
            text = if(enabled) text else disabledText,
            modifier = Modifier.padding(ButtonDefaults.ContentPadding)
        )
    }
}

/** Clickable list item with leading icon */
@Composable
fun ListItemButton(
    icon: ImageVector,
    text: String,
    action: () -> Unit
) = ListItem(
    modifier = Modifier.clickable(onClick = action),
    leadingContent = { EZIcon(icon = icon) },
    headlineContent = { Text(text = text) }
)