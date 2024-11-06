package com.chesystems.uibits

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/** Simple dropdown menu that shows a list of clickable text options */
@Composable
fun EZDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    options: List<Pair<String, () -> Unit>>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        options.forEach { (text, action) ->
            DropdownMenuItem(
                text = { Text(text = text) },
                onClick = {
                    action()
                    onDismiss()
                }
            )
        }
    }
}