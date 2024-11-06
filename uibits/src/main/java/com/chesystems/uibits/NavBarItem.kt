package com.chesystems.uibits

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/** Navigation bar item with icon, label and optional alert badge. */
@Composable
fun RowScope.NavBarItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    alertAmount: Int = 0,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { EZIcon(icon = icon, alertAmount = alertAmount) },
        label = { Text(label) }
    )
}