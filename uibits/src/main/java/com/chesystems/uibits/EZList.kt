package com.chesystems.uibits

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> EZAnimatedColumn(
    items: List<T>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onEmpty: @Composable () -> Unit = {},
    itemKey: (T) -> Any,
    itemContent: @Composable (T, Modifier) -> Unit,
    finalContent: @Composable (() -> Unit)? = null
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

fun <T> filterListBySearch(
    list: List<T>,
    search: String,
    idSelector: (T) -> String
): List<T> {
    return if (search.isEmpty()) {
        list
    } else {
        list.filter { idSelector(it).contains(search, ignoreCase = true) }
    }
}

@Composable
fun <T> SearchableList(
    items: List<T>,
    idSelector: (T) -> String,
    content: @Composable (List<T>, String, (String) -> Unit) -> Unit
) {
    val (search, setSearch) = remember { mutableStateOf("") }
    val filteredItems by lazy {
        filterListBySearch(items, search, idSelector)
    }

    content(filteredItems, search, setSearch)
}