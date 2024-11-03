package com.chesystemsdev.uibits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Displays a flow of clickable text buttons with selected state.
 * Selected item shows a subtle border.
 *
 * @param entries List of items to display
 * @param selected Currently selected item
 * @param onClick Called when an item is clicked
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowClick(
    entries: List<String>,
    selected: String,
    onClick: (String) -> Unit
) {
    FlowRow {
        entries.forEach { entry ->
            val isSelected = selected == entry
            TextButton(
                onClick = { onClick(entry) },
                border = if (isSelected) BorderStroke(
                    width = Spacing.dot,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.067f)
                ) else null
            ) {
                Text(
                    text = entry,
                    modifier = Modifier.padding(horizontal = Spacing.small)
                )
            }
        }
    }
}