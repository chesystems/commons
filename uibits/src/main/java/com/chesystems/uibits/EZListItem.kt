package com.chesystems.uibits

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

/** Base list item with customizable text and optional leading/trailing content */
@Composable
private fun EZListItem(
    modifier: Modifier = Modifier,
    size: TextUnit? = null,
    leading: @Composable (() -> Unit)? = null,
    title: String,
    subtitle: String? = null,
    decoration: TextDecoration = TextDecoration.None,
    color: Color? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    ListItem(
        modifier = modifier,
        leadingContent = leading,
        headlineContent = { Text(text = title, textDecoration = decoration, fontSize = size ?: TextUnit.Unspecified) },
        supportingContent = subtitle?.let { 
            { Text(text = it, textDecoration = decoration, fontSize = size?.times(0.8) ?: TextUnit.Unspecified) }
        },
        trailingContent = trailing,
        colors = ListItemDefaults.colors(containerColor = color ?: ListItemDefaults.containerColor)
    )
}

/** List item with icon badge, alert counter and navigation actions */
@Composable
fun EZItemNavAlert(
    modifier: Modifier = Modifier,
    size: TextUnit? = null,
    icon: ImageVector? = null,
    alertAmount: Int,
    title: String,
    subtitle: String? = null,
    decoration: TextDecoration = TextDecoration.None,
    color: Color? = null,
    actions: List<Pair<ImageVector, () -> Unit>>? = null
) {
    EZListItem(
        modifier = modifier,
        size = size,
        title = title,
        subtitle = subtitle,
        leading = icon?.let {
            {
                Box(Modifier.padding(ButtonDefaults.TextButtonContentPadding)) {
                    EZIcon(icon = it, alertAmount = alertAmount, offsetY = -14, offsetX = 18)
                }
            }
        },
        trailing = actions?.let {
            { Row { actions.forEach { (icon, action) -> EZIconButton(icon = icon) { action() } } } }
        },
        decoration = decoration,
        color = color
    )
}

/** List item with alert counter and action buttons */
@Composable
fun EZItemAlert(
    modifier: Modifier = Modifier,
    size: TextUnit? = null,
    icon: ImageVector? = null,
    title: String,
    subtitle: String? = null,
    decoration: TextDecoration = TextDecoration.None,
    color: Color? = null,
    disableOnNoAlert: Boolean = false,
    actions: List<Pair<Int, Pair<ImageVector, () -> Unit>>>? = null
) {
    EZListItem(
        modifier = modifier,
        size = size,
        title = title,
        subtitle = subtitle,
        leading = icon?.let { { EZIcon(icon = it, padded = true) } },
        trailing = actions?.let {
            {
                Row {
                    actions.forEach { (alert, iconAction) ->
                        EZIconButton(
                            icon = iconAction.first,
                            alertAmount = alert,
                            enabled = !disableOnNoAlert || alert > 0
                        ) { iconAction.second() }
                    }
                }
            }
        },
        decoration = decoration,
        color = color
    )
}

/** Simple list item with optional icon and action buttons */
@Composable
fun EZItem(
    modifier: Modifier = Modifier,
    size: TextUnit? = null,
    icon: ImageVector? = null,
    title: String,
    subtitle: String? = null,
    decoration: TextDecoration = TextDecoration.None,
    color: Color? = null,
    actions: List<Pair<ImageVector, () -> Unit>>? = null
) {
    EZListItem(
        modifier = modifier,
        size = size,
        title = title,
        subtitle = subtitle,
        leading = icon?.let { { EZIcon(icon = it, padded = true) } },
        trailing = actions?.let {
            { Row { actions.forEach { (icon, action) -> EZIconButton(icon = icon) { action() } } } }
        },
        decoration = decoration,
        color = color
    )
}

/** Checkbox list item with optional strike-through text and action buttons */
@Composable
fun EZCheckItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    check: Boolean,
    decorate: Boolean = true,
    color: Color? = null,
    setCheck: ((Boolean) -> Unit)? = null,
    trailing: List<Pair<ImageVector, () -> Unit>>? = null
) {
    val decoration = if (decorate && check) TextDecoration.LineThrough else TextDecoration.None
    val content = @Composable {
        EZListItem(
            modifier = modifier,
            title = title,
            subtitle = subtitle,
            decoration = decoration,
            color = color,
            leading = { Checkbox(checked = check, onCheckedChange = null) },
            trailing = trailing?.let {
                { Row { trailing.forEach { (icon, action) -> EZIconButton(icon = icon, onClick = action) } } }
            }
        )
    }
    
    if (setCheck != null) Clickable(onClick = { setCheck(!check) }) { content() }
    else content()
}