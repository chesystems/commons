package com.chesystemsdev.uibits

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

/** Stylish top app bar with customizable title, navigation and actions */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    title: String,
    color: Color = MaterialTheme.colorScheme.surface,
    deco: TextDecoration? = null,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title, fontSize = 18.sp, textDecoration = deco) },
        actions = actions,
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color)
    )
}