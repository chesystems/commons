package com.chesystems.uibits

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/** Displays an icon with an optional alert bubble overlay */
@Composable
fun EZIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    offsetX: Int = 28,
    offsetY: Int = -3,
    alertAmount: Int
) = Box {
    icon?.let { EZIcon(modifier, it, enabled) }
    if (alertAmount > 0) AlertBubble(alertAmount, offsetX, offsetY)
}

/** Simple icon with customizable padding and enabled state */
@Composable
fun EZIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    enabled: Boolean = true,
    padded: Boolean = false,
) {
    val padding = if(padded) ButtonDefaults.TextButtonContentPadding else null
    Icon(
        modifier = padding?.let { modifier.padding(it) } ?: modifier,
        imageVector = icon,
        contentDescription = null,
        tint = LocalContentColor.current.copy(alpha = if(enabled) 1f else 0.7f)
    )
}

/** Toggleable checkbox icon */
@Composable
fun CheckIcon(check: Boolean) = EZIcon(
    icon = if(check) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
    padded = true
)

/** Expandable arrow icon that points up/down */
@Composable
fun ExpandIcon(expand: Boolean) = EZIcon(
    icon = if(expand) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
    padded = true
)