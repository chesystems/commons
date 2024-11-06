package com.chesystems.uibits

import androidx.compose.ui.unit.dp

object Spacing {
    val none = 0.dp
    val dot = 1.dp
    val extraSmall = 2.dp // Matches Android's 2dp spacing
    val small = 4.dp // Common Android spacing
    val medium = 8.dp // Standard Android spacing
    val big = 16.dp // Material Design standard spacing

    val fabAvoidantPadding = 88.dp // Standard FAB height (56dp) + padding
}

object Corner {
    val none = 0.dp
    val dot = 1.dp
    val extraSmall = 2.dp
    val small = 4.dp 
    val medium = 8.dp
    val big = 16.dp // Material Design standard corner radius
    val extraBig = 28.dp // Material Design large component radius
}

object ImageSize {
    val small = 40.dp // Material avatar size
    val medium = 56.dp // Material standard component height
    val big = 112.dp // Material large component size
    val extraBig = 144.dp // Common large image size
}