package com.chesystems.uibits

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> EZAnimatedColumn(
    items: List<T>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onEmpty: @Composable () -> Unit = {},
    itemKey: (T) -> Any,
    itemContent: @Composable (T, Modifier) -> Unit,
    finalContent: @Composable() (() -> Unit)? = null
) {
    if (items.isNotEmpty()) {
        LazyColumn(
            contentPadding = contentPadding
        ) {
            items(
                items = items,
                key = { itemKey(it) }
            ) { item ->
                key(itemKey(item)) {
                    itemContent(item, Modifier.animateItem())
                }
            }
            if(finalContent != null)
                item { finalContent() }
        }
    } else onEmpty()
}